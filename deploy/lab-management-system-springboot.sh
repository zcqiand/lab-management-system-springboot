#!/bin/sh
# Usage: lab-management-system-springboot.sh <DOCKER_USERNAME> <DOCKER_PASSWORD> [VERSION]
#
# 由 .github/workflows/ci.yml 的 deploy job 远程调用:
#   ssh deploy@vps -- cd /home/deploy/lab-management-system-springboot
#                    && sh lab-management-system-springboot.sh $DOCKER_USERNAME $DOCKER_PASSWORD $VERSION
#
# VERSION 默认是 latest。tag-based deploy 时显式传 tag 名（v0.1.x-YYYYMMDD）。
# CI 同时 push :latest + :<tag> 两份镜像,回滚只要手动指定旧 tag 再跑一次本脚本。
#
# 与姊妹仓 saas-identity-platform-springboot.sh 的差异:
#   - env 名走 LAB_* 家族: LAB_DB_URL/USER/PASSWORD（非 SPRING_DATASOURCE_*）
#   - HOST_PORT=8013（lab 家族 801x: vue=8010 react=8011 nextjs=8012 springboot=8013）
#   - 额外 env: LAB_JWT_SECRET（必填 fail-fast，不用 dev 默认值）、
#     LAB_SAAS_BASE / LAB_SSO_CALLBACK_REDIRECT（SSO 跳板指向 saas-nextjs IdP）、
#     LAB_CORS_ALLOWED_ORIGINS（lab 前端域）
#
# 前置: deploy 用户需在 docker 组中(sudo usermod -aG docker deploy)。
#        springboot.env 必须由 setup-vps.sh 或本脚本首启生成(LAB_DB_URL 必填)。

set -eu

USERNAME="${1:-}"
PASSWORD="${2:-}"
VERSION="${3:-latest}"
IMAGE="${USERNAME}/lab-management-system-springboot:${VERSION}"
BASE="/home/deploy/lab-management-system-springboot"
CONTAINER_NAME="lab-management-system-springboot"
HOST_PORT=8013

# nginx domain（deploy 脚本渲染 nginx vhost 时用）
NGINX_DOMAIN="${NGINX_DOMAIN:-lab-springboot.xiangru.uk}"
NGINX_CERT_BASENAME="${NGINX_CERT_BASENAME:-xiangru-uk}"

if [ -z "$USERNAME" ] || [ -z "$PASSWORD" ]; then
  echo "Usage: $0 <DOCKER_USERNAME> <DOCKER_PASSWORD> [VERSION]" >&2
  exit 2
fi

# springboot.env 自举保护: 缺失时, 如 $DATABASE_URL + $DATABASE_USER + $DATABASE_PASSWORD 在环境里,
# 自动生成（LAB_* 映射 + CORS 白名单 + JWT/SSO 必填项校验）; 否则 fail fast。
# setup-vps.sh 仍是首推（VPS 一次性, 生成 nginx vhost + 目录）, 本分支仅给
# "先有 DATABASE_URL 临时上线"的场景。
if [ ! -f "$BASE/springboot.env" ]; then
  if [ -n "${DATABASE_URL:-}" ] && [ -n "${DATABASE_USER:-}" ] && [ -n "${DATABASE_PASSWORD:-}" ] && [ -n "${LAB_JWT_SECRET:-}" ]; then
    echo "→ bootstrapping $BASE/springboot.env from env DATABASE_URL/USER/PASSWORD + LAB_JWT_SECRET"
    umask 077
    {
      printf 'LAB_DB_URL=%s\n' "$DATABASE_URL"
      printf 'LAB_DB_USER=%s\n' "$DATABASE_USER"
      printf 'LAB_DB_PASSWORD=%s\n' "$DATABASE_PASSWORD"
      printf 'SERVER_PORT=8080\n'
      # JWT 签名密钥（HS256 ≥32B）。prod 必填 —— 不落 dev 默认值。
      printf 'LAB_JWT_SECRET=%s\n' "$LAB_JWT_SECRET"
      # CORS 白名单：lab 前端两仓 + 本地 dev。运维可在 setup-vps 之后手工追加 origin。
      printf 'LAB_CORS_ALLOWED_ORIGINS=https://lab-react.xiangru.uk,https://lab-vue.xiangru.uk,http://localhost:5173,http://localhost:5174\n'
      # SSO 跳板：saas IdP（lab-react .env.production 的 VITE_SAAS_BASE_URL 同源）
      printf 'LAB_SAAS_BASE=https://saas-nextjs.xiangru.uk\n'
      printf 'LAB_SSO_CALLBACK_REDIRECT=https://lab-react.xiangru.uk/login\n'
    } > "$BASE/springboot.env"
    chown deploy:deploy "$BASE/springboot.env" 2>/dev/null || true
    chmod 600 "$BASE/springboot.env"
  else
    echo "ERROR: $BASE/springboot.env missing. Set DATABASE_URL/USER/PASSWORD + LAB_JWT_SECRET env (e.g. DATABASE_URL=jdbc:postgresql://host/lab_prod DATABASE_USER=postgres DATABASE_PASSWORD=... LAB_JWT_SECRET=<32B+ random> sudo -E sh deploy/setup-vps.sh) or run setup-vps.sh first." >&2
    exit 1
  fi
fi
# 校验 springboot.env 里有 LAB_DB_URL + LAB_JWT_SECRET（即使 env-file 已存在, 内容可能是上一次失败留下的）
if ! grep -q '^LAB_DB_URL=' "$BASE/springboot.env"; then
  echo "ERROR: $BASE/springboot.env has no LAB_DB_URL line" >&2
  exit 1
fi
if ! grep -q '^LAB_JWT_SECRET=' "$BASE/springboot.env"; then
  echo "ERROR: $BASE/springboot.env has no LAB_JWT_SECRET line" >&2
  exit 1
fi

# nginx vhost 自举（缺时创建, 不 reload —— reload 要 root）:
# 检测 /etc/nginx/sites-enabled/<NGINX_DOMAIN> 是否存在; 缺时从 nginx-vps.conf.example
# 模板渲染, 做 symlink。reload 需 sudo, 留给手工:
#   sudo nginx -t && sudo systemctl reload nginx
NGINX_SITES_AVAILABLE="/etc/nginx/sites-available"
NGINX_SITES_ENABLED="/etc/nginx/sites-enabled"
NGINX_VHOST_FILE="${NGINX_SITES_AVAILABLE}/${NGINX_DOMAIN}"
NGINX_VHOST_LINK="${NGINX_SITES_ENABLED}/${NGINX_DOMAIN}"
NGINX_TEMPLATE="${BASE}/nginx-vps.conf.example"

# 拉模板（deploy/ 目录随仓库 deploy 脚本一起, 但首次拉时可能不存在, 补一下）
if [ ! -f "${NGINX_TEMPLATE}" ]; then
  echo "→ fetching nginx-vps.conf.example template"
  curl -fsSL "https://raw.githubusercontent.com/zcqiand/lab-management-system-springboot/refs/heads/master/deploy/nginx-vps.conf.example" -o "${NGINX_TEMPLATE}"
fi

if [ -e "${NGINX_VHOST_LINK}" ] || [ -e "${NGINX_VHOST_FILE}" ]; then
  echo "→ nginx vhost ${NGINX_VHOST_FILE} already exists, skip bootstrap"
else
  echo "→ nginx vhost missing, bootstrapping ${NGINX_VHOST_FILE} (domain=${NGINX_DOMAIN} cert=${NGINX_CERT_BASENAME})"
  umask 022
  sed \
    -e "s/lab.YOUR_DOMAIN/${NGINX_DOMAIN}/g" \
    -e "s|/etc/nginx/ssl/your-cert.cert|/etc/nginx/ssl/${NGINX_CERT_BASENAME}.cert|g" \
    -e "s|/etc/nginx/ssl/your-cert.key|/etc/nginx/ssl/${NGINX_CERT_BASENAME}.key|g" \
    "${NGINX_TEMPLATE}" > "${NGINX_VHOST_FILE}"
  ln -sf "${NGINX_VHOST_FILE}" "${NGINX_VHOST_LINK}"
  echo "→ nginx vhost created. To enable: sudo nginx -t && sudo systemctl reload nginx"
fi

# 必要时补 LAB_CORS_ALLOWED_ORIGINS（已有则不覆盖, 运维手工补的 prod origin 不会丢）。
if ! grep -q '^LAB_CORS_ALLOWED_ORIGINS=' "$BASE/springboot.env"; then
  echo "→ append LAB_CORS_ALLOWED_ORIGINS to existing $BASE/springboot.env"
  umask 077
  printf 'LAB_CORS_ALLOWED_ORIGINS=https://lab-react.xiangru.uk,https://lab-vue.xiangru.uk,http://localhost:5173,http://localhost:5174\n' >> "$BASE/springboot.env"
fi

echo "→ image: $IMAGE"
echo "→ docker login"
printf '%s' "$PASSWORD" | docker login -u "$USERNAME" --password-stdin

echo "→ docker pull"
docker pull "$IMAGE"

echo "→ docker stop & rm $CONTAINER_NAME"
docker stop "$CONTAINER_NAME" 2>/dev/null || true
docker rm "$CONTAINER_NAME" 2>/dev/null || true

echo "→ docker run"
docker run -d \
  --name "$CONTAINER_NAME" \
  --restart unless-stopped \
  -p "127.0.0.1:${HOST_PORT}:8080" \
  --env-file "$BASE/springboot.env" \
  "$IMAGE"

echo "→ docker image prune"
docker image prune -f

echo "→ docker ps"
docker ps --filter name="$CONTAINER_NAME"

# 健康检查: 直接 wget /actuator/health 探 200, 不依赖 Docker HEALTHCHECK 语义。
# （saas-springboot v0.1.8/09/10 教训: HEALTHCHECK 语义在 Docker daemon 不同版本上
#  行为不一致; host 端口探针才可靠。wget 探 HOST_PORT, 不是容器内 8080。）
i=0
while [ $i -lt 120 ]; do
  if wget --tries=1 --timeout=3 -q "http://127.0.0.1:${HOST_PORT}/actuator/health" -O /dev/null 2>/dev/null; then
    echo "→ /actuator/health 200 (host 127.0.0.1:${HOST_PORT}) after ${i}s"
    break
  fi
  # 容器实际死亡 (OOM / start-cmd failure / 立刻 crash) 提前终止循环, 立刻报失败。
  if ! docker inspect --format='{{.State.Running}}' "$CONTAINER_NAME" 2>/dev/null | grep -q true; then
    echo "→ container not running, logs:"
    docker logs --tail 30 "$CONTAINER_NAME"
    exit 1
  fi
  i=$((i+1))
  sleep 1
done

if [ $i -ge 120 ]; then
  echo "→ /actuator/health 仍未 200（120s 上限）, logs:"
  docker logs --tail 30 "$CONTAINER_NAME"
  exit 1
fi

echo "→ deploy done at $(date -u)"

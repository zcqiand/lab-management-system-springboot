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
#   - DB env 是全家族统一四件套 DATABASE_URL/USER/PASSWORD/NAME（值 jdbc: 格式）
#   - 容器内 Spring Boot 监听 :5205；host=container=5205（ADR-0018 单层 port 方案），
#     nginx 反代直接打 127.0.0.1:5205（见 nginx-vps.conf.example）
#   - 额外 env: JWT_SIGNING_KEY（必填 fail-fast，不用 dev 默认值；与代码占位符同名）、
#     LAB_SAAS_BASE_URL / LAB_SSO_CALLBACK_REDIRECT（SSO 跳板指向 saas-nextjs IdP）、
#     LAB_CORS_ALLOWED_ORIGINS（lab 前端域）
#
# 前置: deploy 用户需在 docker 组中(sudo usermod -aG docker deploy)。
#        springboot.env 必须由 setup-vps.sh 或本脚本首启生成(DATABASE_URL 必填)。

set -eu

USERNAME="${1:-}"
PASSWORD="${2:-}"
VERSION="${3:-latest}"
IMAGE="${USERNAME}/lab-management-system-springboot:${VERSION}"
BASE="/home/deploy/lab-management-system-springboot"
CONTAINER_NAME="lab-management-system-springboot"

# nginx domain（deploy 脚本渲染 nginx vhost 时用）
NGINX_DOMAIN="${NGINX_DOMAIN:-lab-springboot.xiangru.uk}"
NGINX_CERT_BASENAME="${NGINX_CERT_BASENAME:-xiangru-uk}"

if [ -z "$USERNAME" ] || [ -z "$PASSWORD" ]; then
  echo "Usage: $0 <DOCKER_USERNAME> <DOCKER_PASSWORD> [VERSION]" >&2
  exit 2
fi

# springboot.env 自举保护: 缺失时, 如 $DATABASE_URL + $DATABASE_USER + $DATABASE_PASSWORD 在环境里,
# 自动生成（全家族统一 DATABASE_* key + CORS 白名单 + JWT/SSO 必填项校验）; 否则 fail fast。
# setup-vps.sh 仍是首推（VPS 一次性, 生成 nginx vhost + 目录）, 本分支仅给
# "先有 DATABASE_URL 临时上线"的场景。
if [ ! -f "$BASE/springboot.env" ]; then
  if [ -n "${DATABASE_URL:-}" ] && [ -n "${DATABASE_USER:-}" ] && [ -n "${DATABASE_PASSWORD:-}" ] && [ -n "${JWT_SIGNING_KEY:-}" ] && [ -n "${LAB_SAAS_CLIENT_SECRET:-}" ]; then
    echo "→ bootstrapping $BASE/springboot.env from env DATABASE_URL/USER/PASSWORD + JWT_SIGNING_KEY + LAB_SAAS_CLIENT_SECRET"
    umask 077
    {
      printf 'DATABASE_URL=%s\n' "$DATABASE_URL"
      printf 'DATABASE_USER=%s\n' "$DATABASE_USER"
      printf 'DATABASE_PASSWORD=%s\n' "$DATABASE_PASSWORD"
      printf 'SERVER_PORT=5205\n'
      # JWT 签名密钥（HS256 ≥32B）。prod 必填 —— 不落 dev 默认值。
      # key 名与 application.yml 占位符一致（JWT_SIGNING_KEY；曾用 LAB_JWT_SECRET，
      # 2026-08-28 断链修复：老名无读者，prod 曾静默回落 dev 默认密钥）。
      printf 'JWT_SIGNING_KEY=%s\n' "$JWT_SIGNING_KEY"
      # CORS 白名单：lab 前端两仓 + 本地 dev。运维可在 setup-vps 之后手工追加 origin。
      printf 'LAB_CORS_ALLOWED_ORIGINS=https://lab-react.xiangru.uk,https://lab-vue.xiangru.uk,http://localhost:5201,http://localhost:5202,http://localhost:5203\n'
      # SSO 跳板：v0.1.x 接 saas-springboot v0.2.0 真 OAuth IdP（同栈匹配）。
      # ClientId 用固定 UUID 11111111-... 不是字符串 'lab-mgmt'，原因同 lab-aspnetcore
      # v0.1.9 — shared/openapi.yaml TypeSpec @format("uuid") 让 springboot UUID 接 Guid,
      # 与 3 个 saas 后端 V014/V009 seed client_id 同源。
      # saas-base 的 key 是 LAB_SAAS_BASE_URL（yml 占位符名；曾误写 LAB_SAAS_BASE
      # 丢 _URL 后缀 → 无读者，SSO 出口静默回落 localhost:3000）。
      printf 'LAB_SAAS_BASE_URL=https://saas-springboot.xiangru.uk\n'
      # 登录 UI 同栈匹配：lab-react 后端是 lab-springboot → 登录页指 saas-react
      #（2026-08-29 前指 saas-nextjs；saas-react LoginPage 已补 OAuth code 回跳）
      printf 'LAB_SSO_LOGIN_URL=https://saas-react.xiangru.uk\n'
      printf 'LAB_SAAS_CLIENT_ID=11111111-1111-1111-1111-111111111111\n'
      printf 'LAB_SAAS_CLIENT_SECRET=%s\n' "$LAB_SAAS_CLIENT_SECRET"
      printf 'LAB_SAAS_DEFAULT_TENANT_ID=%s\n' "${LAB_SAAS_DEFAULT_TENANT_ID:-00000000-0000-0000-0000-000000000001}"
      printf 'LAB_SSO_CALLBACK_REDIRECT=https://lab-react.xiangru.uk/login\n'
      # 服务账号（菜单快照）:禁兜底 —— 缺了 login 后拉菜单静默吃 dev 默认 alice
      if [ -z "${LAB_SAAS_SERVICE_USER:-}" ] || [ -z "${LAB_SAAS_SERVICE_PASSWORD:-}" ]; then
        echo "ERROR: LAB_SAAS_SERVICE_USER/PASSWORD env required to bootstrap $BASE/springboot.env (add to ci.yml envs; yml fallback 是 dev 值 alice, prod 不得静默兜底)" >&2
        exit 1
      fi
      printf 'LAB_SAAS_SERVICE_USER=%s\n' "$LAB_SAAS_SERVICE_USER"
      printf 'LAB_SAAS_SERVICE_PASSWORD=%s\n' "$LAB_SAAS_SERVICE_PASSWORD"
      # JWT 三件套显式写(值=契约文件值;禁 yml 占位默认值静默兜底)
      printf 'JWT_ISSUER=lab-management-system\n'
      printf 'JWT_AUDIENCE=lab-management-system-clients\n'
      printf 'JWT_TTL_SECONDS=3600\n'
      printf 'DATABASE_NAME=lab_prod\n'
    } > "$BASE/springboot.env"
    chown deploy:deploy "$BASE/springboot.env" 2>/dev/null || true
    chmod 600 "$BASE/springboot.env"
  else
    echo "ERROR: $BASE/springboot.env missing. Set DATABASE_URL/USER/PASSWORD + JWT_SIGNING_KEY + LAB_SAAS_CLIENT_SECRET env (e.g. DATABASE_URL=jdbc:postgresql://host/lab_prod DATABASE_USER=postgres DATABASE_PASSWORD=... JWT_SIGNING_KEY=<32B+ random> LAB_SAAS_CLIENT_SECRET=<saas-springboot V009 seeded client secret> sudo -E sh deploy/setup-vps.sh) or run setup-vps.sh first." >&2
    exit 1
  fi
fi
# 校验 springboot.env 里有 DATABASE_URL + JWT_SIGNING_KEY（即使 env-file 已存在, 内容可能是上一次失败留下的）
# 老契约迁移提示: 旧 env-file 里是 LAB_DATABASE_URL/LAB_JWT_SECRET/LAB_SAAS_BASE 等
# 老 key 名 —— 二选一: 手工改 key 名, 或备份后删掉 env-file 带 secrets 重跑本脚本重建。
# 改 env-file 后必须走本脚本重建容器（--env-file 只在 docker create 时读, restart 不重读）。
if ! grep -q '^DATABASE_URL=' "$BASE/springboot.env"; then
  echo "ERROR: $BASE/springboot.env has no DATABASE_URL line (old key LAB_DATABASE_URL? see migration note above)" >&2
  exit 1
fi
if ! grep -q '^JWT_SIGNING_KEY=' "$BASE/springboot.env"; then
  echo "ERROR: $BASE/springboot.env has no JWT_SIGNING_KEY line (old key LAB_JWT_SECRET? see migration note above)" >&2
  exit 1
fi

# nginx vhost 重渲染（每次 deploy 都跑,ADR-0018:容器端口变了 vhost 必须跟）:
# 模板从 master 拉,渲染后写入 sites-available,symlink sites-enabled,再 sudo nginx -t + reload。
# diff 检测:内容未变跳过 reload (nginx -t 也省)。
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

# 渲染到临时文件 —— sed 同时覆盖 3 种 placeholder:
#   Style A (lab-vue/react):      <domain>
#   Style B/C (nextjs/sp/aspc):   lab.YOUR_DOMAIN / saas.YOUR_DOMAIN
#   cert 路径: your-cert.{crt,cert} / <domain>.crt → 统一到 ${NGINX_CERT_BASENAME}.cert
TMP_VHOST="$(mktemp -t vpstpl.XXXXXX)"
sed \
  -e "s|<domain>|${NGINX_DOMAIN}|g" \
  -e "s|lab\.YOUR_DOMAIN|${NGINX_DOMAIN}|g" \
  -e "s|saas\.YOUR_DOMAIN|${NGINX_DOMAIN}|g" \
  -e "s|/etc/nginx/ssl/<domain>\.crt|/etc/nginx/ssl/${NGINX_CERT_BASENAME}.cert|g" \
  -e "s|/etc/nginx/ssl/<domain>\.key|/etc/nginx/ssl/${NGINX_CERT_BASENAME}.key|g" \
  -e "s|/etc/nginx/ssl/your-cert\.crt|/etc/nginx/ssl/${NGINX_CERT_BASENAME}.cert|g" \
  -e "s|/etc/nginx/ssl/your-cert\.cert|/etc/nginx/ssl/${NGINX_CERT_BASENAME}.cert|g" \
  -e "s|/etc/nginx/ssl/your-cert\.key|/etc/nginx/ssl/${NGINX_CERT_BASENAME}.key|g" \
  "${NGINX_TEMPLATE}" > "${TMP_VHOST}"

# diff 检测:已有 vhost 且内容相同就 skip,不同才重写 + reload
if [ -e "${NGINX_VHOST_FILE}" ] && diff -q "${TMP_VHOST}" "${NGINX_VHOST_FILE}" >/dev/null 2>&1; then
  echo "→ nginx vhost ${NGINX_VHOST_FILE} unchanged, skip"
  rm -f "${TMP_VHOST}"
else
  echo "→ rendering nginx vhost ${NGINX_VHOST_FILE} (domain=${NGINX_DOMAIN} cert=${NGINX_CERT_BASENAME})"
  # 写入 sites-available (deploy 用户可能没写权限,需要 sudoers 配 nginx 白名单)
  if [ -w "${NGINX_SITES_AVAILABLE}" ]; then
    cp "${TMP_VHOST}" "${NGINX_VHOST_FILE}"
  else
    sudo cp "${TMP_VHOST}" "${NGINX_VHOST_FILE}" \
      || { echo "ERROR: sudo cp ${NGINX_VHOST_FILE} failed"; rm -f "${TMP_VHOST}"; exit 1; }
  fi
  # symlink sites-enabled
  if [ -w "${NGINX_SITES_ENABLED}" ]; then
    ln -sf "${NGINX_VHOST_FILE}" "${NGINX_VHOST_LINK}"
  else
    sudo ln -sf "${NGINX_VHOST_FILE}" "${NGINX_VHOST_LINK}" \
      || { echo "ERROR: sudo ln ${NGINX_VHOST_LINK} failed"; rm -f "${TMP_VHOST}"; exit 1; }
  fi
  rm -f "${TMP_VHOST}"
  # nginx config test + reload (CI 自动完成,不再依赖手工)
  echo "→ nginx -t"
  sudo nginx -t
  echo "→ systemctl reload nginx"
  sudo systemctl reload nginx
  echo "✓ nginx reloaded"
fi

# 必要时补 LAB_CORS_ALLOWED_ORIGINS（已有则不覆盖, 运维手工补的 prod origin 不会丢）。
if ! grep -q '^LAB_CORS_ALLOWED_ORIGINS=' "$BASE/springboot.env"; then
  echo "→ append LAB_CORS_ALLOWED_ORIGINS to existing $BASE/springboot.env"
  umask 077
  printf 'LAB_CORS_ALLOWED_ORIGINS=https://lab-react.xiangru.uk,https://lab-vue.xiangru.uk,http://localhost:5201,http://localhost:5202,http://localhost:5203\n' >> "$BASE/springboot.env"
fi

# v0.1.14 起: IdP 登录页 = saas 前端域名（不是 API 域名，API /login 404）。
# 早期 env 只有 LAB_SAAS_BASE，authorizeUrl 曾拼出 {API}/login 404。append-only 补。
# v0.1.27: 登录 UI 同栈匹配 saas-react（lab-react 后端 = 本仓）。存量 env 里
# 脚本旧默认 saas-nextjs 原地迁移；自定义值不动。
if ! grep -q '^LAB_SSO_LOGIN_URL=' "$BASE/springboot.env"; then
  echo "→ append LAB_SSO_LOGIN_URL to existing $BASE/springboot.env"
  umask 077
  printf 'LAB_SSO_LOGIN_URL=https://saas-react.xiangru.uk\n' >> "$BASE/springboot.env"
elif grep -q '^LAB_SSO_LOGIN_URL=https://saas-nextjs\.xiangru\.uk$' "$BASE/springboot.env"; then
  echo "→ migrate stale LAB_SSO_LOGIN_URL saas-nextjs -> saas-react (同栈匹配) in $BASE/springboot.env"
  sed -i 's#^LAB_SSO_LOGIN_URL=https://saas-nextjs\.xiangru\.uk$#LAB_SSO_LOGIN_URL=https://saas-react.xiangru.uk#' "$BASE/springboot.env"
fi

# 端口分段迁移 2026-09-02 (conventions §6): 老 springboot.env 由本脚本老版本 bootstrap 时写
# 过 SERVER_PORT=8080；2026-09-02 后 application.yml:Dockerfile:deploy.sh:HOST_PORT 已全迁
# 5205，而 env-file 的 SERVER_PORT=8080 让 Spring Boot 仍监听 :8080。但容器 EXPOSE 5205 +
# -p 127.0.0.1:8013:5205，wget 探针 connection refused → deploy 120s 上限 kill 容器。
# append_if_missing 只补缺失不 reconcile stale（家族通病，见 springboot env 漂移案例库），
# 必须显式 sed 锚定整行迁移。运维手工改 SERVER_PORT=5205 也走同一迁移逻辑。
if ! grep -q '^SERVER_PORT=' "$BASE/springboot.env"; then
  echo "→ append SERVER_PORT to existing $BASE/springboot.env"
  umask 077
  printf 'SERVER_PORT=5205\n' >> "$BASE/springboot.env"
elif grep -q '^SERVER_PORT=8080$' "$BASE/springboot.env"; then
  echo "→ migrate stale SERVER_PORT 8080 -> 5205 in $BASE/springboot.env (conventions §6 端口分段)"
  sed -i 's#^SERVER_PORT=8080$#SERVER_PORT=5205#' "$BASE/springboot.env"
fi

# v0.1.16 起: LAB_SAAS_* 系列 append-only 补齐 + SECRET fail-fast。
# 事故（2026-08-26 prod SSO 502）: 早期 env 只有一行 LAB_SAAS_BASE，CLIENT_ID/
# CLIENT_SECRET/DEFAULT_TENANT_ID/CALLBACK_REDIRECT 全缺 → app 静默回落
# application.yml 默认 client-id='lab-mgmt'（字符串）→ saas 400 INVALID_CLIENT
# → 502 被 Cloudflare 换皮丢 CORS 头，浏览器误报 CORS。缺失项必须补，补不了的报错。
if ! grep -q '^LAB_SAAS_BASE_URL=' "$BASE/springboot.env"; then
  echo "→ append LAB_SAAS_BASE_URL to existing $BASE/springboot.env"
  umask 077
  printf 'LAB_SAAS_BASE_URL=https://saas-springboot.xiangru.uk\n' >> "$BASE/springboot.env"
fi
if ! grep -q '^LAB_SSO_CALLBACK_REDIRECT=' "$BASE/springboot.env"; then
  echo "→ append LAB_SSO_CALLBACK_REDIRECT to existing $BASE/springboot.env"
  umask 077
  printf 'LAB_SSO_CALLBACK_REDIRECT=https://lab-react.xiangru.uk/login\n' >> "$BASE/springboot.env"
fi
if ! grep -q '^LAB_SAAS_CLIENT_ID=' "$BASE/springboot.env"; then
  echo "→ append LAB_SAAS_CLIENT_ID to existing $BASE/springboot.env"
  umask 077
  printf 'LAB_SAAS_CLIENT_ID=11111111-1111-1111-1111-111111111111\n' >> "$BASE/springboot.env"
fi
if ! grep -q '^LAB_SAAS_DEFAULT_TENANT_ID=' "$BASE/springboot.env"; then
  echo "→ append LAB_SAAS_DEFAULT_TENANT_ID to existing $BASE/springboot.env"
  umask 077
  printf 'LAB_SAAS_DEFAULT_TENANT_ID=%s\n' "${LAB_SAAS_DEFAULT_TENANT_ID:-00000000-0000-0000-0000-000000000001}" >> "$BASE/springboot.env"
fi
# CLIENT_SECRET 无默认值: 优先 append SSH env 转发过来的（ci.yml envs 段）,
# 两处都没有 → fail-fast。静默回落 yml 占位值 'lab-mgmt-secret' 在 prod 必 401。
if ! grep -q '^LAB_SAAS_CLIENT_SECRET=' "$BASE/springboot.env"; then
  if [ -n "${LAB_SAAS_CLIENT_SECRET:-}" ]; then
    echo "→ append LAB_SAAS_CLIENT_SECRET (from SSH env) to existing $BASE/springboot.env"
    umask 077
    printf 'LAB_SAAS_CLIENT_SECRET=%s\n' "$LAB_SAAS_CLIENT_SECRET" >> "$BASE/springboot.env"
  else
    echo "ERROR: $BASE/springboot.env has no LAB_SAAS_CLIENT_SECRET and SSH env did not forward one. Set it in the env-file or add LAB_SAAS_CLIENT_SECRET to ci.yml envs — otherwise the app falls back to the dev placeholder and saas rejects every token exchange." >&2
    exit 1
  fi
fi
# 漂移可见性: 部署日志回显生效的 SSO 出口配置（SECRET 只显示已设,不回显值）。
echo "→ sso env effective: LAB_SAAS_BASE_URL=$(grep '^LAB_SAAS_BASE_URL=' "$BASE/springboot.env" | tail -1 | cut -d= -f2-) LAB_SAAS_CLIENT_ID=$(grep '^LAB_SAAS_CLIENT_ID=' "$BASE/springboot.env" | tail -1 | cut -d= -f2-) LAB_SAAS_CLIENT_SECRET=<set:$(grep -c '^LAB_SAAS_CLIENT_SECRET=' "$BASE/springboot.env")>"

# 2026-08-28 key 对齐(老 env-file 迁移): 逐 key append-if-missing 到
# .env.production 全集(suite L0.5 check_deploy_parity 锁死)。service 账号是
# secret 类:老文件已有则保留;没有则从 env 传入,fail-fast 不兜底。
if [ -f "$BASE/springboot.env" ]; then
  append_if_missing() {
    key="$1"; val="$2"
    if ! grep -q "^${key}=" "$BASE/springboot.env"; then
      echo "→ append ${key} to existing $BASE/springboot.env"
      umask 077
      printf '%s=%s\n' "$key" "$val" >> "$BASE/springboot.env"
    fi
  }
  append_if_missing DATABASE_NAME 'lab_prod'
  append_if_missing JWT_ISSUER 'lab-management-system'
  append_if_missing JWT_AUDIENCE 'lab-management-system-clients'
  append_if_missing JWT_TTL_SECONDS '3600'
  if ! grep -q '^LAB_SAAS_SERVICE_USER=' "$BASE/springboot.env"; then
    if [ -z "${LAB_SAAS_SERVICE_USER:-}" ] || [ -z "${LAB_SAAS_SERVICE_PASSWORD:-}" ]; then
      echo "ERROR: LAB_SAAS_SERVICE_USER/PASSWORD missing in $BASE/springboot.env and not forwarded via ci.yml envs (yml fallback 是 dev 值, prod 不得静默兜底)" >&2
      exit 1
    fi
    append_if_missing LAB_SAAS_SERVICE_USER "$LAB_SAAS_SERVICE_USER"
    append_if_missing LAB_SAAS_SERVICE_PASSWORD "$LAB_SAAS_SERVICE_PASSWORD"
  fi
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
  -p "127.0.0.1:5205:5205" \
  --env-file "$BASE/springboot.env" \
  "$IMAGE"

echo "→ docker image prune"
docker image prune -f

echo "→ docker ps"
docker ps --filter name="$CONTAINER_NAME"

# 健康检查: 直接 wget /actuator/health 探 200, 不依赖 Docker HEALTHCHECK 语义。
# （saas-springboot v0.1.8/09/10 教训: HEALTHCHECK 语义在 Docker daemon 不同版本上
#  行为不一致; host 端口探针才可靠。wget 探 127.0.0.1:5205 (host=container)。）
i=0
while [ $i -lt 120 ]; do
  if wget --tries=1 --timeout=3 -q "http://127.0.0.1:5205/actuator/health" -O /dev/null 2>/dev/null; then
    echo "→ /actuator/health 200 (host 127.0.0.1:5205) after ${i}s"
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

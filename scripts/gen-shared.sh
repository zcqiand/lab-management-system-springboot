#!/bin/bash
# Generate Java client locally from shared's OpenAPI.yaml.
#
# 架构（对齐 saas-identity-platform-springboot v0.2.0 模式）：shared 仓是纯契约源
# （TypeSpec → OpenAPI.yaml only），语言产物在各消费仓本地生成。
# 本脚本：先触发 shared emit，再跑 openapi-generator 产 spring interfaceOnly 骨架
# （DB schema 同步已拆到 scripts/scaffold-entities.sh——ADR-0025/0033 DB-First）。
set -euo pipefail

SHARED_DIR="$(cd "$(dirname "$0")/../../lab-management-system-shared" && pwd)"
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
DEST="$ROOT/src/main/java"

echo "[gen-shared] step 1/2 — shared: emit OpenAPI.yaml..."
(cd "$SHARED_DIR" && npm run emit:openapi)

OPENAPI="$SHARED_DIR/generated/openapi/openapi.yaml"
if [ ! -f "$OPENAPI" ]; then
  echo "[gen-shared] ERROR: missing $OPENAPI" >&2
  exit 1
fi

echo "[gen-shared] step 2/2 — springboot: openapi-generator → src/main/java/..."

# npx 解析 @openapitools/openapi-generator-cli（与 shared 仓同一工具链）。
# 参数镜像 saas-identity-platform-springboot 的 gen-shared.sh（v0.2.0 定案）：
# spring-boot library, interfaceOnly, useSpringBoot3, dateLibrary=java8。
# hideGenerationTimestamp=true（5.70）：@Generated 不再注入 date=时间戳，
# 否则每次 regen 154 文件全量 diff，污染 marker-only 惯例。生成器内置开关，
# 生成的注解缩为 @Generated(value=..., comments="Generator version: ...")，语义不变。
npx --yes @openapitools/openapi-generator-cli generate \
  -g spring \
  -i "$OPENAPI" \
  -o "$ROOT/.openapi-tmp/java" \
  --library spring-boot \
  --model-package io.xr.lab.shared.dto \
  --api-package io.xr.lab.shared.api \
  --invoker-package io.xr.lab.shared \
  --additional-properties useTags=true,interfaceOnly=true,skipDefaultInterface=true,useBeanValidation=true,useSpringBoot3=true,dateLibrary=java8,hideGenerationTimestamp=true

# 把生成的 dto + api 挪进 springboot 源码树。
# 目录必须与包名一致（io.xr.lab.shared.api → shared/api/）：2026-09-02 前错位拷进
# platform/api/，javac 全量编译不挑目录能过，但 mvn spring-boot:run 增量编译
# 撞 stale class（AuthApi 旧包名残留 target/classes）→ AuthController 构造炸
# Unresolved compilation problems。saas 版（saas/identity/shared/api）无此错位。
mkdir -p "$DEST/io/xr/lab/shared/dto" "$DEST/io/xr/lab/shared/api"
rm -rf "$DEST/io/xr/lab/shared/dto"/* "$DEST/io/xr/lab/shared/api"/* "$DEST/io/xr/lab/platform/api"
cp -r "$ROOT/.openapi-tmp/java/src/main/java/io/xr/lab/shared/dto/." "$DEST/io/xr/lab/shared/dto/"
cp -r "$ROOT/.openapi-tmp/java/src/main/java/io/xr/lab/shared/api/." "$DEST/io/xr/lab/shared/api/"
rm -rf "$ROOT/.openapi-tmp"

# 生成器已知缺陷修补：oneOf + enum discriminator（AuthState 4 态）下，
# 接口被生成 `String getKind()`，而各变体类返回各自的嵌套 KindEnum —— 返回类型
# 不兼容无法 override（javac 报“未覆盖抽象方法”）。多态由类上的
# @JsonTypeInfo/@JsonSubTypes 注解驱动，接口方法本身无人消费，删掉即净。
sed -i '/^    public String getKind();$/d' "$DEST/io/xr/lab/shared/dto/AuthState.java"

# L1 前置：生成器排版不过 google-java-format（L1 门会拦），
# codegen 末端统一 apply，保证产物落地即 gate-ready（saas 仓是手动补的，这里进脚本）。
mvn -q spotless:apply

# DB - schema 同步随 ADR-0033 退役（原 step 3/3：拷 shared/sql/migrations/* 进
# src/main/resources/db/migration/ 供 Flyway replay，含 DIVERGED_VERSIONS="V014"
# 分叉保护）。DB-First（ADR-0025/0033）下本仓不拥有迁移：真源 = shared
# src/db/schema.ts，镜像 = bash scripts/scaffold-entities.sh → entity/Generated/。
echo "[gen-shared] OK"
echo "[gen-shared]    DB schema 同步请跑: bash scripts/scaffold-entities.sh（shared 已 db:migrate 之后）"

# ADR-0026 §2: 写 last-gen-shared.json marker（API 类别），供 suite 跨仓 staleness check 使用。
# 失败不阻塞 gen-shared.sh —— staleness 是 warning（V1 档）不是 build blocker。
# 失败时 suite 会报 UNKNOWN 让 reviewer 看到，而不是悄悄丢失同步信号。
SHARED_SHA=$(cd "$SHARED_DIR" && git rev-parse HEAD)
MARKER="$ROOT/.state/last-gen-shared.json"
mkdir -p "$ROOT/.state"

if python3 - "$MARKER" "$SHARED_SHA" "$(basename "$0")" "$(basename "$ROOT")" <<'PYEOF'
import datetime, json, sys

marker_path, shared_sha, cmd, repo = sys.argv[1:5]
try:
    with open(marker_path, encoding="utf-8") as f:
        marker = json.load(f)
except (FileNotFoundError, json.JSONDecodeError):
    marker = {}

# 5.77（2026-09-21 人裁立项）：同 sha 零写入——目标通道 synced_sha 与现存 marker 相同
# → 整个 marker 文件零写入（时间戳/mtime 保持原值，字节级幂等）；sha 真变才全量写
# （新 sha + 新时间戳）。判据只比 sha，时间戳不参与；JSON 形状/key 名一概不动。
if cmd.startswith("gen-shared"):
    channel = "api_synced"
elif cmd.startswith(("scaffold", "sync-db", "pull-schema")):
    channel = "db_synced"
else:
    channel = None

if channel is not None and marker.get(channel + "_sha") == shared_sha:
    print("[marker] %s_sha unchanged (%s...) - zero write, keep timestamp (5.77)"
          % (channel, shared_sha[:12]))
    sys.exit(3)

now = datetime.datetime.now(datetime.timezone.utc).isoformat()
if channel is not None:
    marker[channel + "_sha"] = shared_sha
    marker[channel + "_at"] = now
    marker[channel + "_cmd"] = cmd

# shared_sha 取「最近一次同步」对应的 sha：ISO-8601 UTC 时间戳字典序==时间序。
# 勿用 max(sha)——SHA 字典序不是 git 时间序（5.21 事故）。
entries = [
    (marker.get(k + "_at", ""), marker[k + "_sha"])
    for k in ("api_synced", "db_synced")
    if marker.get(k + "_sha")
]
marker["shared_sha"] = max(entries)[1] if entries else shared_sha
marker["consumer_repo"] = repo

with open(marker_path, "w", encoding="utf-8") as f:
    json.dump(marker, f, ensure_ascii=False, indent=2)
    f.write("\n")
PYEOF
then
  echo "[gen-shared]    ADR-0026 marker 已落盘: $MARKER (shared HEAD ${SHARED_SHA:0:7})"
else
  rc=$?
  if [ "$rc" -eq 3 ]; then
    echo "[gen-shared]    ADR-0026 marker sha 未变，零写入（5.77 同 sha 不刷时间戳）: $MARKER"
  else
    echo "[gen-shared]    WARN: marker 写失败（python3 缺失？）—— staleness 将报 UNKNOWN" >&2
  fi
fi

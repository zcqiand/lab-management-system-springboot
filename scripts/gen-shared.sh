#!/bin/bash
# Generate Java client locally from shared's OpenAPI.yaml.
#
# 架构（对齐 saas-identity-platform-springboot v0.2.0 模式）：shared 仓是纯契约源
# （TypeSpec → OpenAPI.yaml only），语言产物在各消费仓本地生成。
# 本脚本两步走：先触发 shared emit，再跑 openapi-generator 产 spring interfaceOnly 骨架。
set -euo pipefail

SHARED_DIR="$(cd "$(dirname "$0")/../../lab-management-system-shared" && pwd)"
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
DEST="$ROOT/src/main/java"

echo "[gen-shared] step 1/3 — shared: emit OpenAPI.yaml..."
(cd "$SHARED_DIR" && npm run emit:openapi)

OPENAPI="$SHARED_DIR/generated/openapi/openapi.yaml"
if [ ! -f "$OPENAPI" ]; then
  echo "[gen-shared] ERROR: missing $OPENAPI" >&2
  exit 1
fi

echo "[gen-shared] step 2/3 — springboot: openapi-generator → src/main/java/..."

# npx 解析 @openapitools/openapi-generator-cli（与 shared 仓同一工具链）。
# 参数镜像 saas-identity-platform-springboot 的 gen-shared.sh（v0.2.0 定案）：
# spring-boot library, interfaceOnly, useSpringBoot3, dateLibrary=java8。
npx --yes @openapitools/openapi-generator-cli generate \
  -g spring \
  -i "$OPENAPI" \
  -o "$ROOT/.openapi-tmp/java" \
  --library spring-boot \
  --model-package io.xr.lab.shared.dto \
  --api-package io.xr.lab.shared.api \
  --invoker-package io.xr.lab.shared \
  --additional-properties useTags=true,interfaceOnly=true,skipDefaultInterface=true,useBeanValidation=true,useSpringBoot3=true,dateLibrary=java8

# 把生成的 dto + api 挪进 springboot 源码树。
mkdir -p "$DEST/io/xr/lab/shared/dto" "$DEST/io/xr/lab/platform/api"
rm -rf "$DEST/io/xr/lab/shared/dto"/* "$DEST/io/xr/lab/platform/api"/*
cp -r "$ROOT/.openapi-tmp/java/src/main/java/io/xr/lab/shared/dto/." "$DEST/io/xr/lab/shared/dto/"
cp -r "$ROOT/.openapi-tmp/java/src/main/java/io/xr/lab/shared/api/." "$DEST/io/xr/lab/platform/api/"
rm -rf "$ROOT/.openapi-tmp"

# 生成器已知缺陷修补：oneOf + enum discriminator（AuthState 4 态）下，
# 接口被生成 `String getKind()`，而各变体类返回各自的嵌套 KindEnum —— 返回类型
# 不兼容无法 override（javac 报“未覆盖抽象方法”）。多态由类上的
# @JsonTypeInfo/@JsonSubTypes 注解驱动，接口方法本身无人消费，删掉即净。
sed -i '/^    public String getKind();$/d' "$DEST/io/xr/lab/shared/dto/AuthState.java"

# L1 前置：生成器排版不过 google-java-format（L1 门会拦），
# codegen 末端统一 apply，保证产物落地即 gate-ready（saas 仓是手动补的，这里进脚本）。
mvn -q spotless:apply

# DB - lab-shared SQL SSOT 落地：Flyway replay V001-V013
echo "[gen-shared] step 3/3 - DB: copy shared/sql/migrations/* -> src/main/resources/db/migration/"
# 分叉保护（2026-08-26 prod 502 事故复盘 + 2026-08-26 收敛）：
#   * V014 是永久结构性分叉--本仓演化版直接 ALTER inspection_calculation_methods
#     （VPS flyway history 记录其 checksum，改文件=起崩）；shared 旧版 ALTER
#     inspection_calculation_rules 是 fresh replay 链（emit-schema / sql.replay.test /
#     sync-db 全量重建）的必需环节--两版语义互补，各自不可替换，本地为准、不覆盖。
#   * V015/V017 已逐字节收敛（shared V015=smoke seed、V017=条件式 rename 与本仓相同），
#     豁免清单从 "V014 V015" 缩到 "V014"。
#   * 其余文件：目标已存在且内容不同 = 新分叉 = 直接 abort（防 lab 事故重演，
#     shared 侧改动必须先过「fresh replay 可执行 + flyway checksum 兼容」再进来）。
DIVERGED_VERSIONS="V014"
SHARED_SQL="$SHARED_DIR/sql/migrations"
if [ -d "$SHARED_SQL" ]; then
  mkdir -p "$ROOT/src/main/resources/db/migration"
  for f in "$SHARED_SQL"/V*.sql; do
    [ -e "$f" ] || continue
    ver=$(basename "$f" | cut -d_ -f1)
    if echo "$DIVERGED_VERSIONS" | grep -qw "$ver"; then
      echo "[gen-shared] SKIP diverged migration: $(basename "$f") (local version is authoritative)"
      continue
    fi
    target="$ROOT/src/main/resources/db/migration/$(basename "$f")"
    if [ -e "$target" ] && ! cmp -s "$f" "$target"; then
      echo "[gen-shared] FATAL: migration diverged: $(basename "$f") differs between shared and this repo." >&2
      echo "[gen-shared]          refusing to overwrite (flyway checksum on applied DBs is locked)." >&2
      echo "[gen-shared]          resolve: converge byte-for-byte or add to DIVERGED_VERSIONS with justification." >&2
      exit 1
    fi
    cp "$f" "$target"
  done
  [ -f "$SHARED_SQL/README.md" ] && cp "$SHARED_SQL/README.md" "$ROOT/src/main/resources/db/migration/README.md"
else
  echo "[gen-shared] WARN: $SHARED_SQL not found; DB layer skipped"
fi

echo "[gen-shared] OK"

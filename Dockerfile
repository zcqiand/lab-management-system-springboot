# =============================================================================
# lab-management-system-springboot — 生产镜像
#
#   builder  → mvn package（Spring Boot fat jar）
#   runtime  → eclipse-temurin:21-jre-jammy + app.jar，监听 SERVER_PORT=5205（conventions §6）
#
# 数据库：PostgreSQL（远程）。容器内不持有 DB 文件 —— 运行期必须通过
#         DATABASE_URL / DATABASE_USER / DATABASE_PASSWORD（全家族统一四件套）注入连接串
#         （由 VPS springboot.env 注入，见 deploy/lab-management-system-springboot.sh）。
#
# 端口：容器内 Spring Boot 监听 :5205（conventions §6 端口分段）；VPS nginx 反代到 publish 出的端口（默认 8013）。
#
# 镜像族系（与 saas-springboot 同构）：
#   builder 用 maven:3.9-eclipse-temurin-21, runtime 用 eclipse-temurin:21-jre-jammy
# runtime 选 jammy 而非 alpine：避免 musl libc + native deps 冲突。
# （注意：eclipse-temurin 仓库没有 `-jre-slim` tag —— saas 仓 v0.1.7 踩过，
#  勿再回退到 `-slim` 后缀，那 tag 不在 Docker Hub 上。）
# =============================================================================


# ---------- Stage 1: builder ----------
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app

# 缓存友好的层：先只复制 pom.xml 跑 dependency:go-offline，
# 再 copy src。多数 commit 只动 src, deps 缓存命中。
COPY pom.xml ./
RUN mvn -B -e -ntp -DskipTests dependency:go-offline

COPY src ./src
# fat jar 名 = <artifactId>-<version>.jar（pom 无 finalName 自定义）：
# lab-management-system-springboot-0.1.0-SNAPSHOT.jar
RUN mvn -B -e -ntp -DskipTests package \
 && cp target/lab-management-system-springboot-0.1.0-SNAPSHOT.jar /app/app.jar


# ---------- Stage 2: runtime ----------
FROM eclipse-temurin:21-jre-jammy AS runtime
WORKDIR /app

# jammy 缺 wget —— Docker HEALTHCHECK 需要它探 /actuator/health
RUN apt-get update \
 && apt-get install -y --no-install-recommends wget ca-certificates \
 && rm -rf /var/lib/apt/lists/*

COPY --from=builder /app/app.jar /app/app.jar

# JVM 在容器内堆上限参考 cgroup 内存限额（默认 75%）
ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75.0"
ENV SERVER_PORT=5205
ENV TZ=UTC

EXPOSE 5205

# Spring Boot 冷启动 5-15s @ 小 VPS。probe 走 /actuator/health
# （spring-boot-starter-actuator + management.endpoints.web.exposure.include:health
# 在 application.yml 里开；SecurityConfig 已 permitAll /actuator/**）。
# 如果 servlet 链还没就绪, /actuator/health 会返回 503,
# Docker HEALTHCHECK exit 1 —— fail-loud 行为。
HEALTHCHECK --interval=30s --timeout=5s --start-period=30s --retries=3 \
  CMD wget -q --spider http://127.0.0.1:5205/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

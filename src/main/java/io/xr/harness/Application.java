package io.xr.harness;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Spring Boot 入口。
 *
 * <p>包名固定为 {@code io.xr.harness} 以与 harness 同栈共存。重命名为实际项目根包的 推荐时机：在 {@code
 * docs/functions/function-tree.md} 已经能稳定支撑业务模块前，不必动。
 *
 * <p>业务代码在 {@code io.xr.lab.platform.*}（gen-shared 产物在 io.xr.lab.shared，无组件）， 显式加扫该包--默认扫描只覆盖
 * io.xr.harness.*，漏扫会静默回退到 Spring Boot 默认安全链 （Basic 401），B1 实测踩过。
 */
@SpringBootApplication
@ComponentScan(basePackages = {"io.xr.harness", "io.xr.lab"})
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}

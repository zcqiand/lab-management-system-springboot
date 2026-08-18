package io.xr.harness;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 入口。
 *
 * <p>包名固定为 {@code io.xr.harness} 以与 harness 同栈共存。重命名为实际项目根包的
 * 推荐时机：在 {@code docs/functions/function-tree.md} 已经能稳定支撑业务模块前，不必动。
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

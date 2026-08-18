package io.xr.harness.junit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 声明该测试直接验证的功能子项 ID。
 *
 * <p>与 docs/functions/function-tree.md 的三级 ID 严格对应。Suite 的 L5 门会校验引用完整性。
 *
 * <p><b>契约（机器管不了的部分，靠自觉）：</b>
 *
 * <ul>
 *   <li>只在测试直接验证该子项可观察行为时挂 ID
 *   <li>间接受益的测试不挂
 *   <li>工程设施的测试不挂业务 ID
 *   <li>一个测试挂 3 个以上 ID，通常说明测得太宽
 * </ul>
 *
 * <p>被 {@code @Disabled} / skip 的测试不会进入 trace 的 fns（listener 强制清空）—— "声明覆盖但不执行" = 假绿，在物理上不可能发生。
 *
 * <p>该注解与 {@link HarnessTraceListener} 一同存在于测试 classpath，因为它们只在测试时有意义。 业务代码放回 src/main/java，不应
 * import 这个包。
 *
 * <pre>{@code
 * @Test
 * @Fn({"M01.F01.I14"})
 * void 折扣四舍五入() {
 *     ...
 * }
 * }</pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Fn {
  /** 功能子项 ID 列表。允许一次挂多个，但通常 1 个。 */
  String[] value();
}

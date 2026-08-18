package io.xr.harness.junit;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.TestSource;
import org.junit.platform.engine.support.descriptor.MethodSource;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;

/**
 * 把 {@code @Fn} 标记落成 suite 契约的 {@code .state/trace.json}。
 *
 * <p>仅当 {@code -Dharness.trace=true} 时启用（由 suite 的 trace_cmd 注入）， 避免每次 {@code mvn test} 都覆盖工作树里的
 * trace。
 *
 * <p><b>契约：</b>被 disabled 的测试其 {@code fns} 必须为空数组。listener 在源头抹掉， suite 的契约层会再次复核。这是物理上的不可能性，不是纪律。
 *
 * <p>手写 JSON 输出而不是引入 Jackson：
 *
 * <ul>
 *   <li>spring-boot-starter-test 不传 jackson-databind
 *   <li>输出结构简单（数组/字符串/布尔），少了序列化器
 *   <li>测试 scope 里少一个依赖 = 编译/L2 风险更小
 * </ul>
 */
public final class HarnessTraceListener implements TestExecutionListener {

  private static final String TRACE_FLAG = "harness.trace";
  private static final String TRACE_FILE = ".state/trace.json";

  private final List<String[]> rows = new ArrayList<>(); // [test, fns_csv, inert_bool]
  private boolean enabled;
  private Path projectRoot;

  @Override
  public void testPlanExecutionStarted(TestPlan plan) {
    String flag = System.getProperty(TRACE_FLAG, System.getenv(TRACE_FLAG));
    this.enabled = "true".equalsIgnoreCase(flag);
    this.projectRoot = Paths.get("").toAbsolutePath().normalize();
  }

  @Override
  public void executionFinished(TestIdentifier ident, TestExecutionResult result) {
    if (!enabled || !ident.isTest()) {
      return;
    }
    rows.add(toRow(ident, false));
  }

  @Override
  public void executionSkipped(TestIdentifier ident, String reason) {
    if (!enabled || !ident.isTest()) {
      return;
    }
    rows.add(toRow(ident, true));
  }

  @Override
  public void testPlanExecutionFinished(TestPlan plan) {
    if (!enabled) {
      return;
    }
    try {
      Path out = projectRoot.resolve(TRACE_FILE);
      Files.createDirectories(out.getParent());
      Files.write(out, render().getBytes(StandardCharsets.UTF_8));
    } catch (IOException e) {
      throw new IllegalStateException("Failed to write " + TRACE_FILE, e);
    }
  }

  private String[] toRow(TestIdentifier ident, boolean inert) {
    return new String[] {locatePath(ident), inert ? "" : joinFns(ident), Boolean.toString(inert)};
  }

  /** file + "::" + displayName。多换行参数化测试由 displayName 区分。 */
  private String locatePath(TestIdentifier ident) {
    TestSource src = ident.getSource().orElse(null);
    if (src instanceof MethodSource ms) {
      String fqcn = ms.getClassName();
      String simple = fqcn.substring(fqcn.lastIndexOf('.') + 1);
      int dot = fqcn.lastIndexOf('.');
      String pkgPath = (dot > 0) ? fqcn.substring(0, dot).replace('.', '/') : "";
      String filePath =
          pkgPath.isEmpty()
              ? "src/test/java/" + simple + ".java"
              : "src/test/java/" + pkgPath + "/" + simple + ".java";
      return filePath + "::" + ident.getDisplayName();
    }
    return ident.getUniqueId();
  }

  private String joinFns(TestIdentifier ident) {
    List<String> fns = resolveFns(ident);
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < fns.size(); i++) {
      if (i > 0) {
        sb.append(',');
      }
      sb.append(fns.get(i));
    }
    return sb.toString();
  }

  private List<String> resolveFns(TestIdentifier ident) {
    TestSource src = ident.getSource().orElse(null);
    if (!(src instanceof MethodSource ms)) {
      return List.of();
    }
    try {
      Class<?> klass =
          Class.forName(ms.getClassName(), false, Thread.currentThread().getContextClassLoader());
      Method m = findMethod(klass, ms.getMethodName());
      if (m == null) {
        return List.of();
      }
      Fn fn = m.getAnnotation(Fn.class);
      if (fn == null) {
        return List.of();
      }
      return Arrays.stream(fn.value()).distinct().sorted().toList();
    } catch (ClassNotFoundException e) {
      return List.of();
    }
  }

  private Method findMethod(Class<?> klass, String name) {
    for (Method m : klass.getDeclaredMethods()) {
      if (m.getName().equals(name)) {
        return m;
      }
    }
    Class<?> sup = klass.getSuperclass();
    if (sup != null && sup != Object.class) {
      return findMethod(sup, name);
    }
    return null;
  }

  private String render() {
    StringBuilder sb = new StringBuilder();
    sb.append("{\n  \"schema\": 1,\n  \"tests\": [\n");
    for (int i = 0; i < rows.size(); i++) {
      String[] r = rows.get(i);
      if (i > 0) {
        sb.append(",\n");
      }
      sb.append("    {\"test\": ").append(quote(r[0]));
      sb.append(", \"fns\": ").append(r[1].isEmpty() ? "[]" : quoteArray(r[1]));
      sb.append(", \"inert\": ").append(r[2]);
      sb.append('}');
    }
    sb.append("\n  ]\n}\n");
    return sb.toString();
  }

  /** JSON 字符串字面量。fn IDs 都是 M0x.F0x.I0x，不会有控制字符，但保险起见转义背斜线/引号。 */
  private static String quote(String s) {
    StringBuilder sb = new StringBuilder(s.length() + 2);
    sb.append('"');
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      switch (c) {
        case '\\':
        case '"':
          sb.append('\\').append(c);
          break;
        case '\n':
          sb.append("\\n");
          break;
        case '\r':
          sb.append("\\r");
          break;
        case '\t':
          sb.append("\\t");
          break;
        default:
          sb.append(c);
      }
    }
    sb.append('"');
    return sb.toString();
  }

  /** fn IDs 由逗号拼接 → JSON 数组。 */
  private static String quoteArray(String csv) {
    StringBuilder sb = new StringBuilder();
    sb.append('[');
    String[] parts = csv.split(",", -1);
    for (int i = 0; i < parts.length; i++) {
      if (i > 0) {
        sb.append(", ");
      }
      sb.append(quote(parts[i]));
    }
    sb.append(']');
    return sb.toString();
  }
}

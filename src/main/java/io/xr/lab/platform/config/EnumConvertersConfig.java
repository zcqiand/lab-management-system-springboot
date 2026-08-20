package io.xr.lab.platform.config;

import io.xr.lab.shared.dto.ContractStatus;
import io.xr.lab.shared.dto.FlowStatus;
import io.xr.lab.shared.dto.InspectionParameterSourceType;
import io.xr.lab.shared.dto.InspectionStandardStatus;
import io.xr.lab.shared.dto.OAuthGrantType;
import io.xr.lab.shared.dto.OAuthResponseType;
import java.beans.PropertyEditorSupport;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * codegen enum 的 query 参数按契约值反序列化。
 *
 * <p>OpenAPI codegen 生成的 enum（如 {@code FlowStatus.REVIEW("review")}）的
 * {@code @JsonCreator#fromValue} 只作用于 JSON body；query string 走 Spring 默认的 按枚举名转换（要求 "REVIEW"）。而
 * shared 契约值是小写 "review"（msw / nextjs / 前端 orval 全按契约值发送）。这里给所有出现在 @RequestParam 位置的 enum 注册 {@link
 * PropertyEditor}，按 {@code getValue()} 契约值匹配，大小写不敏感退化到枚举名兜底。
 *
 * <p>新增 enum 进 query 参数时，把它加进 {@link #QUERY_ENUMS} 即可（codegen enum 都有 {@code getValue()}，反射工厂自动适配）。
 */
@ControllerAdvice
@Configuration
public class EnumConvertersConfig {

  /** 出现在 API 接口 query 参数位置的 codegen enum（全量，新增照加）。 */
  private static final Class<?>[] QUERY_ENUMS = {
    OAuthResponseType.class,
    OAuthGrantType.class,
    ContractStatus.class,
    FlowStatus.class,
    InspectionParameterSourceType.class,
    InspectionStandardStatus.class,
  };

  /** 反射取 codegen enum 的 getValue() 做契约值匹配。 */
  static <T extends Enum<T>> Converter<String, T> byContractValue(Class<T> type) {
    Method getValue;
    try {
      getValue = type.getMethod("getValue");
    } catch (NoSuchMethodException e) {
      throw new IllegalStateException(
          type.getSimpleName() + " lacks getValue(): " + e.getMessage(), e);
    }
    return source -> {
      for (T each : type.getEnumConstants()) {
        String contract;
        try {
          contract = (String) getValue.invoke(each);
        } catch (IllegalAccessException | InvocationTargetException e) {
          throw new IllegalStateException("getValue() invoke failed on " + type.getSimpleName(), e);
        }
        if (contract != null && contract.equals(source)) {
          return each;
        }
      }
      // 兜底：枚举名大小写不敏感（如手工传 REVIEW 也认）
      for (T each : type.getEnumConstants()) {
        if (each.name().equalsIgnoreCase(source)) {
          return each;
        }
      }
      throw new IllegalArgumentException("No enum constant " + type.getSimpleName() + "." + source);
    };
  }

  /** 注册到所有 @RequestParam 绑定（ControllerAdvice 的 InitBinder 全局生效）。 */
  @InitBinder
  public void initBinder(WebDataBinder binder) {
    for (Class<?> enumType : QUERY_ENUMS) {
      register(binder, enumType);
    }
  }

  @SuppressWarnings("unchecked")
  private static void register(WebDataBinder binder, Class<?> enumType) {
    // raw Class<?> → Class<T> 的桥接：byContractValue 只需 Enum 子类，擦除后安全
    Class<? extends Enum<?>> typed = (Class<? extends Enum<?>>) enumType;
    Converter<String, ? extends Enum<?>> conv = byContractValueRaw(typed);
    binder.registerCustomEditor(
        enumType,
        new PropertyEditorSupport() {
          @Override
          public void setAsText(String text) throws IllegalArgumentException {
            setValue(conv.convert(text));
          }
        });
  }

  @SuppressWarnings("unchecked")
  private static <T extends Enum<T>> Converter<String, T> byContractValueRaw(
      Class<? extends Enum<?>> type) {
    return byContractValue((Class<T>) type);
  }
}

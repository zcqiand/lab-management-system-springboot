package io.xr.lab.platform.auth.sso;

import io.xr.lab.shared.dto.MenuNode;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * SaasMenuMapper — saas EffectiveMenuNode → lab 契约 MenuNode 的递归映射。
 *
 * <p>字段差异：saas 用 name / icon 可空 / type=group|page；契约是 label / icon 可空。 icon=null 时按 type
 * 兜底（group→"resource"、page→"file"），前端 ICON_MAP 对未知名有 默认图标兜底，不会空白。子树按 sortOrder 升序（saas
 * 已排，这里防御性再排一次）。
 */
@Component
public class SaasMenuMapper {

  /** group 类型节点的默认 icon（与静态 demo 菜单 icon 风格对齐）。 */
  static final String DEFAULT_GROUP_ICON = "resource";

  /** page 类型节点的默认 icon。 */
  static final String DEFAULT_PAGE_ICON = "file";

  public List<MenuNode> map(List<SaasMeClient.SaasMenuNode> roots) {
    if (roots == null) {
      return List.of();
    }
    return roots.stream().sorted(bySortOrder()).map(this::mapNode).toList();
  }

  private MenuNode mapNode(SaasMeClient.SaasMenuNode src) {
    MenuNode dst = new MenuNode();
    dst.setId(src.getId());
    dst.setLabel(src.getName() != null ? src.getName() : src.getCode());
    if (src.getPath() != null) {
      dst.setPath(src.getPath());
    }
    String icon = src.getIcon();
    if (icon == null || icon.isEmpty()) {
      icon = "group".equals(src.getType()) ? DEFAULT_GROUP_ICON : DEFAULT_PAGE_ICON;
    }
    dst.setIcon(icon);
    if (src.getChildren() != null && !src.getChildren().isEmpty()) {
      dst.setChildren(src.getChildren().stream().sorted(bySortOrder()).map(this::mapNode).toList());
    }
    return dst;
  }

  private static Comparator<SaasMeClient.SaasMenuNode> bySortOrder() {
    return Comparator.comparing(
        SaasMeClient.SaasMenuNode::getSortOrder,
        Comparator.nullsLast(Comparator.<Integer>naturalOrder()));
  }
}

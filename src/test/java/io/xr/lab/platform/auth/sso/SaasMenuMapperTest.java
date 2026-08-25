package io.xr.lab.platform.auth.sso;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.xr.lab.platform.auth.sso.SaasMeClient.SaasMenuNode;
import io.xr.lab.shared.dto.MenuNode;
import java.util.List;
import org.junit.jupiter.api.Test;

/** SaasMenuMapper 单测：name→label 映射、icon 兜底、children 递归、sortOrder 排序。 */
class SaasMenuMapperTest {

  private final SaasMenuMapper mapper = new SaasMenuMapper();

  private static SaasMenuNode node(
      String id,
      String name,
      String type,
      String icon,
      Integer sortOrder,
      List<SaasMenuNode> children) {
    SaasMenuNode n = new SaasMenuNode();
    n.setId(id);
    n.setCode(id);
    n.setName(name);
    n.setType(type);
    n.setIcon(icon);
    n.setSortOrder(sortOrder);
    n.setChildren(children);
    return n;
  }

  @Test
  void mapsNameToLabelAndFallsBackIconByType() {
    SaasMenuNode group = node("grp-res", "资源管理", "group", null, 1, null);
    SaasMenuNode page = node("m-lab-dash", "总览", "page", null, 0, null);
    List<MenuNode> result = mapper.map(List.of(group, page));

    assertEquals(2, result.size());
    // 按 sortOrder：page(0) 在前，group(1) 在后
    assertEquals("总览", result.get(0).getLabel());
    assertEquals(SaasMenuMapper.DEFAULT_PAGE_ICON, result.get(0).getIcon());
    assertEquals("资源管理", result.get(1).getLabel());
    assertEquals(SaasMenuMapper.DEFAULT_GROUP_ICON, result.get(1).getIcon());
  }

  @Test
  void sortsBySortOrderAndMapsChildrenRecursively() {
    SaasMenuNode child2 = node("c2", "子2", "page", null, 2, null);
    SaasMenuNode child1 = node("c1", "子1", "page", "Custom", 1, null);
    SaasMenuNode root = node("root", "组", "group", null, 0, List.of(child2, child1));

    List<MenuNode> result = mapper.map(List.of(root));
    assertEquals(1, result.size());
    MenuNode rootOut = result.get(0);
    assertEquals(2, rootOut.getChildren().size());
    // children 按 sortOrder 排序：c1 在前
    assertEquals("c1", rootOut.getChildren().get(0).getId());
    // 显式 icon 保留
    assertEquals("Custom", rootOut.getChildren().get(0).getIcon());
  }

  @Test
  void nullInputReturnsEmpty() {
    assertTrue(mapper.map(null).isEmpty());
  }
}

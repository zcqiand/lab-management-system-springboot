package io.xr.lab.platform.auth.sso;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.xr.lab.platform.auth.sso.SaasMeClient.SaasMenuNode;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * M09.F03.I04 — saas /api/v1/me/menus 返回 Map<appCode, List<EffectiveMenuNode>>（lab-management 视角只取
 * LAB_APP_CODE 的树）。
 *
 * <p>2026-08-28 prod 503 修复：saas MeService.getMyMenus 真实现后，返 Map 而非扁平 ?appCode= 的
 * EffectiveMenuNode[]。
 */
class SaasMeClientListMyMenusTest {

  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  void parsesMapWithAppCodeAndPicksLabManagement() throws Exception {
    // 名称字段是 title（2026-09-14 实测 saas payload；name 从未存在 ——
    // 与 lab-nextjs / lab-aspnetcore 同一轮 /apps 重命名漂移，@JsonProperty("title") 修复）。
    String json =
        "{\"lab-management\":["
            + "{\"id\":\"g\",\"appId\":\"a\",\"parentId\":null,\"code\":\"m-overview\","
            + "\"title\":\"总览\",\"path\":null,\"icon\":null,\"type\":\"group\",\"sortOrder\":1,"
            + "\"children\":[{\"id\":\"p\",\"appId\":\"a\",\"parentId\":\"g\",\"code\":\"m-dashboard\","
            + "\"title\":\"仪表盘\",\"path\":\"/dashboard\",\"icon\":null,\"type\":\"page\",\"sortOrder\":1,"
            + "\"children\":[]}]},"
            + "{\"id\":\"g2\",\"appId\":\"a\",\"parentId\":null,\"code\":\"m-basedata\","
            + "\"title\":\"基础数据\",\"path\":null,\"icon\":null,\"type\":\"group\",\"sortOrder\":2,"
            + "\"children\":[]}"
            + "]}";
    Map<String, List<SaasMenuNode>> map =
        mapper.readValue(json, new TypeReference<Map<String, List<SaasMenuNode>>>() {});
    List<SaasMenuNode> labTree = map.get("lab-management");
    assertEquals(2, labTree.size());
    assertEquals("m-overview", labTree.get(0).getCode());
    assertEquals("总览", labTree.get(0).getName());
    assertEquals(1, labTree.get(0).getChildren().size());
    assertEquals("m-dashboard", labTree.get(0).getChildren().get(0).getCode());
    assertEquals("仪表盘", labTree.get(0).getChildren().get(0).getName());
  }

  @Test
  void parsesMapWithEmptyValueYieldsEmptyTree() throws Exception {
    String json = "{\"lab-management\":[]}";
    Map<String, List<SaasMenuNode>> map =
        mapper.readValue(json, new TypeReference<Map<String, List<SaasMenuNode>>>() {});
    assertTrue(map.get("lab-management").isEmpty());
  }

  @Test
  void parsesMapMissingAppCodeYieldsNull() throws Exception {
    String json = "{\"other-app\":[]}";
    Map<String, List<SaasMenuNode>> map =
        mapper.readValue(json, new TypeReference<Map<String, List<SaasMenuNode>>>() {});
    assertTrue(map.get("lab-management") == null);
  }
}

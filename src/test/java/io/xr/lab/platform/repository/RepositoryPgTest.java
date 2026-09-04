package io.xr.lab.platform.repository;

import static org.assertj.core.api.Assertions.assertThat;

import io.xr.lab.platform.entity.ContractEntity;
import io.xr.lab.platform.entity.SampleReceiptEntity;
import io.xr.lab.shared.dto.ContractStatus;
import io.xr.lab.shared.dto.FlowStatus;
import io.xr.lab.shared.dto.ReceiptResult;
import java.util.UUID;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository 真 PG 切片测试（lab_test）。硬依赖共享 PG —— 连不上即失败，不 skip。
 *
 * <p>背景（2026-09-04 家族事故）：service 测试全 Mockito mock 掉 Repository， 17 个 JPQL @Query 从未被执行过任何 SQL 方言 ——
 * JPQL 语法错/翻译错只会在 prod 首请求爆（lab-aspnetcore v0.2.25/26 同构事故：EF 查询翻译错误 prod 才炸）。 本测试把 filter/summary
 * JPQL 放到真 PG 上执行。
 *
 * <p>注释里两处「真库踩坑，mock 测试测不出」（ContractRepository:14 / SampleReceiptRepository:14 的 IS NULL
 * 判空）正是本测试要永久锁住的回归面。
 *
 * <p>数据隔离：随机 tenant_id + 事务回滚（@DataJpaTest 默认）。 CI 分层：@Tag("pg") 标记 —— CI runner 够不到内网 PG，surefire
 * excludedGroups 排除（ci.yml）；suite gate L4 全量跑（本机可达），真库行为不丢防线。
 */
@Tag("pg")
@DataJpaTest
// 不复用 io.xr.harness.Application：全量 ComponentScan 会拉起 LabJwtSigner 等
// ADR-0019 fail-fast bean（测试 JVM 无 env → "" 转 long 崩）。JPA 切片最小配置：
// 只装配 datasource + Hibernate + repository/entity 扫描（M03 repository 测试面）。
@Import(RepositoryPgTest.JpaSliceConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(
    properties = {
      // env 可覆盖；连不上即测试失败 —— 没有兜底，没有 skip
      "spring.datasource.url=${LAB_TEST_DATABASE_URL:jdbc:postgresql://100.79.128.25:5432/lab_test}",
      "spring.datasource.username=${LAB_TEST_DATABASE_USER:postgres}",
      "spring.datasource.password=${LAB_TEST_DATABASE_PASSWORD:qiand68+++}",
      // lab_test 表结构 = shared SQL SSOT 已建（无 flyway_schema_history），
      // baseline-on-migrate 会 INSERT baseline 行污染库 → 关闭；ddl-auto=validate 保持校验
      "spring.flyway.enabled=false",
      "spring.jpa.hibernate.ddl-auto=validate",
    })
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class RepositoryPgTest {

  /** JPA 切片最小配置：@DataJpaTest 默认找不到上层 @SpringBootConfiguration（主类在 io.xr.harness），显式给扫描根。 */
  @org.springframework.context.annotation.Configuration
  @EntityScan(basePackages = "io.xr.lab.platform.entity")
  @EnableJpaRepositories(basePackages = "io.xr.lab.platform.repository")
  @Import({DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
  static class JpaSliceConfig {}

  private static final String Tenant = "PG-T-" + UUID.randomUUID().toString().substring(0, 8);

  @Autowired ContractRepository contracts;
  @Autowired SampleReceiptRepository receipts;

  /** receipts_category_fk：category_code → inspection_report_names.code（lab_test 种子行）。 */
  private static final String Category = "CAT-SMK-001";

  private ContractEntity contract(String code) {
    ContractEntity c = new ContractEntity();
    c.setId("CTR-PG-" + code);
    c.setContractCode("CTR-" + code);
    c.setProjectName("项目-" + code);
    c.setClientUnit("pg-test-client"); // 以下四个 NOT NULL 是真库约束（mock 测不出）
    c.setConstructionUnit("pg-test-construction");
    c.setWitnessUnit("pg-test-witness-unit");
    c.setWitness("pg-test-witness");
    c.setStatus(ContractStatus.ACTIVE);
    c.setTenantId(Tenant);
    return c;
  }

  private SampleReceiptEntity receipt(String code, FlowStatus status, String category) {
    SampleReceiptEntity r = new SampleReceiptEntity();
    r.setId("RCP-PG-" + code);
    r.setContractId("CTR-PG-1");
    r.setCommissionCode("COMM-" + code);
    r.setCommissionDate("2026-09-0" + (code.length() % 9 + 1));
    r.setCategoryCode(category);
    r.setProjectName("项目-" + code);
    r.setReceivedBy("pg-test");
    r.setSampleSource("source");
    r.setTestCategory("cat");
    r.setFlowStatus(status);
    r.setFlowHistory("[]");
    r.setResult(ReceiptResult.EMPTY);
    r.setTenantId(Tenant);
    return r;
  }

  @Test
  void filter_nullStatusDoesNotCollapseWhere() {
    // ContractRepository:14 踩坑回归锁：status=null 时 `null=''` UNKNOWN 折叠 WHERE
    contracts.save(contract("1"));
    contracts.save(contract("2"));

    var out = contracts.filter(Tenant, "", null);

    assertThat(out).hasSize(2); // mock 测不出：折叠则恒空
  }

  @Test
  void filter_keywordLowerLikeMatchesBothColumns() {
    contracts.save(contract("1")); // CTR-1 / 项目-1

    var byCode = contracts.filter(Tenant, "ctr-1", null);
    var byName = contracts.filter(Tenant, "项目", null);
    var miss = contracts.filter(Tenant, "no-such", null);

    assertThat(byCode).hasSize(1);
    assertThat(byName).hasSize(1);
    assertThat(miss).isEmpty();
  }

  @Test
  void filter_statusEnumEqualityUsesWireValue() {
    contracts.save(contract("1")); // ACTIVE

    assertThat(contracts.filter(Tenant, "", ContractStatus.ACTIVE)).hasSize(1);
    assertThat(contracts.filter(Tenant, "", ContractStatus.ARCHIVED)).isEmpty();
  }

  @Test
  void receiptsFilter_nullableBranchesAndKeyword() {
    contracts.save(contract("1")); // sample_receipts_contract_fk：receipts 须有真父行
    receipts.save(receipt("A", FlowStatus.RECEIVING, Category));
    receipts.save(receipt("B", FlowStatus.TASK_ASSIGNMENT, Category));

    // SampleReceiptRepository:14 踩坑回归锁：flowStatus=null 不折叠
    assertThat(receipts.filter(Tenant, "", null, "")).hasSize(2);
    assertThat(receipts.filter(Tenant, "", FlowStatus.RECEIVING, "")).hasSize(1);
    assertThat(receipts.filter(Tenant, "", null, "comm-a")).hasSize(1); // 大小写不敏
    assertThat(receipts.filter(Tenant, "CTR-PG-1", null, "")).hasSize(2);
  }

  @Test
  void summary_dateRangeAndCategoryFilters() {
    contracts.save(contract("1"));
    receipts.save(receipt("A", FlowStatus.RECEIVING, Category));
    receipts.save(receipt("BB", FlowStatus.RECEIVING, Category));

    var all = receipts.summary(Tenant, "ALL", "", "");
    assertThat(all).hasSize(2);

    var cat = receipts.summary(Tenant, Category, "", "");
    assertThat(cat).hasSize(2); // 两行同 category

    var range = receipts.summary(Tenant, "ALL", "2026-09-02", "2026-12-31");
    assertThat(range)
        .allSatisfy(r -> assertThat(r.getCommissionDate()).isGreaterThanOrEqualTo("2026-09-02"));
  }

  @Test
  void jsonbColumnsRoundTripOnRealPg() {
    // 6 个 jsonb 实体中 sample_receipts 占 4 列 —— H2/InMemory 测不出 jsonb 读写
    contracts.save(contract("1"));
    var r = receipt("J", FlowStatus.RECEIVING, Category);
    r.setJudgmentBasis("[{\"k\":\"v\"}]");
    r.setTestingBasis("{\"basis\":\"pg\"}");
    receipts.saveAndFlush(r);

    var back = receipts.findByTenantIdAndId(Tenant, r.getId()).orElseThrow();
    assertThat(back.getJudgmentBasis()).contains("k");
    assertThat(back.getTestingBasis()).contains("pg");
  }
}

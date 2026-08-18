-- V014__enum_columns_to_text.sql
-- M06.F05 计算规则 + M06.F06 技术要求的 PG 自定义 enum（calculation_algorithm_type /
-- requirement_value_type / requirement_comparison / requirement_judgment_mode /
-- requirement_verification_status）改为 TEXT。原因：Hibernate 6 通过 @JdbcTypeCode(NAMED_ENUM)
-- 持久化时传 enum 常量名（SIMPLE_AVG），与 PG enum 小写标签（simple_avg）不兼容；改 TEXT 后用
-- AttributeConverter 控制存 DTO @JsonValue 同款字符串，PG 不再强约束。
-- 共享 SSOT 不变（shared 仓仍是 CHECK 约束思想），本仓 dev 维护。

-- inspection_calculation_rules.algorithm_type
ALTER TABLE inspection_calculation_rules
    ALTER COLUMN algorithm_type TYPE text USING algorithm_type::text;
DROP TYPE IF EXISTS calculation_algorithm_type CASCADE;

-- inspection_technical_requirements.value_type
ALTER TABLE inspection_technical_requirements
    ALTER COLUMN value_type TYPE text USING value_type::text;
DROP TYPE IF EXISTS requirement_value_type CASCADE;

-- inspection_technical_requirements.comparison
ALTER TABLE inspection_technical_requirements
    ALTER COLUMN comparison TYPE text USING comparison::text;
DROP TYPE IF EXISTS requirement_comparison CASCADE;

-- inspection_technical_requirements.judgment_mode
ALTER TABLE inspection_technical_requirements
    ALTER COLUMN judgment_mode TYPE text USING judgment_mode::text;
DROP TYPE IF EXISTS requirement_judgment_mode CASCADE;

-- inspection_technical_requirements.verification_status
ALTER TABLE inspection_technical_requirements
    ALTER COLUMN verification_status TYPE text USING verification_status::text;
DROP TYPE IF EXISTS requirement_verification_status CASCADE;

COMMENT ON COLUMN inspection_calculation_rules.algorithm_type IS
    '计算算法类型（V014 起改为 text）。DDO @JsonValue 值域：simple_avg / compressive_strength / flexural_strength / steel_tensile / formula / manual / auto_calc_ratio。';
COMMENT ON COLUMN inspection_technical_requirements.value_type IS
    '值类型（V014 text）。值域：numeric / string / range / formula / manual。';
COMMENT ON COLUMN inspection_technical_requirements.comparison IS
    '比较符（V014 text）。值域：≥ / ≤ / = / range / eq。';
COMMENT ON COLUMN inspection_technical_requirements.judgment_mode IS
    '判定模式（V014 text）。值域：automatic / manual。';
COMMENT ON COLUMN inspection_technical_requirements.verification_status IS
    '审核状态（V014 text）。值域：draft / reviewed / verified / rejected。';

-- V017__b5_b6_enums_to_text.sql
-- B5/B6 新增 4 个 PG enum 列也改 TEXT（V014 只覆盖 B2 端的 5 个 enum，漏了 B5 端 M06.F03/F04
-- 的 source_type/status + B6 端 M06.F02/F03/F04/F07 的 qualification_level/inspection_standard_role）。
-- 原因同 V014：Hibernate 6 @Enumerated(STRING) 传 enum 常量名，与 PG enum 标签不一致；
-- @JdbcTypeCode(NAMED_ENUM) 不在我们用的版本里。要 AttributeConverter 显式写 DTO @JsonValue 同款字符串，
-- 列必须是 TEXT（PG 才能强转）。

-- 1. inspection_parameters.source_type
ALTER TABLE inspection_parameters
    ALTER COLUMN source_type TYPE text USING source_type::text;
DROP TYPE IF EXISTS inspection_parameter_source_type CASCADE;

-- 2. inspection_standards.status
ALTER TABLE inspection_standards
    ALTER COLUMN status TYPE text USING status::text;
DROP TYPE IF EXISTS inspection_standard_status CASCADE;

-- 3. inspection_object_parameters.qualification_level
ALTER TABLE inspection_object_parameters
    ALTER COLUMN qualification_level TYPE text USING qualification_level::text;
DROP TYPE IF EXISTS qualification_level CASCADE;

-- 4. inspection_object_standards.role + inspection_report_name_standards.role
ALTER TABLE inspection_object_standards
    ALTER COLUMN role TYPE text USING role::text;
ALTER TABLE inspection_report_name_standards
    ALTER COLUMN role TYPE text USING role::text;
DROP TYPE IF EXISTS inspection_standard_role CASCADE;

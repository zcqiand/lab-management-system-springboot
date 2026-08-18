-- V015__m03_enums_to_text.sql
-- B3 把 3 个 PG enum 改 TEXT（B2 V014 同模式）：
--   contracts.status : contract_status（active / archived）
--   sample_receipts.flow_status : flow_status（8 阶段：receiving / task_assignment / data_entry / review / approval / issuance / archived / completed）
--   sample_receipts.result : receipt_result（pass / fail / '' 空）
-- AttributeConverter 接管枚举值映射（B2 已立 contract/calc/tech 端）。

ALTER TABLE contracts
    ALTER COLUMN status TYPE text USING status::text;
DROP TYPE IF EXISTS contract_status CASCADE;

ALTER TABLE sample_receipts
    ALTER COLUMN flow_status TYPE text USING flow_status::text;
DROP TYPE IF EXISTS flow_status CASCADE;

ALTER TABLE sample_receipts
    ALTER COLUMN result TYPE text USING result::text;
DROP TYPE IF EXISTS receipt_result CASCADE;

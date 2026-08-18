# Frontend Bind Points

> 后端 M05 仪表盘端点（报告汇总 + 仪表盘统计）+ M03.F03 检测记录 CRUD 已与前端 3 仓
> 对接（nextjs / react / vue），此文件用 `@entry` 标记 springboot 仓侧的入口锚点
> （让 L5 引用完整性门知道 UI 入口在前端仓，springboot 只是后端实现）。
>
> 实际 data-fn / @entry 入口：
> - nextjs summary: `output/lab-management-system-nextjs/src/features/summary/SummaryPage.tsx`（`data-fn="M05.F01.I01"`）
> - react summary: `output/lab-management-system-react/src/features/summary/SummaryList.tsx`（`data-fn="M05.F01.I01"`）
> - vue summary: `output/lab-management-system-vue/src/features/summary/SummaryList.vue`（`data-fn="M05.F01.I01"`）
> - nextjs test-records: `output/lab-management-system-nextjs/src/app/api/test-records/route.ts`（`@entry M03.F03.I06 / I08`）

@entry M05.F01.I01
@entry M05.F02.I01
@entry M03.F03.I06
@entry M03.F03.I08

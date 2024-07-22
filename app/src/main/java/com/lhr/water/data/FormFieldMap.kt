package com.lhr.water.data

// 交貨
val deliveryFieldMap = mapOf(
    "ano" to "單位代號",
    "applyNo" to "申請單號",
    "contact" to "聯絡人",
    "contactPhone" to "電話",
    "contractNumber" to "契約字號",
    "date" to "印表日期",
    "dealStatus" to "處理狀態",
    "deliveryDate" to "交貨期限",
    "deliverylocation" to "交貨地點",
    "deptName" to "單位名稱",
    "extendDate1" to "展延期限(一)",
    "extendDate2" to "展延期限(二)",
    "extendDate3" to "展延期限(三)",
    "extendNo1" to "展延文號(一)",
    "extendNo2" to "展延文號(二)",
    "extendNo3" to "展延文號(三)",
    "formNumber" to "交貨通知單號",
    "formId" to "formId",
    "projectNumber" to "工程編號",
    "receiptDept" to "收料單位",
    "reportId" to "報表代號",
    "reportTitle" to "報表標題",
    "sumAddition" to "合計",
    "underwriter" to "承售廠商",
    "updatedAt" to "更新時間",
    "isCreateRNumber" to "產生領料單"
)

val deliveryItemFieldMap = mapOf(
    "itemId" to "itemId",
    "number" to "序號",
    "itemNo" to "標項",
    "batch" to "批次",
    "no" to "契約項次",
    "materialNumber" to "材料編號",
    "materialName" to "材料名稱",
    "materialSpec" to "規格",
    "materialUnit" to "規格",
    "inRequestQuantity" to "請領數量",
    "inApprovedQuantity" to "實領數量",
    "price" to "單價",
    "amount" to "金額",
    "updatedAt" to "更新時間",
    "deliveryStatus" to "分段交貨"
)

// 領料
val receiveFieldMap = mapOf(
    "accountingSubject" to "會計科目",
    "ano" to "單位代號",
    "caseNumber" to "案號",
    "costAllocationUnit" to "成本分攤單位",
    "date" to "印表日期",
    "dealStatus" to "處理狀態",
    "formNumber" to "領料單號",
    "formId" to "formId",
    "issuingUnit" to "發料單位",
    "originalVoucherNumber" to "原憑證字號",
    "pickingDate" to "領料日期",
    "pickingDept" to "領料單位",
    "projectName" to "工程名稱",
    "projectNumber" to "工程編號",
    "reportId" to "報表代號",
    "reportTitle" to "報表標題",
    "systemCode" to "系統代號",
    "updatedAt" to "更新時間",
    "usageCode" to "用途代號",
    "isCreateRNumber" to "產生領料單"
)

val receiveItemFieldMap = mapOf(
    "itemId" to "itemId",
    "number" to "序號",
    "materialNumber" to "材料編號",
    "materialName" to "材料名稱",
    "materialSpec" to "規格",
    "materialUnit" to "單位",
    "outRequestQuantity" to "請領數量",
    "outApprovedQuantity" to "實領數量",
    "price" to "單價",
    "amount" to "金額",
    "updatedAt" to "更新時間"
)

// 調撥
val transferFieldMap = mapOf(
    "contact" to "聯絡人",
    "contactPhone" to "電話",
    "date" to "印表日期",
    "dealStatus" to "處理狀態",
    "formNumber" to "表單單號",
    "formId" to "formId",
    "originalVoucherNumber" to "原憑證字號號",
    "receivingApplyTransferDate" to "請撥日期",
    "receivingApplyTransferNumber" to "收方請撥單號",
    "receivingDept" to "收方單位",
    "receivingLocation" to "收料地點",
    "receivingTransferDate" to "收方撥入日期",
    "receivingTransferNumber" to "收方調撥單號",
    "reportId" to "報表代號",
    "reportTitle" to "報表名稱",
    "requiredDate" to "需用日期",
    "transferDescription" to "調撥或用途說明",
    "transferringDept" to "撥方單位",
    "transferringDeptAno" to "撥方單位代號",
    "transferringTransferDate" to "撥方調撥日期",
    "transferringTransferNumber" to "撥方調撥單號",
    "transferStatus" to "單據狀態",
    "updatedAt" to "更新時間",
    "isCreateRNumber" to "產生領料單"
)

val transferItemFieldMap = mapOf(
    "itemId" to "itemId",
    "number" to "序號",
    "materialNumber" to "材料編號",
    "materialName" to "材料名稱",
    "materialSpec" to "規格",
    "materialUnit" to "單位",
    "outRequestQuantity" to "出庫請求數量",
    "outApprovedQuantity" to "出庫核定數量",
    "inRequestQuantity" to "入庫請求數量",
    "inApprovedQuantity" to "入庫核定數量",
    "approvalResult" to "核定結果",
    "applyNumber" to "請撥單序號",
    "updatedAt" to "更新時間",
)

// 退料
val returningFieldMap = mapOf(
    "accountingSubject" to "會計科目",
    "ano" to "單位代號",
    "costAllocationUnit" to "成本分攤單位",
    "date" to "印表日期",
    "dealStatus" to "處理狀態",
    "formNumber" to "退料單號",
    "formId" to "formId",
    "leadDept" to "領調單位",
    "leadNumber" to "領調單號",
    "originalVoucherNumber" to "原憑證字號",
    "projectName" to "工程名稱",
    "projectNumber" to "工程編號",
    "receiptDept" to "收料單位",
    "receivedDate" to "實收日期",
    "reportId" to "報表代號",
    "reportTitle" to "報表名稱",
    "returnDept" to "退料單位",
    "systemCode" to "系統代號",
    "updatedAt" to "更新時間",
    "usageCode" to "用途代號",
    "isCreateRNumber" to "產生領料單"
)

val returningItemFieldMap = mapOf(
    "itemId" to "itemId",
    "number" to "序號",
    "materialNumber" to "材料編號",
    "materialName" to "材料名稱",
    "materialSpec" to "規格",
    "materialUnit" to "單位",
    "inRequestQuantity" to "入庫請求數量",
    "inApprovedQuantity" to "入庫核定數量",
    "price" to "單價",
    "amount" to "金額",
    "updatedAt" to "更新時間"
)

// 盤點
val inventoryFieldMap = mapOf(
    "actualQuantity" to "實盤數量",
    "approvedDate" to "區處審核日期",
    "checkDate" to "盤點日期",
    "date" to "印表日期",
    "dealStatus" to "處理狀態",
    "deptName" to "單位",
    "formNumber" to "盤點單號",
    "formId" to "盤點單Id",
    "inventoryUnit" to "單位代號",
    "lastUseDate" to "最後使用日期",
    "materialName" to "材料名稱",
    "materialNumber" to "材料編號",
    "materialSpec" to "規格",
    "materialUnit" to "單位",
    "reportId" to "報表代號",
    "reportTitle" to "報表名稱",
    "seq" to "次數",
    "updatedAt" to "更新時間"
)
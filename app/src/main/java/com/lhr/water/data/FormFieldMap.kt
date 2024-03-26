package com.lhr.water.data

// 交貨
val deliveryFieldMap = mapOf(
    "dealStatus" to "處理狀態",
    "reportId" to "報表代號",
    "reportTitle" to "報表標題",
    "id" to "id",
    "date" to "印表日期",
    "dealTime" to "處理時間",
    "ano" to "單位代號",
    "formNumber" to "表單單號",
    "underwriter" to "承售廠商",
    "contractNumber" to "契約字號",
    "deliveryDate" to "交貨期限",
    "extendDate1" to "展延期限(一)",
    "extendDate2" to "展延期限(二)",
    "extendDate3" to "展延期限(三)",
    "receiptDept" to "收料單位",
    "deliverylocation" to "交貨地點",
    "extendNo1" to "展延文號(一)",
    "extendNo2" to "展延文號(二)",
    "extendNo3" to "展延文號(三)",
    "projectNumber" to "工程編號",
    "contact" to "聯絡人",
    "contactPhone" to "電話",
    "applyNo" to "申請單號",
    "sumAddition" to "合計"
)

val deliveryItemFieldMap = mapOf(
    "number" to "序號",
    "itemNo" to "標項",
    "batch" to "批次",
    "no" to "契約項次",
    "materialNumber" to "材料編號",
    "materialName" to "材料名稱",
    "materialSpec" to "規格",
    "materialUnit" to "規格",
    "requestedQuantity" to "請領數量",
    "actualQuantity" to "實領數量",
    "price" to "單價",
    "itemCost" to "複價"
)

// 領料
val pickingFieldMap = mapOf(
    "dealStatus" to "處理狀態",
    "reportId" to "報表代號",
    "reportTitle" to "報表標題",
    "id" to "id",
    "date" to "印表日期",
    "dealTime" to "處理時間",
    "issuingUnit" to "發料單位",
    "pickingDate" to "領料日期",
    "pickingDept" to "領料單位",
    "formNumber" to "表單單號",
    "originalVoucherNumber" to "原憑証字號",
    "costAllocationUnit" to "成本分攤單位",
    "accountingSubject" to "會計科目",
    "systemCode" to "系統代號",
    "usageCode" to "用途代號",
    "projectNumber" to "工程編號",
    "projectName" to "工程名稱",
    "caseNumber" to "案號",
)

val pickingItemFieldMap = mapOf(
    "number" to "序號",
    "materialNumber" to "材料編號",
    "materialName" to "材料名稱",
    "materialSpec" to "規格",
    "materialUnit" to "單位",
    "requestedQuantity" to "請領數量",
    "actualQuantity" to "實領數量",
    "price" to "單價",
    "itemCost" to "複價"
)

// 盤點
val transferFieldMap = mapOf(
    "formNumber" to "表單單號",
    "dealStatus" to "處理狀態",
    "reportId" to "報表代號",
    "reportTitle" to "報表標題",
    "id" to "id",
    "date" to "印表日期",
    "dealTime" to "處理時間",
    "transferringTransferNumber" to "撥方調撥單號",
    "transferringDept" to "撥方單位",
    "originalVoucherNumber" to "原憑證字號",
    "requiredDate" to "需用日期",
    "transferringTransferDate" to "撥方調撥日期",
    "receivingApplyTransferDate" to "收方撥入日期",
    "receivingTransferDate" to "收方撥入日期",
    "receivingDept" to "收方單位",
    "receivingLocation" to "收料地點",
    "contact" to "聯絡人",
    "contactPhone" to "電話",
    "transferDescription" to "調撥或用途說明",
)

val transferItemFieldMap = mapOf(
    "number" to "序號",
    "materialNumber" to "材料編號",
    "materialName" to "材料名稱",
    "materialSpec" to "規格",
    "materialUnit" to "單位",
    "actualQuantity" to "核撥數量",
    "applyNumber" to "請撥序號",
    "allocatedQuantity" to "實撥數量",
    "receivedQuantity" to "實收數量"
)

// 退料
val returningFieldMap = mapOf(
    "dealStatus" to "處理狀態",
    "reportId" to "報表代號",
    "reportTitle" to "報表標題",
    "id" to "id",
    "date" to "印表日期",
    "dealTime" to "處理時間",
    "receiptDept" to "收料單位",
    "leadNumber" to "領調單號",
    "leadDept" to "領調單位",
    "formNumber" to "表單單號",
    "receivedDate" to "實收日期",
    "accountingSubject" to "會計科目",
    "costAllocationUnit" to "成本分攤單位",
    "returnDept" to "退料單位",
    "systemCode" to "系統代號",
    "usageCode" to "用途代號",
    "projectNumber" to "工程編號",
    "projectName" to "工程名稱",
    "originalVoucherNumber" to "原憑證字號",
)

val returningItemFieldMap = mapOf(
    "number" to "序號",
    "materialNumber" to "材料編號",
    "materialName" to "材料名稱",
    "materialSpec" to "規格",
    "materialUnit" to "單位",
    "returnedQuantity" to "退回數量",
    "receivedQuantity" to "實收數量",
    "stockingPrice" to "入庫單價",
    "returnAmt" to "退回金額"
)

// 盤點
val inventoryFieldMap = mapOf(
    "formNumber" to "表單單號",
    "dealStatus" to "處理狀態",
    "reportId" to "報表代號",
    "reportTitle" to "材料盤點單",
    "id" to "id",
    "date" to "印表日期",
    "dealTime" to "處理時間",
    "inventoryUnit" to "單位代號",
    "deptName" to "單位名稱",
    "seq" to "次數",
    "materialNumber" to "材料編號",
    "materialUnit" to "材料單位",
    "materialName" to "材料名稱",
    "materialSpec" to "材料規格",
    "actualQuantity" to "實盤數量",
    "checkDate" to "盤點日期",
    "lastUseDate" to "最後使用日期",
    "approvedDate" to "區處審核日期"
)
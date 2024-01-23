package com.lhr.water.util

val API_BASE: String = "http://localhost:8080"
object MapPageStatus {
    const val RegionPage = "Region"
    const val MapPage = "Map"
}

object TransferStatus {
    const val transferInput = "transferInput"
    const val transferOutput = "transferOutput"
    const val notTransfer = "notTransfer"
}

object FormName {
    const val deliveryFormName = "交貨通知單"
    const val checkFormName = "財務驗收單"
    const val pickingFormName = "材料領料單"
    const val transferFormName = "材料調撥單"
    const val returningFormName = "材料退料單"
    const val inventoryFormName = "盤點"
}


object DealStatus {
    const val waitDeal = "待處理"
    const val nowDeal = "處理中"
    const val completeDeal = "處理完成"
}

object FormField {
    // 交貨通知單欄位
    private val deliveryFormField = arrayOf("deliveryStatus", "reportId", "reportTitle", "date", "formNumber", "underwriter", "ano", "contractNumber", "deliveryDay", "deliveryDate", "extendDate1", "extendDate2", "extendDate3", "receiptDept", "deliverylocation", "extendNo1", "extendNo2", "extendNo3", "projectNumber", "contact", "contactPhone", "applyNo", "sumAddition")
    private val deliveryMaterialField = arrayOf("number", "itemNo", "batch", "no", "materialNumber", "materialName", "materialSpec", "materialUnit", "length", "deliveryQuantity", "receivedQuantity", "price", "itemCost")
    // 材料領料單欄位
    private val pickFormField = arrayOf("dealStatus", "reportId", "reportTitle", "date", "issuingUnit", "pickingDate", "pickingDept", "formNumber", "originalVoucherNumber", "costAllocationUnit", "accountingSubject", "systemCode", "usageCode", "projectNumber", "projectName", "materialReservationNumber", "caseNumber", "remark", "sumAddition", "collectionMethod")
    private val pickMaterialField = arrayOf("number", "materialNumber", "materialName", "materialSpec", "materialUnit", "waterMeterStar", "waterMeterEnd", "requestedQuantity", "actualQuantity", "price", "itemCost")
    // 材料調撥單欄位
    private val transferFormField = arrayOf("dealStatus", "reportId", "reportTitle", "date", "formNumber", "transferDate", "transferringTransferNumber", "receivingTransferNumber", "transferringDept", "originalVoucherNumber", "requiredDate", "transferringTransferDate", "receivingTransferDate", "receivingDept", "receivingLocation", "contact", "contactPhone", "transferDescription")
    private val transferMaterialField = arrayOf("number", "materialNumber", "materialName", "materialSpec", "materialUnit", "waterMeterStar", "waterMeterEnd", "approvedQuantity", "applyNumber", "allocatedQuantity", "receivedQuantity", "remark")
    // 材料退料單欄位
    private val returningFormField = arrayOf("dealStatus", "reportId", "reportTitle", "date", "receiptDept", "formNumber", "leadDept", "returnReceiptNumber", "receivedDate", "accountingSubject", "costAllocationUnit", "materialReservationNumber", "returnDept", "systemCode", "usageCode", "projectNumber", "projectName", "originalVoucherNumber", "sumAddition", "returnReason")
    private val returningMaterialField = arrayOf("number", "materialNumber", "materialName", "materialSpec", "materialUnit", "waterMeterStar", "waterMeterEnd", "returnedQuantity", "receivedQuantity", "stockingPrice", "itemCost")

    private const val deliveryFormName = "交貨通知單"
    private const val pickingFormName = "材料領料單"
    private const val transferFormName = "材料調撥單"
    private const val returningFormName = "材料退料單"

    val formFieldMap: Map<String, Array<Array<String>>> = mapOf(
        deliveryFormName to arrayOf(deliveryFormField, deliveryMaterialField),
        pickingFormName to arrayOf(pickFormField, pickMaterialField),
        transferFormName to arrayOf(transferFormField, transferMaterialField),
        returningFormName to arrayOf(returningFormField, returningMaterialField)
    )

}
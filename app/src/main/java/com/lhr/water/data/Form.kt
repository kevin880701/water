package com.lhr.water.data

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.lhr.water.data.form.FieldName
import org.json.JSONObject

data class Form(
    @FieldName(chinese = "處理狀態", english = "dealStatus")
    val dealStatus: String?,
    @FieldName(chinese = "報表代號", english = "reportId")
    val reportId: String?,
    @FieldName(chinese = "報表ID", english = "id")
    val id: Int?,
    @FieldName(chinese = "報表標題", english = "reportTitle")
    val reportTitle: String?,
    @FieldName(chinese = "印表日期", english = "date")
    val date: String?,
    @FieldName(chinese = "處理時間", english = "dealTime")
    val dealTime: String?,
    @FieldName(chinese = "單位代號", english = "ano")
    val ano: String?,
    @FieldName(chinese = "承售廠商", english = "underwriter")
    val underwriter: String?,
    @FieldName(chinese = "契約字號", english = "contractNumber")
    val contractNumber: String?,
    @FieldName(chinese = "交貨期限", english = "deliveryDate")
    val deliveryDate: String?,
    @FieldName(chinese = "展延期限(一)", english = "extendDate1")
    val extendDate1: String?,
    @FieldName(chinese = "展延期限(二)", english = "extendDate2")
    val extendDate2: String?,
    @FieldName(chinese = "展延期限(三)", english = "extendDate3")
    val extendDate3: String?,
    @FieldName(chinese = "收料單位", english = "receiptDept")
    val receiptDept: String?,
    @FieldName(chinese = "領調單號", english = "leadNumber")
    val leadNumber: String?,
    @FieldName(chinese = "領調單位", english = "leadDept")
    val leadDept: String?,
    @FieldName(chinese = "表單單號", english = "formNumber")
    val formNumber: String?,
    @FieldName(chinese = "實收日期", english = "receivedDate")
    val receivedDate: String?,
    @FieldName(chinese = "會計科目", english = "accountingSubject")
    val accountingSubject: String?,
    @FieldName(chinese = "成本分攤單位", english = "costAllocationUnit")
    val costAllocationUnit: String?,
    @FieldName(chinese = "退料單位", english = "returnDept")
    val returnDept: String?,
    @FieldName(chinese = "系統代號", english = "systemCode")
    val systemCode: String?,
    @FieldName(chinese = "用途代號", english = "usageCode")
    val usageCode: String?,
    @FieldName(chinese = "工程編號", english = "projectNumber")
    val projectNumber: String?,
    @FieldName(chinese = "工程名稱", english = "projectName")
    val projectName: String?,
    @FieldName(chinese = "原憑證字號", english = "originalVoucherNumber")
    val originalVoucherNumber: String?,
    @FieldName(chinese = "交貨狀態", english = "deliveryStatus")
    val deliveryStatus: String?,
    @FieldName(chinese = "交貨地點", english = "deliverylocation")
    val deliverylocation: String?,
    @FieldName(chinese = "交貨地點", english = "deliveryLocation")
    val deliveryLocation: String?,
    @FieldName(chinese = "展延文號(一)", english = "extendNo1")
    val extendNo1: String?,
    @FieldName(chinese = "展延文號(二)", english = "extendNo2")
    val extendNo2: String?,
    @FieldName(chinese = "展延文號(三)", english = "extendNo3")
    val extendNo3: String?,
    @FieldName(chinese = "申請單號", english = "applyNo")
    val applyNo: String?,
    @FieldName(chinese = "合計", english = "sumAddition")
    val sumAddition: String?,
    @FieldName(chinese = "撥方單位", english = "transferringDept")
    val transferringDept: String?,
    @FieldName(chinese = "收方單位", english = "receivingDept")
    val receivingDept: String?,
    @FieldName(chinese = "需用日期", english = "requiredDate")
    val requiredDate: String?,
    @FieldName(chinese = "收方撥入日期", english = "receivingApplyTransferDate")
    val receivingApplyTransferDate: String?,
    @FieldName(chinese = "收方調撥單號", english = "receivingTransferNumber")
    val receivingTransferNumber: String?,
    @FieldName(chinese = "撥方調撥單號", english = "transferringTransferNumber")
    val transferringTransferNumber: String?,
    @FieldName(chinese = "收方請撥單號", english = "receivingApplyTransferNumber")
    val receivingApplyTransferNumber: String?,
    @FieldName(chinese = "撥方調撥日期", english = "transferringTransferDate")
    val transferringTransferDate: String?,
    @FieldName(chinese = "收方撥入日期", english = "receivingTransferDate")
    val receivingTransferDate: String?,
    @FieldName(chinese = "收料地點", english = "receivingLocation")
    val receivingLocation: String?,
    @FieldName(chinese = "聯絡人", english = "contact")
    val contact: String?,
    @FieldName(chinese = "電話", english = "contactPhone")
    val contactPhone: String?,
    @FieldName(chinese = "調撥或用途說明", english = "transferDescription")
    val transferDescription: String?,
    @FieldName(chinese = "貨物資訊", english = "itemDetails")
    val itemDetails: List<ItemDetail>?

) {
    companion object {
        fun formFromJson(json: String): Form {

            val jsonMap: Map<String, Any?> = jacksonObjectMapper().readValue(json.toString())

            return Form(
                dealStatus = jsonMap["dealStatus"] as? String,
                reportId = jsonMap["reportId"] as? String,
                id = jsonMap["id"] as? Int,
                reportTitle = jsonMap["reportTitle"] as? String,
                date = jsonMap["date"] as? String,
                dealTime = jsonMap["dealTime"] as? String,
                ano = jsonMap["ano"] as? String,
                underwriter = jsonMap["underwriter"] as? String,
                contractNumber = jsonMap["contractNumber"] as? String,
                deliveryDate = jsonMap["deliveryDate"] as? String,
                extendDate1 = jsonMap["extendDate1"] as? String,
                extendDate2 = jsonMap["extendDate2"] as? String,
                extendDate3 = jsonMap["extendDate3"] as? String,
                receiptDept = jsonMap["receiptDept"] as? String,
                leadNumber = jsonMap["leadNumber"] as? String,
                leadDept = jsonMap["leadDept"] as? String,
                formNumber = jsonMap["formNumber"] as? String,
                receivedDate = jsonMap["receivedDate"] as? String,
                accountingSubject = jsonMap["accountingSubject"] as? String,
                costAllocationUnit = jsonMap["costAllocationUnit"] as? String,
                returnDept = jsonMap["returnDept"] as? String,
                systemCode = jsonMap["systemCode"] as? String,
                usageCode = jsonMap["usageCode"] as? String,
                projectNumber = jsonMap["projectNumber"] as? String,
                projectName = jsonMap["projectName"] as? String,
                originalVoucherNumber = jsonMap["originalVoucherNumber"] as? String,
                deliveryStatus = jsonMap["deliveryStatus"] as? String,
                deliverylocation = jsonMap["deliverylocation"] as? String,
                deliveryLocation = jsonMap["deliveryLocation"] as? String,
                extendNo1 = jsonMap["extendNo1"] as? String,
                extendNo2 = jsonMap["extendNo2"] as? String,
                extendNo3 = jsonMap["extendNo3"] as? String,
                applyNo = jsonMap["applyNo"] as? String,
                sumAddition = jsonMap["sumAddition"] as? String,
                transferringDept = jsonMap["transferringDept"] as? String,
                receivingDept = jsonMap["receivingDept"] as? String,
                requiredDate = jsonMap["requiredDate"] as? String,
                receivingApplyTransferDate = jsonMap["receivingApplyTransferDate"] as? String,
                receivingTransferNumber = jsonMap["receivingTransferNumber"] as? String,
                transferringTransferNumber = jsonMap["transferringTransferNumber"] as? String,
                receivingApplyTransferNumber = jsonMap["receivingApplyTransferNumber"] as? String,
                transferringTransferDate = jsonMap["transferringTransferDate"] as? String,
                receivingTransferDate = jsonMap["receivingTransferDate"] as? String,
                receivingLocation = jsonMap["receivingLocation"] as? String,
                contact = jsonMap["contact"] as? String,
                contactPhone = jsonMap["contactPhone"] as? String,
                transferDescription = jsonMap["transferDescription"] as? String,
                itemDetails = (jsonMap["itemDetail"] as? List<Map<String, Any?>>)?.map {
                    ItemDetail.fromJson(
                        it
                    )
                }
            )
        }


        fun Form.toJsonString(): String {
            val objectMapper = jacksonObjectMapper()
            return objectMapper.writeValueAsString(this)
        }
    }

}

data class ItemDetail(
    @FieldName(chinese = "序號", english = "number")
    val number: String?,
    @FieldName(chinese = "材料編號", english = "materialNumber")
    val materialNumber: String?,
    @FieldName(chinese = "材料名稱", english = "materialName")
    val materialName: String?,
    @FieldName(chinese = "規格", english = "materialSpec")
    val materialSpec: String?,
    @FieldName(chinese = "單位", english = "materialUnit")
    val materialUnit: String?,
    @FieldName(chinese = "退回數量", english = "returnedQuantity")
    val returnedQuantity: String?,
    @FieldName(chinese = "實收數量", english = "receivedQuantity")
    val receivedQuantity: Int?,
    @FieldName(chinese = "入庫單價", english = "stockingPrice")
    val stockingPrice: String?,
    @FieldName(chinese = "請領數量", english = "requestedQuantity")
    val requestedQuantity: String?,
    @FieldName(chinese = "實領數量", english = "actualQuantity")
    val actualQuantity: Int?,
    @FieldName(chinese = "實撥數量", english = "allocatedQuantity")
    val allocatedQuantity: Int?,
    @FieldName(chinese = "請撥序號", english = "applyNumber")
    val applyNumber: String?,
    @FieldName(chinese = "退回金額", english = "returnAmt")
    val returnAmt: String?
) {
    companion object {
        fun fromJson(json: Map<String, Any?>): ItemDetail {
            return ItemDetail(
                number = json["序號"] as? String ?: null,
                materialNumber = json["材料編號"] as? String ?: null,
                materialName = json["材料名稱"] as? String ?: null,
                materialSpec = json["規格"] as? String ?: null,
                materialUnit = json["單位"] as? String ?: null,
                returnedQuantity = json["退回數量"] as? String ?: null,
                receivedQuantity = json["實收數量"] as? Int,
                stockingPrice = json["入庫單價"] as? String ?: null,
                requestedQuantity = json["請領數量"] as? String ?: null,
                actualQuantity = json["實領數量"] as? Int,
                allocatedQuantity = json["核撥數量"] as? Int,
                applyNumber = json["請撥序號"] as? String ?: null,
                returnAmt = json["退回金額"] as? String ?: null
            )
        }
    }
}
package com.lhr.water.data

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.lhr.water.data.form.FieldName
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties

data class Form(
    @FieldName(chinese = "處理狀態", english = "dealStatus")
    var dealStatus: String? = null,
    @FieldName(chinese = "報表代號", english = "reportId")
    var reportId: String? = null,
    @FieldName(chinese = "報表ID", english = "id")
    var id: String? = null,
    @FieldName(chinese = "報表標題", english = "reportTitle")
    var reportTitle: String? = null,
    @FieldName(chinese = "印表日期", english = "date")
    var date: String? = null,
    @FieldName(chinese = "處理時間", english = "dealTime")
    var dealTime: String? = null,
    @FieldName(chinese = "單位代號", english = "ano")
    var ano: String? = null,
    @FieldName(chinese = "承售廠商", english = "underwriter")
    var underwriter: String? = null,
    @FieldName(chinese = "契約字號", english = "contractNumber")
    var contractNumber: String? = null,
    @FieldName(chinese = "交貨期限", english = "deliveryDate")
    var deliveryDate: String? = null,
    @FieldName(chinese = "展延期限(一)", english = "extendDate1")
    var extendDate1: String? = null,
    @FieldName(chinese = "展延期限(二)", english = "extendDate2")
    var extendDate2: String? = null,
    @FieldName(chinese = "展延期限(三)", english = "extendDate3")
    var extendDate3: String? = null,
    @FieldName(chinese = "收料單位", english = "receiptDept")
    var receiptDept: String? = null,
    @FieldName(chinese = "領調單號", english = "leadNumber")
    var leadNumber: String? = null,
    @FieldName(chinese = "領調單位", english = "leadDept")
    var leadDept: String? = null,
    @FieldName(chinese = "表單單號", english = "formNumber")
    var formNumber: String? = null,
    @FieldName(chinese = "實收日期", english = "receivedDate")
    var receivedDate: String? = null,
    @FieldName(chinese = "會計科目", english = "accountingSubject")
    var accountingSubject: String? = null,
    @FieldName(chinese = "成本分攤單位", english = "costAllocationUnit")
    var costAllocationUnit: String? = null,
    @FieldName(chinese = "退料單位", english = "returnDept")
    var returnDept: String? = null,
    @FieldName(chinese = "系統代號", english = "systemCode")
    var systemCode: String? = null,
    @FieldName(chinese = "用途代號", english = "usageCode")
    var usageCode: String? = null,
    @FieldName(chinese = "工程編號", english = "projectNumber")
    var projectNumber: String? = null,
    @FieldName(chinese = "工程名稱", english = "projectName")
    var projectName: String? = null,
    @FieldName(chinese = "原憑證字號", english = "originalVoucherNumber")
    var originalVoucherNumber: String? = null,
    @FieldName(chinese = "交貨狀態", english = "deliveryStatus")
    var deliveryStatus: String? = null,
    @FieldName(chinese = "交貨地點", english = "deliverylocation")
    var deliverylocation: String? = null,
    @FieldName(chinese = "交貨地點", english = "deliveryLocation")
    var deliveryLocation: String? = null,
    @FieldName(chinese = "展延文號(一)", english = "extendNo1")
    var extendNo1: String? = null,
    @FieldName(chinese = "展延文號(二)", english = "extendNo2")
    var extendNo2: String? = null,
    @FieldName(chinese = "展延文號(三)", english = "extendNo3")
    var extendNo3: String? = null,
    @FieldName(chinese = "申請單號", english = "applyNo")
    var applyNo: String? = null,
    @FieldName(chinese = "合計", english = "sumAddition")
    var sumAddition: String? = null,
    @FieldName(chinese = "撥方單位", english = "transferringDept")
    var transferringDept: String? = null,
    @FieldName(chinese = "收方單位", english = "receivingDept")
    var receivingDept: String? = null,
    @FieldName(chinese = "需用日期", english = "requiredDate")
    var requiredDate: String? = null,
    @FieldName(chinese = "收方撥入日期", english = "receivingApplyTransferDate")
    var receivingApplyTransferDate: String? = null,
    @FieldName(chinese = "收方調撥單號", english = "receivingTransferNumber")
    var receivingTransferNumber: String? = null,
    @FieldName(chinese = "撥方調撥單號", english = "transferringTransferNumber")
    var transferringTransferNumber: String? = null,
    @FieldName(chinese = "收方請撥單號", english = "receivingApplyTransferNumber")
    var receivingApplyTransferNumber: String? = null,
    @FieldName(chinese = "撥方調撥日期", english = "transferringTransferDate")
    var transferringTransferDate: String? = null,
    @FieldName(chinese = "收方撥入日期", english = "receivingTransferDate")
    var receivingTransferDate: String? = null,
    @FieldName(chinese = "收料地點", english = "receivingLocation")
    var receivingLocation: String? = null,
    @FieldName(chinese = "發料單位", english = "issuingUnit")
    var issuingUnit: String? = null,
    @FieldName(chinese = "領料日期", english = "pickingDate")
    var pickingDate: String? = null,
    @FieldName(chinese = "領料單位", english = "pickingDept")
    var pickingDept: String? = null,
    @FieldName(chinese = "案號", english = "caseNumber")
    var caseNumber: String? = null,
    @FieldName(chinese = "聯絡人", english = "contact")
    var contact: String? = null,
    @FieldName(chinese = "電話", english = "contactPhone")
    var contactPhone: String? = null,
    @FieldName(chinese = "調撥或用途說明", english = "transferDescription")
    var transferDescription: String? = null,
    @FieldName(chinese = "貨物資訊", english = "itemDetails")
    var itemDetails: List<ItemDetail>? = null

) {
    companion object {
        fun formFromJson(json: String): Form {

            var jsonMap: Map<String, Any?> = jacksonObjectMapper().readValue(json.toString())

            return Form(
                dealStatus = jsonMap["dealStatus"] as? String,
                reportId = jsonMap["reportId"] as? String,
                id = jsonMap["id"] as? String,
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
                issuingUnit = jsonMap["issuingUnit"] as? String,
                pickingDate = jsonMap["pickingDate"] as? String,
                pickingDept = jsonMap["pickingDept"] as? String,
                caseNumber = jsonMap["caseNumber"] as? String,
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
            var objectMapper = jacksonObjectMapper()

            var formMap = mutableMapOf<String, Any?>(
                "dealStatus" to dealStatus,
                "reportId" to reportId,
                "id" to id,
                "reportTitle" to reportTitle,
                "date" to date,
                "dealTime" to dealTime,
                "ano" to ano,
                "underwriter" to underwriter,
                "contractNumber" to contractNumber,
                "deliveryDate" to deliveryDate,
                "extendDate1" to extendDate1,
                "extendDate2" to extendDate2,
                "extendDate3" to extendDate3,
                "receiptDept" to receiptDept,
                "leadNumber" to leadNumber,
                "leadDept" to leadDept,
                "formNumber" to formNumber,
                "receivedDate" to receivedDate,
                "accountingSubject" to accountingSubject,
                "costAllocationUnit" to costAllocationUnit,
                "returnDept" to returnDept,
                "systemCode" to systemCode,
                "usageCode" to usageCode,
                "projectNumber" to projectNumber,
                "projectName" to projectName,
                "originalVoucherNumber" to originalVoucherNumber,
                "deliveryStatus" to deliveryStatus,
                "deliverylocation" to deliverylocation,
                "deliveryLocation" to deliveryLocation,
                "extendNo1" to extendNo1,
                "extendNo2" to extendNo2,
                "extendNo3" to extendNo3,
                "applyNo" to applyNo,
                "sumAddition" to sumAddition,
                "transferringDept" to transferringDept,
                "receivingDept" to receivingDept,
                "requiredDate" to requiredDate,
                "receivingApplyTransferDate" to receivingApplyTransferDate,
                "receivingTransferNumber" to receivingTransferNumber,
                "transferringTransferNumber" to transferringTransferNumber,
                "receivingApplyTransferNumber" to receivingApplyTransferNumber,
                "transferringTransferDate" to transferringTransferDate,
                "receivingTransferDate" to receivingTransferDate,
                "receivingLocation" to receivingLocation,
                "issuingUnit" to issuingUnit,
                "pickingDate" to pickingDate,
                "pickingDept" to pickingDept,
                "caseNumber" to caseNumber,
                "contact" to contact,
                "contactPhone" to contactPhone,
                "transferDescription" to transferDescription,
                "itemDetail" to itemDetails?.map {
                    it.toJsonMap()
                }
            )
            return objectMapper.writeValueAsString(formMap)
        }


        fun getEnglishFieldName(chineseFieldName: String): String? {
            val formProperties = Form::class.memberProperties
            for (property in formProperties) {
                val fieldNameAnnotation = property.findAnnotation<FieldName>()
                if (fieldNameAnnotation != null && fieldNameAnnotation.chinese == chineseFieldName) {
                    return fieldNameAnnotation.english
                }
            }
            return null
        }
    }
}

data class ItemDetail(
    @FieldName(chinese = "序號", english = "number")
    var number: String?,
    @FieldName(chinese = "標項", english = "itemNo")
    var itemNo: String?,
    @FieldName(chinese = "批次", english = "batch")
    var batch: String?,
    @FieldName(chinese = "契約項次", english = "no")
    var no: String?,
    @FieldName(chinese = "材料編號", english = "materialNumber")
    var materialNumber: String?,
    @FieldName(chinese = "材料名稱", english = "materialName")
    var materialName: String?,
    @FieldName(chinese = "規格", english = "materialSpec")
    var materialSpec: String?,
    @FieldName(chinese = "單位", english = "materialUnit")
    var materialUnit: String?,
    @FieldName(chinese = "發出數量", english = "deliveryQuantity")
    var deliveryQuantity: String?,
    @FieldName(chinese = "實收數量", english = "receivedQuantity")
    var receivedQuantity: Int?,
    @FieldName(chinese = "單價", english = "price")
    var price: String?,
    @FieldName(chinese = "複價", english = "itemCost")
    var itemCost: String?,
    @FieldName(chinese = "請領數量", english = "requestedQuantity")
    var requestedQuantity: String?,
    @FieldName(chinese = "實領數量", english = "actualQuantity")
    var actualQuantity: Int?,
    @FieldName(chinese = "核撥數量", english = "allocatedQuantity")
    var allocatedQuantity: Int?,
    @FieldName(chinese = "請撥序號", english = "applyNumber")
    var applyNumber: String?,
    @FieldName(chinese = "退回數量", english = "returnedQuantity")
    var returnedQuantity: String?,
    @FieldName(chinese = "入庫單價", english = "stockingPrice")
    var stockingPrice: String?,
    @FieldName(chinese = "退回金額", english = "returnAmt")
    var returnAmt: String?
) {

    fun toJsonMap(): Map<String, Any?> {
        return mapOf(
            "number" to number,
            "itemNo" to itemNo,
            "batch" to batch,
            "no" to no,
            "materialNumber" to materialNumber,
            "materialName" to materialName,
            "materialSpec" to materialSpec,
            "materialUnit" to materialUnit,
            "deliveryQuantity" to deliveryQuantity,
            "receivedQuantity" to receivedQuantity,
            "price" to price,
            "itemCost" to itemCost,
            "requestedQuantity" to requestedQuantity,
            "actualQuantity" to actualQuantity,
            "allocatedQuantity" to allocatedQuantity,
            "applyNumber" to applyNumber,
            "returnedQuantity" to returnedQuantity,
            "stockingPrice" to stockingPrice,
            "returnAmt" to returnAmt
        )
    }

    companion object {
        fun fromJson(json: Map<String, Any?>): ItemDetail {
            return ItemDetail(
                number = json["number"] as? String ?: null,
                itemNo = json["itemNo"] as? String ?: null,
                batch = json["batch"] as? String ?: null,
                no = json["no"] as? String ?: null,
                materialNumber = json["materialNumber"] as? String ?: null,
                materialName = json["materialName"] as? String ?: null,
                materialSpec = json["materialSpec"] as? String ?: null,
                materialUnit = json["materialUnit"] as? String ?: null,
                deliveryQuantity = json["deliveryQuantity"] as? String ?: null,
                receivedQuantity = json["receivedQuantity"] as? Int,
                price = json["price"] as? String ?: null,
                itemCost = json["itemCost"] as? String ?: null,
                requestedQuantity = json["requestedQuantity"] as? String ?: null,
                actualQuantity = json["actualQuantity"] as? Int,
                allocatedQuantity = json["allocatedQuantity"] as? Int,
                applyNumber = json["applyNumber"] as? String ?: null,
                returnedQuantity = json["returnedQuantity"] as? String ?: null,
                stockingPrice = json["stockingPrice"] as? String ?: null,
                returnAmt = json["returnAmt"] as? String ?: null
            )
        }

        fun ItemDetail.toJsonString(): String {
            var objectMapper = jacksonObjectMapper()

            var itemDetailMap = mutableMapOf<String, Any?>(
                "number" to number,
                "itemNo" to itemNo,
                "batch" to batch,
                "no" to no,
                "materialNumber" to materialNumber,
                "materialName" to materialName,
                "materialSpec" to materialSpec,
                "materialUnit" to materialUnit,
                "deliveryQuantity" to deliveryQuantity,
                "receivedQuantity" to receivedQuantity,
                "price" to price,
                "itemCost" to itemCost,
                "requestedQuantity" to requestedQuantity,
                "actualQuantity" to actualQuantity,
                "allocatedQuantity" to allocatedQuantity,
                "applyNumber" to applyNumber,
                "returnedQuantity" to returnedQuantity,
                "stockingPrice" to stockingPrice,
                "returnAmt" to returnAmt
            )
            return objectMapper.writeValueAsString(itemDetailMap)
        }
    }
}
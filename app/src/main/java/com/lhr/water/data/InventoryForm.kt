package com.lhr.water.data

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.lhr.water.data.form.FieldName

data class InventoryForm(
    @FieldName(chinese = "處理狀態", english = "dealStatus")
    var dealStatus: String? = null,
    @FieldName(chinese = "報表代號", english = "reportId")
    var reportId: String? = null,
    @FieldName(chinese = "報表ID", english = "id")
    var id: String? = null,
    @FieldName(chinese = "報表標題", english = "reportTitle")
    var reportTitle: String? = null,
    @FieldName(chinese = "表單單號", english = "formNumber")
    var formNumber: String? = null,
    @FieldName(chinese = "印表日期", english = "date")
    var date: String? = null,
    @FieldName(chinese = "處理時間", english = "dealTime")
    var dealTime: String? = null,
    @FieldName(chinese = "單位代號", english = "inventoryUnit")
    var inventoryUnit: String? = null,
    @FieldName(chinese = "單位名稱", english = "deptName")
    var deptName: String? = null,
    @FieldName(chinese = "次數", english = "seq")
    var seq: String? = null,
    @FieldName(chinese = "材料編號", english = "materialNumber")
    var materialNumber: String? = null,
    @FieldName(chinese = "材料名稱", english = "materialName")
    var materialName: String? = null,
    @FieldName(chinese = "規格", english = "materialSpec")
    var materialSpec: String? = null,
    @FieldName(chinese = "單位", english = "materialUnit")
    var materialUnit: String? = null,
    @FieldName(chinese = "實領數量", english = "actualQuantity")
    var actualQuantity: Int? = null,
    @FieldName(chinese = "盤點日期", english = "checkDate")
    var checkDate: String? = null,
    @FieldName(chinese = "最後使用日期", english = "lastUseDate")
    var lastUseDate: String? = null,
    @FieldName(chinese = "區處審核日期", english = "approvedDate")
    var approvedDate: String? = null,
) {
    companion object {
        fun formInventoryFormJson(json: String): InventoryForm {

            var jsonMap: Map<String, Any?> = jacksonObjectMapper().readValue(json.toString())

            return InventoryForm(
                dealStatus = jsonMap["dealStatus"] as? String,
                reportId = jsonMap["reportId"] as? String,
                id = jsonMap["id"] as? String,
                reportTitle = jsonMap["reportTitle"] as? String,
                formNumber = jsonMap["formNumber"] as? String,
                date = jsonMap["date"] as? String,
                dealTime = jsonMap["dealTime"] as? String,
                inventoryUnit = jsonMap["inventoryUnit"] as? String,
                deptName = jsonMap["deptName"] as? String,
                seq = jsonMap["seq"] as? String,
                materialNumber = jsonMap["materialNumber"] as? String,
                materialName = jsonMap["materialName"] as? String,
                materialSpec = jsonMap["materialSpec"] as? String,
                materialUnit = jsonMap["materialUnit"] as? String,
                actualQuantity = jsonMap["actualQuantity"] as? Int,
                checkDate = jsonMap["checkDate"] as? String,
                lastUseDate = jsonMap["lastUseDate"] as? String,
                approvedDate = jsonMap["approvedDate"] as? String,
            )
        }

        fun InventoryForm.toJsonString(): String {
            var objectMapper = jacksonObjectMapper()

            var formMap = mutableMapOf<String, Any?>(
                "dealStatus" to dealStatus,
                "reportId" to reportId,
                "id" to id,
                "reportTitle" to reportTitle,
                "approvedDate" to approvedDate,
                "date" to date,
                "dealTime" to dealTime,
                "inventoryUnit" to inventoryUnit,
                "deptName" to deptName,
                "seq" to seq,
                "materialNumber" to materialNumber,
                "materialName" to materialName,
                "materialSpec" to materialSpec,
                "materialUnit" to materialUnit,
                "actualQuantity" to actualQuantity,
                "checkDate" to checkDate,
                "lastUseDate" to lastUseDate,
                "formNumber" to formNumber,
            )
            return objectMapper.writeValueAsString(formMap)
        }
    }
}
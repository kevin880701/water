package com.lhr.water.data.form

import com.google.gson.annotations.SerializedName

data class InventoryForm(
    @FieldName(chinese = "表單單號", english = "formNumber")
    @SerializedName("formNumber")
    val formNumber: String,

    @FieldName(chinese = "處理狀態", english = "dealStatus")
    @SerializedName("dealStatus")
    val dealStatus: String,

    @FieldName(chinese = "報表代號", english = "reportId")
    @SerializedName("reportId")
    val reportId: String,

    @FieldName(chinese = "報表標題", english = "reportTitle")
    @SerializedName("reportTitle")
    val reportTitle: String,

    @FieldName(chinese = "日期", english = "date")
    @SerializedName("date")
    val date: String,

    @FieldName(chinese = "處理時間", english = "dealTime")
    @SerializedName("dealTime")
    val dealTime: String,

    @FieldName(chinese = "庫存單位", english = "inventoryUnit")
    @SerializedName("inventoryUnit")
    val inventoryUnit: String,

    @FieldName(chinese = "部門名稱", english = "deptName")
    @SerializedName("deptName")
    val deptName: String,

    @FieldName(chinese = "序列", english = "seq")
    @SerializedName("seq")
    val seq: String,

    @FieldName(chinese = "物料編號", english = "materialNumber")
    @SerializedName("materialNumber")
    val materialNumber: String,

    @FieldName(chinese = "物料名稱", english = "materialName")
    @SerializedName("materialName")
    val materialName: String,

    @FieldName(chinese = "物料規格", english = "materialSpec")
    @SerializedName("materialSpec")
    val materialSpec: String,

    @FieldName(chinese = "物料單位", english = "materialUnit")
    @SerializedName("materialUnit")
    val materialUnit: String,

    @FieldName(chinese = "實際數量", english = "actualQuantity")
    @SerializedName("actualQuantity")
    val actualQuantity: Int,

    @FieldName(chinese = "檢查日期", english = "checkDate")
    @SerializedName("checkDate")
    val checkDate: String,

    @FieldName(chinese = "最後使用日期", english = "lastUseDate")
    @SerializedName("lastUseDate")
    val lastUseDate: String,

    @FieldName(chinese = "核准日期", english = "approvedDate")
    @SerializedName("approvedDate")
    val approvedDate: String
)
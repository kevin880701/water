package com.lhr.water.data.form

import com.google.gson.annotations.SerializedName

data class ReceiveForm(
    @FieldName(chinese = "表單單號", english = "formNumber")
    @SerializedName("formNumber")
    override val formNumber: String,

    @FieldName(chinese = "處理狀態", english = "dealStatus")
    @SerializedName("dealStatus")
    override val dealStatus: String,

    @FieldName(chinese = "報表代號", english = "reportId")
    @SerializedName("reportId")
    override val reportId: String,

    @FieldName(chinese = "報表標題", english = "reportTitle")
    @SerializedName("reportTitle")
    override val reportTitle: String,

    @FieldName(chinese = "日期", english = "date")
    @SerializedName("date")
    override val date: String,

    @FieldName(chinese = "處理時間", english = "dealTime")
    @SerializedName("dealTime")
    override val dealTime: String,

    @FieldName(chinese = "發料單位", english = "issuingUnit")
    @SerializedName("issuingUnit")
    val issuingUnit: String?,

    @FieldName(chinese = "領料日期", english = "pickingDate")
    @SerializedName("pickingDate")
    val pickingDate: String?,

    @FieldName(chinese = "領料單位", english = "pickingDept")
    @SerializedName("pickingDept")
    val pickingDept: String?,

    @FieldName(chinese = "原憑證字號", english = "originalVoucherNumber")
    @SerializedName("originalVoucherNumber")
    val originalVoucherNumber: String?,

    @FieldName(chinese = "成本分攤單位", english = "costAllocationUnit")
    @SerializedName("costAllocationUnit")
    val costAllocationUnit: String?,

    @FieldName(chinese = "會計科目", english = "accountingSubject")
    @SerializedName("accountingSubject")
    val accountingSubject: String?,

    @FieldName(chinese = "系統代號", english = "systemCode")
    @SerializedName("systemCode")
    val systemCode: String?,

    @FieldName(chinese = "用途代號", english = "usageCode")
    @SerializedName("usageCode")
    val usageCode: String?,

    @FieldName(chinese = "工程編號", english = "projectNumber")
    @SerializedName("projectNumber")
    val projectNumber: String?,

    @FieldName(chinese = "工程名稱", english = "projectName")
    @SerializedName("projectName")
    val projectName: String?,

    @FieldName(chinese = "案號", english = "caseNumber")
    @SerializedName("caseNumber")
    val caseNumber: String?,

    @FieldName(chinese = "貨物資訊", english = "itemDetail")
    @SerializedName("itemDetail")
    override val itemDetails: List<ReceiveItemDetail>
) : BaseForm()

data class ReceiveItemDetail(
    @FieldName(chinese = "序號", english = "number")
    @SerializedName("number")
    override val number: String,

    @FieldName(chinese = "材料編號", english = "materialNumber")
    @SerializedName("materialNumber")
    override val materialNumber: String,

    @FieldName(chinese = "材料名稱", english = "materialName")
    @SerializedName("materialName")
    override val materialName: String,

    @FieldName(chinese = "規格", english = "materialSpec")
    @SerializedName("materialSpec")
    override val materialSpec: String,

    @FieldName(chinese = "單位", english = "materialUnit")
    @SerializedName("materialUnit")
    override val materialUnit: String,

    @FieldName(chinese = "請領數量", english = "requestedQuantity")
    @SerializedName("requestedQuantity")
    val requestedQuantity: Int?,

    @FieldName(chinese = "實領數量", english = "actualQuantity")
    @SerializedName("actualQuantity")
    val actualQuantity: Int?,

    @FieldName(chinese = "單價", english = "price")
    @SerializedName("price")
    val price: String?,

    @FieldName(chinese = "複價", english = "itemCost")
    @SerializedName("itemCost")
    val itemCost: String?
): BaseItem()
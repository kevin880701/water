package com.lhr.water.data.form

import com.google.gson.annotations.SerializedName

data class TransferForm(
    @FieldName(chinese = "表單單號", english = "formNumber")
    @SerializedName("formNumber")
    override val formNumber: String,

    @FieldName(chinese = "報表代號", english = "reportId")
    @SerializedName("reportId")
    override val reportId: String,

    @FieldName(chinese = "報表標題", english = "reportTitle")
    @SerializedName("reportTitle")
    override val reportTitle: String,

    @FieldName(chinese = "處理狀態", english = "dealStatus")
    @SerializedName("dealStatus")
    override val dealStatus: String,

    @FieldName(chinese = "日期", english = "date")
    @SerializedName("date")
    override val date: String,

    @FieldName(chinese = "處理時間", english = "dealTime")
    @SerializedName("dealTime")
    override val dealTime: String,

    @FieldName(chinese = "撥方單位", english = "transferringDept")
    @SerializedName("transferringDept")
    val transferringDept: String?,

    @FieldName(chinese = "收方單位", english = "receivingDept")
    @SerializedName("receivingDept")
    val receivingDept: String?,

    @FieldName(chinese = "收料地點", english = "receivingLocation")
    @SerializedName("receivingLocation")
    val receivingLocation: String?,

    @FieldName(chinese = "收方撥入日期", english = "receivingTransferDate")
    @SerializedName("receivingTransferDate")
    val receivingTransferDate: String?,

    @FieldName(chinese = "收方調撥申請日期", english = "receivingApplyTransferDate")
    @SerializedName("receivingApplyTransferDate")
    val receivingApplyTransferDate: String?,

    @FieldName(chinese = "收方請撥單號", english = "receivingApplyTransferNumber")
    @SerializedName("receivingApplyTransferNumber")
    val receivingApplyTransferNumber: String?,

    @FieldName(chinese = "收方調撥單號", english = "receivingTransferNumber")
    @SerializedName("receivingTransferNumber")
    val receivingTransferNumber: String?,

    @FieldName(chinese = "需用日期", english = "requiredDate")
    @SerializedName("requiredDate")
    val requiredDate: String?,

    @FieldName(chinese = "撥方調撥單號", english = "transferringTransferNumber")
    @SerializedName("transferringTransferNumber")
    val transferringTransferNumber: String?,

    @FieldName(chinese = "原憑證字號", english = "originalVoucherNumber")
    @SerializedName("originalVoucherNumber")
    val originalVoucherNumber: String?,

    @FieldName(chinese = "撥方調撥日期", english = "transferringTransferDate")
    @SerializedName("transferringTransferDate")
    val transferringTransferDate: String?,

    @FieldName(chinese = "聯絡人", english = "contact")
    @SerializedName("contact")
    val contact: String?,

    @FieldName(chinese = "電話", english = "contactPhone")
    @SerializedName("contactPhone")
    val contactPhone: String?,

    @FieldName(chinese = "調撥或用途說明", english = "transferDescription")
    @SerializedName("transferDescription")
    val transferDescription: String?,

    @FieldName(chinese = "貨物資訊", english = "itemDetail")
    @SerializedName("itemDetail")
    override val itemDetails: List<TransferItemDetail>
) : BaseForm()

data class TransferItemDetail(
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

    @FieldName(chinese = "實領數量", english = "actualQuantity")
    @SerializedName("actualQuantity")
    val actualQuantity: Int?,

    @FieldName(chinese = "請撥序號", english = "applyNumber")
    @SerializedName("applyNumber")
    val applyNumber: Int?,

    @FieldName(chinese = "核撥數量", english = "allocatedQuantity")
    @SerializedName("allocatedQuantity")
    val allocatedQuantity: Int?,

    @FieldName(chinese = "實收數量", english = "receivedQuantity")
    @SerializedName("receivedQuantity")
    val receivedQuantity: Int?
) : BaseItem()
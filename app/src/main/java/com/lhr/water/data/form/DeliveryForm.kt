package com.lhr.water.data.form

import com.google.gson.annotations.SerializedName

data class DeliveryForm(
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

    @FieldName(chinese = "印表日期", english = "date")
    @SerializedName("date")
    override val date: String,

    @FieldName(chinese = "處理時間", english = "dealTime")
    @SerializedName("dealTime")
    override val dealTime: String,

    @FieldName(chinese = "單位代號", english = "ano")
    @SerializedName("ano")
    val ano: String?,

    @FieldName(chinese = "承售廠商", english = "underwriter")
    @SerializedName("underwriter")
    val underwriter: String?,

    @FieldName(chinese = "交貨日", english = "deliveryDay")
    @SerializedName("deliveryDay")
    val deliveryDay: String?,

    @FieldName(chinese = "契約字號", english = "contractNumber")
    @SerializedName("contractNumber")
    val contractNumber: String?,

    @FieldName(chinese = "交貨日期", english = "deliveryDate")
    @SerializedName("deliveryDate")
    val deliveryDate: String?,

    @FieldName(chinese = "展延期限(一)", english = "extendDate1")
    @SerializedName("extendDate1")
    val extendDate1: String?,

    @FieldName(chinese = "展延期限(二)", english = "extendDate2")
    @SerializedName("extendDate2")
    val extendDate2: String?,

    @FieldName(chinese = "展延期限(三)", english = "extendDate3")
    @SerializedName("extendDate3")
    val extendDate3: String?,

    @FieldName(chinese = "收料單位", english = "receiptDept")
    @SerializedName("receiptDept")
    val receiptDept: String?,

    @FieldName(chinese = "交貨地點", english = "deliverylocation")
    @SerializedName("deliverylocation")
    val deliverylocation: String?,

    @FieldName(chinese = "交貨狀態", english = "deliveryStatus")
    @SerializedName("deliveryStatus")
    val deliveryStatus: String?,

    @FieldName(chinese = "展延文號(一)", english = "extendNo1")
    @SerializedName("extendNo1")
    val extendNo1: String?,

    @FieldName(chinese = "展延文號(二)", english = "extendNo2")
    @SerializedName("extendNo2")
    val extendNo2: String?,

    @FieldName(chinese = "展延文號(三)", english = "extendNo3")
    @SerializedName("extendNo3")
    val extendNo3: String?,

    @FieldName(chinese = "工程編號", english = "projectNumber")
    @SerializedName("projectNumber")
    val projectNumber: String?,

    @FieldName(chinese = "聯絡人", english = "contact")
    @SerializedName("contact")
    val contact: String?,

    @FieldName(chinese = "電話", english = "contactPhone")
    @SerializedName("contactPhone")
    val contactPhone: String?,

    @FieldName(chinese = "申請單號", english = "applyNo")
    @SerializedName("applyNo")
    val applyNo: String?,

    @FieldName(chinese = "貨物資訊", english = "itemDetail")
    @SerializedName("itemDetail")
    override val itemDetails: ArrayList<DeliveryItemDetail>,

    @FieldName(chinese = "合計", english = "sumAddition")
    @SerializedName("sumAddition")
    val sumAddition: String?
) : BaseForm()

data class DeliveryItemDetail(
    @FieldName(chinese = "序號", english = "number")
    @SerializedName("number")
    override val number: String,

    @FieldName(chinese = "標項", english = "itemNo")
    @SerializedName("itemNo")
    val itemNo: String?,

    @FieldName(chinese = "批次", english = "batch")
    @SerializedName("batch")
    val batch: String?,

    @FieldName(chinese = "契約項次", english = "no")
    @SerializedName("no")
    val no: String?,

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

    @FieldName(chinese = "發出數量", english = "deliveryQuantity")
    @SerializedName("deliveryQuantity")
    val deliveryQuantity: Int?,

    @FieldName(chinese = "實收數量", english = "receivedQuantity")
    @SerializedName("receivedQuantity")
    val receivedQuantity: Int?,

    @FieldName(chinese = "單價", english = "price")
    @SerializedName("price")
    val price: String?,

    @FieldName(chinese = "複價", english = "itemCost")
    @SerializedName("itemCost")
    val itemCost: String?
) : BaseItem()
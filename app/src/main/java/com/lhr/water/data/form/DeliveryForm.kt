package com.lhr.water.data.form

import com.google.gson.annotations.SerializedName

data class DeliveryForm(
    @SerializedName("formNumber")
    override val formNumber: String,

    @SerializedName("dealStatus")
    override val dealStatus: String,

    @SerializedName("reportId")
    override val reportId: String,

    @SerializedName("reportTitle")
    override val reportTitle: String,

    @SerializedName("date")
    override val date: String,

    @SerializedName("dealTime")
    override val dealTime: String,

    @SerializedName("ano")
    val ano: String?,

    @SerializedName("underwriter")
    val underwriter: String?,

    @SerializedName("deliveryDay")
    val deliveryDay: String?,

    @SerializedName("contractNumber")
    val contractNumber: String?,

    @SerializedName("deliveryDate")
    val deliveryDate: String?,

    @SerializedName("extendDate1")
    val extendDate1: String?,

    @SerializedName("extendDate2")
    val extendDate2: String?,

    @SerializedName("extendDate3")
    val extendDate3: String?,

    @SerializedName("receiptDept")
    val receiptDept: String?,

    @SerializedName("deliverylocation")
    val deliverylocation: String?,

    @SerializedName("deliveryStatus")
    val deliveryStatus: String?,

    @SerializedName("extendNo1")
    val extendNo1: String?,

    @SerializedName("extendNo2")
    val extendNo2: String?,

    @SerializedName("extendNo3")
    val extendNo3: String?,

    @SerializedName("projectNumber")
    val projectNumber: String?,

    @SerializedName("contact")
    val contact: String?,

    @SerializedName("contactPhone")
    val contactPhone: String?,

    @SerializedName("applyNo")
    val applyNo: String?,

    @SerializedName("itemDetail")
    override val itemDetails: List<DeliveryItemDetail>,

    @SerializedName("sumAddition")
    val sumAddition: String?
) : BaseForm(){

    override fun isInput(): Boolean {
        return true
    }
}

data class DeliveryItemDetail(
    @SerializedName("number")
    override val number: String,

    @SerializedName("itemNo")
    val itemNo: String?,

    @SerializedName("batch")
    val batch: String?,

    @SerializedName("no")
    val no: String?,

    @SerializedName("materialNumber")
    override val materialNumber: String,

    @SerializedName("materialName")
    override val materialName: String,

    @SerializedName("materialSpec")
    override val materialSpec: String,

    @SerializedName("materialUnit")
    override val materialUnit: String,

    @SerializedName("deliveryQuantity")
    val deliveryQuantity: Int?,

    @SerializedName("receivedQuantity")
    val receivedQuantity: Int,

    @SerializedName("price")
    val price: String?,

    @SerializedName("itemCost")
    val itemCost: String?
) : BaseItem(){
    override fun getQuantity(): Int {
        return receivedQuantity
    }
}
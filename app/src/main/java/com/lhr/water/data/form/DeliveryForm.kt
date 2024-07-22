package com.lhr.water.data.form

import com.google.gson.annotations.SerializedName

data class DeliveryForm(

    @SerializedName("ano")
    val ano: String,

    @SerializedName("applyNo")
    val applyNo: String,

    @SerializedName("contact")
    val contact: String,

    @SerializedName("contactPhone")
    val contactPhone: String,

    @SerializedName("contractNumber")
    val contractNumber: String,

    @SerializedName("date")
    override val date: String,

    @SerializedName("dealStatus")
    override val dealStatus: String,

    @SerializedName("deliveryDate")
    val deliveryDate: String,

    @SerializedName("deliverylocation")
    val deliverylocation: String,

    @SerializedName("deptName")
    val deptName: String,

    @SerializedName("extendDate1")
    val extendDate1: String,

    @SerializedName("extendDate2")
    val extendDate2: String,

    @SerializedName("extendDate3")
    val extendDate3: String,

    @SerializedName("extendNo1")
    val extendNo1: String,

    @SerializedName("extendNo2")
    val extendNo2: String,

    @SerializedName("extendNo3")
    val extendNo3: String,

    @SerializedName("formNumber")
    override val formNumber: String,

    @SerializedName("formId")
    override val formId: Int,

    @SerializedName("projectNumber")
    val projectNumber: String,

    @SerializedName("receiptDept")
    val receiptDept: String,

    @SerializedName("reportId")
    override val reportId: String,

    @SerializedName("reportTitle")
    override val reportTitle: String,

    @SerializedName("sumAddition")
    val sumAddition: String,

    @SerializedName("underwriter")
    val underwriter: String,

    @SerializedName("updatedAt")
    override val updatedAt: String,

    @SerializedName("itemDetail")
    override val itemDetails: List<DeliveryItemDetail>,

    @SerializedName("isCreateRNumber")
    override val isCreateRNumber: String,

) : BaseForm(){

    override fun isInput(): Boolean {
        return true
    }
}

data class DeliveryItemDetail(
    @SerializedName("itemId")
    override val itemId: Int,

    @SerializedName("number")
    override val number: String,

    @SerializedName("itemNo")
    val itemNo: String,

    @SerializedName("batch")
    val batch: String,

    @SerializedName("no")
    val no: String,

    @SerializedName("materialNumber")
    override val materialNumber: String,

    @SerializedName("materialName")
    override val materialName: String,

    @SerializedName("materialSpec")
    override val materialSpec: String,

    @SerializedName("materialUnit")
    override val materialUnit: String,

    @SerializedName("requestQuantity")
    override val requestQuantity: String,

    @SerializedName("approvedQuantity")
    override var approvedQuantity: String,

    @SerializedName("price")
    val price: String,

    @SerializedName("amount")
    val amount: String,

    @SerializedName("updatedAt")
    override val updatedAt: String,

    @SerializedName("deliveryStatus")
    var deliveryStatus: String
    
) : BaseItem(){
}
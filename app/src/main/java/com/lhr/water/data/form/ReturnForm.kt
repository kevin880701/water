package com.lhr.water.data.form

import com.google.gson.annotations.SerializedName

data class ReturnForm(
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

    @SerializedName("receiptDept")
    val receiptDept: String?,

    @SerializedName("leadNumber")
    val leadNumber: String?,

    @SerializedName("leadDept")
    val leadDept: String?,

    @SerializedName("receivedDate")
    val receivedDate: String?,

    @SerializedName("accountingSubject")
    val accountingSubject: String?,

    @SerializedName("costAllocationUnit")
    val costAllocationUnit: String?,

    @SerializedName("returnDept")
    val returnDept: String?,

    @SerializedName("systemCode")
    val systemCode: String?,

    @SerializedName("usageCode")
    val usageCode: String?,

    @SerializedName("projectNumber")
    val projectNumber: String?,

    @SerializedName("projectName")
    val projectName: String?,

    @SerializedName("originalVoucherNumber")
    val originalVoucherNumber: String?,

    @SerializedName("itemDetail")
    override val itemDetails: List<ReturnItemDetail>
) : BaseForm(){

    override fun isInput(): Boolean {
        return true
    }
}

data class ReturnItemDetail(
    @SerializedName("number")
    override val number: String,

    @SerializedName("materialNumber")
    override val materialNumber: String,

    @SerializedName("materialName")
    override val materialName: String,

    @SerializedName("materialSpec")
    override val materialSpec: String,

    @SerializedName("materialUnit")
    override val materialUnit: String,

    @SerializedName("returnedQuantity")
    val returnedQuantity: Int?,

    @SerializedName("receivedQuantity")
    val receivedQuantity: Int,

    @SerializedName("stockingPrice")
    val stockingPrice: String?,

    @SerializedName("returnAmt")
    val returnAmt: String?
): BaseItem(){
    override fun getQuantity(): Int {
        return receivedQuantity
    }
}
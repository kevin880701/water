package com.lhr.water.data.form

import com.google.gson.annotations.SerializedName

data class ReturnForm(

    @SerializedName("accountingSubject")
    val accountingSubject: String,

    @SerializedName("ano")
    val ano: String,

    @SerializedName("costAllocationUnit")
    val costAllocationUnit: String,

    @SerializedName("date")
    override val date: String,

    @SerializedName("dealStatus")
    override val dealStatus: String,

    @SerializedName("formNumber")
    override val formNumber: String,

    @SerializedName("id")
    override val id: String,

    @SerializedName("leadDept")
    val leadDept: String,

    @SerializedName("leadNumber")
    val leadNumber: String,

    @SerializedName("originalVoucherNumber")
    val originalVoucherNumber: String,

    @SerializedName("projectName")
    val projectName: String,

    @SerializedName("projectNumber")
    val projectNumber: String,

    @SerializedName("receiptDept")
    val receiptDept: String,

    @SerializedName("receivedDate")
    val receivedDate: String,

    @SerializedName("reportId")
    override val reportId: String,

    @SerializedName("reportTitle")
    override val reportTitle: String,

    @SerializedName("returnDept")
    val returnDept: String,

    @SerializedName("systemCode")
    val systemCode: String,

    @SerializedName("updatedAt")
    val updatedAt: String,

    @SerializedName("usageCode")
    val usageCode: String,

    @SerializedName("itemDetail")
    override val itemDetails: List<ReturnItemDetail>
) : BaseForm(){

    override fun isInput(): Boolean {
        return true
    }
}

data class ReturnItemDetail(
    @SerializedName("id")
    override val id: String,

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

    @SerializedName("inRequestQuantity")
    val inRequestQuantity: String,

    @SerializedName("inApprovedQuantity")
    val inApprovedQuantity: String,

    @SerializedName("price")
    val price: String,

    @SerializedName("amount")
    val amount: String,
    
    @SerializedName("updatedAt")
    val updatedAt: String,
    ): BaseItem(){

    override fun getRequestQuantity(): Int {
        return inRequestQuantity.toInt()
    }

    override fun getApprovedQuantity(): Int {
        return inApprovedQuantity.toInt()
    }
}
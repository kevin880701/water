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

    @SerializedName("formId")
    override val formId: Int,

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
    override val updatedAt: String,

    @SerializedName("usageCode")
    val usageCode: String,

    @SerializedName("itemDetail")
    override val itemDetails: List<ReturnItemDetail>,

    @SerializedName("isCreateRNumber")
    override val isCreateRNumber: String
) : BaseForm(){

    override fun isInput(): Boolean {
        return true
    }
}

data class ReturnItemDetail(
    @SerializedName("itemId")
    override val itemId: Int,

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

    ): BaseItem(){
}
package com.lhr.water.data.form

import com.google.gson.annotations.SerializedName

data class ReceiveForm(

    @SerializedName("accountingSubject")
    val accountingSubject: String,

    @SerializedName("ano")
    val ano: String,

    @SerializedName("caseNumber")
    val caseNumber: String,

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

    @SerializedName("issuingUnit")
    val issuingUnit: String,

    @SerializedName("originalVoucherNumber")
    val originalVoucherNumber: String,

    @SerializedName("pickingDate")
    val pickingDate: String,

    @SerializedName("pickingDept")
    val pickingDept: String,

    @SerializedName("projectName")
    val projectName: String,

    @SerializedName("projectNumber")
    val projectNumber: String,

    @SerializedName("reportId")
    override val reportId: String,

    @SerializedName("reportTitle")
    override val reportTitle: String,

    @SerializedName("systemCode")
    val systemCode: String,

    @SerializedName("updatedAt")
    override val updatedAt: String,

    @SerializedName("usageCode")
    val usageCode: String,

    @SerializedName("itemDetail")
    override val itemDetails: List<ReceiveItemDetail>,

    @SerializedName("isCreateRNumber")
    override val isCreateRNumber: String
) : BaseForm() {

    override fun isInput(): Boolean {
        return false
    }
}

data class ReceiveItemDetail(
    @SerializedName("number")
    override val number: String,

    @SerializedName("itemId")
    override val itemId: Int,

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

    @SerializedName("itemCost")
    val itemCost: String,

    @SerializedName("updatedAt")
    override val updatedAt: String,

    ) : BaseItem() {
}
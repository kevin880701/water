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

    @SerializedName("id")
    override val id: String,

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
    val updatedAt: String,

    @SerializedName("usageCode")
    val usageCode: String,

    @SerializedName("itemDetail")
    override val itemDetails: List<ReceiveItemDetail>
) : BaseForm() {

    override fun isInput(): Boolean {
        return false
    }
}

data class ReceiveItemDetail(
    @SerializedName("number")
    override val number: String,

    @SerializedName("id")
    override val id: String,

    @SerializedName("materialNumber")
    override val materialNumber: String,

    @SerializedName("materialName")
    override val materialName: String,

    @SerializedName("materialSpec")
    override val materialSpec: String,

    @SerializedName("materialUnit")
    override val materialUnit: String,

    @SerializedName("outRequestQuantity")
    val outRequestQuantity: String,

    @SerializedName("outApprovedQuantity")
    val outApprovedQuantity: String,

    @SerializedName("price")
    val price: String,

    @SerializedName("itemCost")
    val itemCost: String,

    @SerializedName("updatedAt")
    val updatedAt: String,

    ) : BaseItem() {
    override fun getRequestQuantity(): Int {
        return outRequestQuantity.toInt()
    }

    override fun getApprovedQuantity(): Int {
        return outApprovedQuantity.toInt()
    }
}
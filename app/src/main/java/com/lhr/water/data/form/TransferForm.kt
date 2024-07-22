package com.lhr.water.data.form

import com.google.gson.annotations.SerializedName
import com.lhr.water.data.LoginData

data class TransferForm(

    @SerializedName("contact")
    val contact: String,

    @SerializedName("contactPhone")
    val contactPhone: String,

    @SerializedName("date")
    override val date: String,

    @SerializedName("dealStatus")
    override val dealStatus: String,

    @SerializedName("formNumber")
    override val formNumber: String,

    @SerializedName("formId")
    override val formId: Int,

    @SerializedName("originalVoucherNumber")
    val originalVoucherNumber: String,

    @SerializedName("receivingApplyTransferDate")
    val receivingApplyTransferDate: String,

    @SerializedName("receivingApplyTransferNumber")
    val receivingApplyTransferNumber: String,

    @SerializedName("receivingDept")
    val receivingDept: String,

    @SerializedName("receivingLocation")
    val receivingLocation: String,

    @SerializedName("receivingTransferDate")
    val receivingTransferDate: String,

    @SerializedName("receivingTransferNumber")
    val receivingTransferNumber: String,

    @SerializedName("reportId")
    override val reportId: String,

    @SerializedName("reportTitle")
    override val reportTitle: String,

    @SerializedName("requiredDate")
    val requiredDate: String,

    @SerializedName("transferDescription")
    val transferDescription: String,

    @SerializedName("transferringDept")
    val transferringDept: String,

    @SerializedName("transferringDeptAno")
    val transferringDeptAno: String,

    @SerializedName("transferringTransferDate")
    val transferringTransferDate: String,

    @SerializedName("transferringTransferNumber")
    val transferringTransferNumber: String,

    @SerializedName("transferStatus")
    val transferStatus: String,

    @SerializedName("updatedAt")
    override val updatedAt: String,

    @SerializedName("isCreateRNumber")
    override val isCreateRNumber: String,

    @SerializedName("itemDetail")
    override val itemDetails: ArrayList<TransferItemDetail>
) : BaseForm(){

    override fun isInput(): Boolean {
        return (transferStatus == "撥方已送出")
    }

    data class TransferItemDetail(

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

        @SerializedName("approvalResult")
        val approvalResult: String,

        @SerializedName("applyNumber")
        val applyNumber: String,

        @SerializedName("updatedAt")
        override val updatedAt: String,

        val transferStatus: String,

    ) : BaseItem(){
    }
}

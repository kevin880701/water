package com.lhr.water.data.form

import com.google.gson.annotations.SerializedName
import com.lhr.water.model.LoginData

data class TransferForm(
    @SerializedName("formNumber")
    override val formNumber: String,

    @SerializedName("reportId")
    override val reportId: String,

    @SerializedName("reportTitle")
    override val reportTitle: String,

    @SerializedName("dealStatus")
    override val dealStatus: String,

    @SerializedName("date")
    override val date: String,

    @SerializedName("dealTime")
    override val dealTime: String,

    @SerializedName("transferringDept")
    val transferringDept: String?,

    @SerializedName("receivingDept")
    val receivingDept: String?,

    @SerializedName("receivingLocation")
    val receivingLocation: String?,

    @SerializedName("receivingTransferDate")
    val receivingTransferDate: String?,

    @SerializedName("receivingApplyTransferDate")
    val receivingApplyTransferDate: String?,

    @SerializedName("receivingApplyTransferNumber")
    val receivingApplyTransferNumber: String?,

    @SerializedName("receivingTransferNumber")
    val receivingTransferNumber: String?,

    @SerializedName("requiredDate")
    val requiredDate: String?,

    @SerializedName("transferringTransferNumber")
    val transferringTransferNumber: String?,

    @SerializedName("originalVoucherNumber")
    val originalVoucherNumber: String?,

    @SerializedName("transferringTransferDate")
    val transferringTransferDate: String?,

    @SerializedName("contact")
    val contact: String?,

    @SerializedName("contactPhone")
    val contactPhone: String?,

    @SerializedName("transferDescription")
    val transferDescription: String?,

    @SerializedName("itemDetail")
    override val itemDetails: ArrayList<TransferItemDetail>
) : BaseForm(){

    override fun isInput(): Boolean {
        return (receivingDept == LoginData.region && receivingLocation == LoginData.map)
    }

    data class TransferItemDetail(

        val receivingDept: String,
        val receivingLocation: String,

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

        @SerializedName("actualQuantity")
        val actualQuantity: Int,

        @SerializedName("applyNumber")
        val applyNumber: Int?,

        @SerializedName("allocatedQuantity")
        val allocatedQuantity: Int?,

        @SerializedName("receivedQuantity")
        val receivedQuantity: Int
    ) : BaseItem(){
        override fun getQuantity(): Int {
            if (receivingDept == LoginData.region && receivingLocation == LoginData.map){
                return receivedQuantity
            }else{
                return actualQuantity
            }
        }
    }
}

package com.lhr.water.data.form

import com.google.gson.annotations.SerializedName

data class ReceiveForm(
    @SerializedName("formNumber") override val formNumber: String,
    @SerializedName("dealStatus") override val dealStatus: String,
    @SerializedName("reportId") override val reportId: String,
    @SerializedName("reportTitle") override val reportTitle: String,
    @SerializedName("date") override val date: String,
    @SerializedName("dealTime") override val dealTime: String,
    @SerializedName("issuingUnit") val issuingUnit: String?,
    @SerializedName("pickingDate") val pickingDate: String?,
    @SerializedName("pickingDept") val pickingDept: String?,
    @SerializedName("originalVoucherNumber") val originalVoucherNumber: String?,
    @SerializedName("costAllocationUnit") val costAllocationUnit: String?,
    @SerializedName("accountingSubject") val accountingSubject: String?,
    @SerializedName("systemCode") val systemCode: String?,
    @SerializedName("usageCode") val usageCode: String?,
    @SerializedName("projectNumber") val projectNumber: String?,
    @SerializedName("projectName") val projectName: String?,
    @SerializedName("caseNumber") val caseNumber: String?,
    @SerializedName("itemDetail") val itemDetail: List<ReceiveItemDetail>?
) : BaseForm()

data class ReceiveItemDetail(
    @SerializedName("number") val number: String?,
    @SerializedName("materialNumber") val materialNumber: String?,
    @SerializedName("materialName") val materialName: String?,
    @SerializedName("materialSpec") val materialSpec: String?,
    @SerializedName("materialUnit") val materialUnit: String?,
    @SerializedName("requestedQuantity") val requestedQuantity: Int?,
    @SerializedName("actualQuantity") val actualQuantity: Int?,
    @SerializedName("price") val price: String?,
    @SerializedName("itemCost") val itemCost: String?
)
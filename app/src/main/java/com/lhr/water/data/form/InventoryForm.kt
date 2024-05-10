package com.lhr.water.data.form

import com.google.gson.annotations.SerializedName

data class InventoryForm(
    @SerializedName("id") val id: Int,
    @SerializedName("formNumber") override val formNumber: String,
    @SerializedName("dealStatus") override val dealStatus: String,
    @SerializedName("reportId") override val reportId: String,
    @SerializedName("reportTitle") override val reportTitle: String,
    @SerializedName("date") override val date: String,
    @SerializedName("dealTime") override val dealTime: String,
    @SerializedName("inventoryUnit") val inventoryUnit: String,
    @SerializedName("deptName") val deptName: String,
    @SerializedName("seq") val seq: String,
    @SerializedName("materialNumber") val materialNumber: String,
    @SerializedName("materialName") val materialName: String,
    @SerializedName("materialSpec") val materialSpec: String,
    @SerializedName("materialUnit") val materialUnit: String,
    @SerializedName("actualQuantity") val actualQuantity: Int,
    @SerializedName("checkDate") val checkDate: String,
    @SerializedName("lastUseDate") val lastUseDate: String,
    @SerializedName("approvedDate") val approvedDate: String
) : BaseForm()
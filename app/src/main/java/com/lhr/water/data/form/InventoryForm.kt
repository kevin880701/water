package com.lhr.water.data.form

import com.google.gson.annotations.SerializedName

data class InventoryForm(
    @SerializedName("formNumber")
    val formNumber: String,

    @SerializedName("dealStatus")
    val dealStatus: String,

    @SerializedName("reportId")
    val reportId: String,

    @SerializedName("reportTitle")
    val reportTitle: String,

    @SerializedName("date")
    val date: String,

    @SerializedName("dealTime")
    val dealTime: String,

    @SerializedName("inventoryUnit")
    val inventoryUnit: String,

    @SerializedName("deptName")
    val deptName: String,

    @SerializedName("seq")
    val seq: String,

    @SerializedName("materialNumber")
    val materialNumber: String,

    @SerializedName("materialName")
    val materialName: String,

    @SerializedName("materialSpec")
    val materialSpec: String,

    @SerializedName("materialUnit")
    val materialUnit: String,

    @SerializedName("actualQuantity")
    val actualQuantity: Int,

    @SerializedName("checkDate")
    val checkDate: String,

    @SerializedName("lastUseDate")
    val lastUseDate: String,

    @SerializedName("approvedDate")
    val approvedDate: String
)
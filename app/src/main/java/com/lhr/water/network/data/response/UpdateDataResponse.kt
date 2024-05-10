package com.lhr.water.network.data.response

import com.google.gson.annotations.SerializedName
import com.lhr.water.data.form.DeliveryForm
import com.lhr.water.data.form.InventoryForm
import com.lhr.water.data.form.ReceiveForm
import com.lhr.water.data.form.ReturnForm
import com.lhr.water.data.form.TransferForm
import com.lhr.water.room.CheckoutEntity
import com.lhr.water.room.StorageEntity
import com.lhr.water.room.StorageRecordEntity

data class UpdateDataResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val updateData: UpdateData
)

data class UpdateData(
    @SerializedName("list") val dataList: DataList
)

data class DataList(
    @SerializedName("delivery") val deliveryFormList: List<DeliveryForm>,
//    @SerializedName("acceptance") val acceptanceFormList: List<DeliveryForm>,
    @SerializedName("transfer") val transferFormList: List<TransferForm>,
    @SerializedName("receive") val receiveFormList: List<ReceiveForm>,
    @SerializedName("return") val returnFormList: List<ReturnForm>,
    @SerializedName("inventory") val inventoryFormList: List<InventoryForm>,

    @SerializedName("whseStrg") val storageList: List<StorageEntity>,
    @SerializedName("invtStrg") val storageRecordList: List<StorageRecordEntity>,
    @SerializedName("checkoutForm") val checkoutFormList: List<CheckoutEntity>
)
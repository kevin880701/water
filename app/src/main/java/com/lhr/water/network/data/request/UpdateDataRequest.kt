package com.lhr.water.network.data.request

import com.google.gson.annotations.SerializedName
import com.lhr.water.data.form.DeliveryForm
import com.lhr.water.data.form.ReceiveForm
import com.lhr.water.data.form.ReturnForm
import com.lhr.water.data.form.TransferForm
import com.lhr.water.network.data.response.UserInfo
import com.lhr.water.room.CheckoutEntity
import com.lhr.water.room.InventoryEntity
import com.lhr.water.room.StorageEntity
import com.lhr.water.room.StorageRecordEntity

data class UpdateDataRequest(
    @SerializedName("list") val dataList: DataList,
    @SerializedName("userInfo") val userInfo: UserInfo
){

}
data class DataList(
    @SerializedName("delivery") val deliveryList: List<DeliveryForm>,
//    @SerializedName("acceptance") val acceptance: List<Any>,
    @SerializedName("transfer") val transferList: List<TransferForm>,
    @SerializedName("receive") val receiveList: List<ReceiveForm>,
    @SerializedName("return") val returnListList: List<ReturnForm>,
    @SerializedName("inventory") val inventoryEntities: List<InventoryEntity>,
    @SerializedName("invtStrg") val storageRecordEntities: List<StorageRecordEntity>
)
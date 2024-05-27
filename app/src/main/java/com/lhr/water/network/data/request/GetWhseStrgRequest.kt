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

data class GetWhseStrgRequest(
    @SerializedName("list") val dataList: DataList,
    @SerializedName("userInfo") val userInfo: UserInfo
){

}
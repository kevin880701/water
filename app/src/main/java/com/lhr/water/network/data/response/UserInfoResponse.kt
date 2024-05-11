package com.lhr.water.network.data.response

import com.google.gson.annotations.SerializedName
import com.lhr.water.data.form.DeliveryForm
import com.lhr.water.data.form.ReceiveForm
import com.lhr.water.data.form.ReturnForm
import com.lhr.water.data.form.TransferForm
import com.lhr.water.room.CheckoutEntity
import com.lhr.water.room.InventoryEntity
import com.lhr.water.room.StorageEntity
import com.lhr.water.room.StorageRecordEntity

data class UserInfoResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val userInfoData: UserInfoData
)

data class UserInfoData(
    @SerializedName("userInfo") val userInfo: UserInfo
)

data class UserInfo(
    @SerializedName("deptAno") val deptAno: String,
    @SerializedName("userId") val userId: String
)
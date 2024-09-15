package com.lhr.water.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * 儲櫃結算後的物料數量。
 */
@Entity(
    tableName = SqlModel.CHECKOUT_TABLE_NAME,
    indices = [Index(value = [SqlModel.id])]
)
class CheckoutEntity(
    storageId: Int,
    materialName: String,
    materialNumber: String,
    quantity: Int,
    inputTime: String, //（InvtLdtm: 材料入庫時間，格式YYYY-MM-DD-HH-MM-SS）
    checkoutTime: String, //（結算時間，格式YYYY-MM-DD-HH-MM-SS）
) : Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id") var id: Int = 0

    @ColumnInfo(name = SqlModel.storageId, typeAffinity = ColumnInfo.INTEGER)
    @SerializedName("storageInventoryId") var storageId = storageId

    @ColumnInfo(name = SqlModel.materialName, typeAffinity = ColumnInfo.TEXT)
    @SerializedName("storageMaterialName") var materialName = materialName

    @ColumnInfo(name = SqlModel.materialNumber, typeAffinity = ColumnInfo.TEXT)
    @SerializedName("storageMaterialNo") var materialNumber = materialNumber

    @ColumnInfo(name = SqlModel.quantity, typeAffinity = ColumnInfo.INTEGER)
    @SerializedName("storageMaterialQuantity") var quantity = quantity

    @ColumnInfo(name = SqlModel.inputTime, typeAffinity = ColumnInfo.TEXT)
    @SerializedName("storageArrivalTime") var inputTime = inputTime

    @ColumnInfo(name = SqlModel.checkoutTime, typeAffinity = ColumnInfo.TEXT)
    @SerializedName("checkoutTime") var checkoutTime = checkoutTime
}
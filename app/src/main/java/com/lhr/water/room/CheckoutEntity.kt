package com.lhr.water.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = SqlModel.CHECKOUT_TABLE_NAME,
    indices = [Index(
        value = [SqlModel.id],
        unique = true
    )]
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
    var id: Int = 0

    @ColumnInfo(name = SqlModel.storageId, typeAffinity = ColumnInfo.INTEGER)
    var storageId = storageId

    @ColumnInfo(name = SqlModel.materialName, typeAffinity = ColumnInfo.TEXT)
    var materialName = materialName

    @ColumnInfo(name = SqlModel.materialNumber, typeAffinity = ColumnInfo.TEXT)
    var materialNumber = materialNumber

    @ColumnInfo(name = SqlModel.quantity, typeAffinity = ColumnInfo.INTEGER)
    var quantity = quantity

    @ColumnInfo(name = SqlModel.inputTime, typeAffinity = ColumnInfo.TEXT)
    var inputTime = inputTime

    @ColumnInfo(name = SqlModel.checkoutTime, typeAffinity = ColumnInfo.TEXT)
    var checkoutTime = checkoutTime

}
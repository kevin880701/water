package com.lhr.water.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(
    tableName = SqlModel.STORAGE_TABLE_NAME,
    indices = [Index(value = [SqlModel.id])]
)
class StorageEntity(
    storageId: Int,
    deptNumber: String,
    mapSeq: Int,
    storageName: String,
    storageX: Int,
    storageY: Int,
) : Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = SqlModel.storageId, typeAffinity = ColumnInfo.INTEGER)
    @SerializedName("storageId") var storageId = storageId

    @ColumnInfo(name = SqlModel.deptNumber, typeAffinity = ColumnInfo.TEXT)
    @SerializedName("storageWarehouseDeptAno") var deptNumber = deptNumber

    @ColumnInfo(name = SqlModel.mapSeq, typeAffinity = ColumnInfo.INTEGER)
    @SerializedName("storageWarehouseSeq") var mapSeq = mapSeq

    @ColumnInfo(name = SqlModel.storageName, typeAffinity = ColumnInfo.TEXT)
    @SerializedName("storageName") var storageName = storageName

    @ColumnInfo(name = SqlModel.storageX, typeAffinity = ColumnInfo.INTEGER)
    @SerializedName("storageX") var storageX = storageX

    @ColumnInfo(name = SqlModel.storageY, typeAffinity = ColumnInfo.INTEGER)
    @SerializedName("storageY") var storageY = storageY
}
package com.lhr.water.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = SqlModel.STORAGE_TABLE_NAME,
    indices = [Index(
        value = [SqlModel.storageId],
        unique = true
    )]
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
    var storageId = storageId

    @ColumnInfo(name = SqlModel.deptNumber, typeAffinity = ColumnInfo.TEXT)
    var deptNumber = deptNumber

    @ColumnInfo(name = SqlModel.mapSeq, typeAffinity = ColumnInfo.INTEGER)
    var mapSeq = mapSeq

    @ColumnInfo(name = SqlModel.storageName, typeAffinity = ColumnInfo.TEXT)
    var storageName = storageName

    @ColumnInfo(name = SqlModel.storageX, typeAffinity = ColumnInfo.INTEGER)
    var storageX = storageX

    @ColumnInfo(name = SqlModel.storageY, typeAffinity = ColumnInfo.INTEGER)
    var storageY = storageY
}
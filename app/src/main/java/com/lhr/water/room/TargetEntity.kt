package com.lhr.water.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

//@Entity(tableName = SqlModel.TARGET_TABLE_NAME, indices = [Index(value = [SqlModel.storageNum], unique = true)])
@Entity(tableName = SqlModel.TARGET_TABLE_NAME, indices = [Index(value = [SqlModel.id])])
class TargetEntity() : Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = SqlModel.regionName, typeAffinity = ColumnInfo.TEXT)
    var regionName = ""

    @ColumnInfo(name = SqlModel.mapName, typeAffinity = ColumnInfo.TEXT)
    var mapName = ""

    @ColumnInfo(name = SqlModel.storageNum, typeAffinity = ColumnInfo.TEXT)
    var storageNum = ""

    @ColumnInfo(name = SqlModel.storageName, typeAffinity = ColumnInfo.TEXT)
    var storageName = ""

    @ColumnInfo(name = SqlModel.storageX, typeAffinity = ColumnInfo.TEXT)
    var storageX = ""

    @ColumnInfo(name = SqlModel.storageY, typeAffinity = ColumnInfo.TEXT)
    var storageY = ""
}
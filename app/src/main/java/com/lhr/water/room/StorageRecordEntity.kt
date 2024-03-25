package com.lhr.water.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = SqlModel.STORAGE_RECORD_TABLE_NAME, indices = [Index(value = [SqlModel.id])])
class StorageRecordEntity() : Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = SqlModel.regionName, typeAffinity = ColumnInfo.TEXT)
    var regionName = ""

    @ColumnInfo(name = SqlModel.mapName, typeAffinity = ColumnInfo.TEXT)
    var mapName = ""

    @ColumnInfo(name = SqlModel.storageName, typeAffinity = ColumnInfo.TEXT)
    var storageName = ""

    @ColumnInfo(name = SqlModel.formNumber, typeAffinity = ColumnInfo.TEXT)
    var formNumber = ""

    @ColumnInfo(name = SqlModel.reportTitle, typeAffinity = ColumnInfo.TEXT)
    var reportTitle = ""

    @ColumnInfo(name = SqlModel.date, typeAffinity = ColumnInfo.TEXT)
    var date = ""

    @ColumnInfo(name = SqlModel.type, typeAffinity = ColumnInfo.INTEGER)
    var type = 0

    @ColumnInfo(name = SqlModel.quantity, typeAffinity = ColumnInfo.INTEGER)
    var quantity = 0

    @ColumnInfo(name = SqlModel.itemDetail, typeAffinity = ColumnInfo.TEXT)
    var itemDetail = ""
}
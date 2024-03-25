package com.lhr.water.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = SqlModel.STORAGE_CONTENT_TABLE_NAME,
    indices = [Index(
        value = [SqlModel.regionName, SqlModel.mapName,
            SqlModel.storageName, SqlModel.materialName,
            SqlModel.materialNumber, SqlModel.materialSpec,
            SqlModel.materialUnit],
        unique = true
    )]
)
class StorageContentEntity(
    regionName: String,
    mapName: String,
    storageName: String,
    formNumber: String,
    date: String,
    type: Int,
    quantity: Int,
    materialName: String,
    materialNumber: String,
    materialSpec: String,
    materialUnit: String
) : Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = SqlModel.regionName, typeAffinity = ColumnInfo.TEXT)
    var regionName = regionName

    @ColumnInfo(name = SqlModel.mapName, typeAffinity = ColumnInfo.TEXT)
    var mapName = mapName

    @ColumnInfo(name = SqlModel.storageName, typeAffinity = ColumnInfo.TEXT)
    var storageName = storageName

    @ColumnInfo(name = SqlModel.formNumber, typeAffinity = ColumnInfo.TEXT)
    var formNumber = formNumber

    @ColumnInfo(name = SqlModel.date, typeAffinity = ColumnInfo.TEXT)
    var date = date

    @ColumnInfo(name = SqlModel.type, typeAffinity = ColumnInfo.INTEGER)
    var type = type

    @ColumnInfo(name = SqlModel.quantity, typeAffinity = ColumnInfo.INTEGER)
    var quantity = quantity

    @ColumnInfo(name = SqlModel.materialName, typeAffinity = ColumnInfo.TEXT)
    var materialName = materialName

    @ColumnInfo(name = SqlModel.materialNumber, typeAffinity = ColumnInfo.TEXT)
    var materialNumber = materialNumber

    @ColumnInfo(name = SqlModel.materialSpec, typeAffinity = ColumnInfo.TEXT)
    var materialSpec = materialSpec

    @ColumnInfo(name = SqlModel.materialUnit, typeAffinity = ColumnInfo.TEXT)
    var materialUnit = materialUnit
}
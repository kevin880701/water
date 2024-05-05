package com.lhr.water.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = SqlModel.REGION_TABLE_NAME,
    indices = [Index(
        value = [SqlModel.regionName],
        unique = true
    )]
)
class RegionEntity(
    regionName: String,
    regionNumber: String,
    deptName: String,
    deptNumber: String,
    mapSeq: Int,
) : Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = SqlModel.regionName, typeAffinity = ColumnInfo.TEXT)
    var regionName = regionName

    @ColumnInfo(name = SqlModel.regionNumber, typeAffinity = ColumnInfo.TEXT)
    var regionNumber = regionNumber

    @ColumnInfo(name = SqlModel.deptName, typeAffinity = ColumnInfo.TEXT)
    var deptName = deptName

    @ColumnInfo(name = SqlModel.deptNumber, typeAffinity = ColumnInfo.TEXT)
    var deptNumber = deptNumber

    @ColumnInfo(name = SqlModel.mapSeq, typeAffinity = ColumnInfo.INTEGER)
    var mapSeq = mapSeq
}
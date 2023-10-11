package com.lhr.water.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = SqlModel.TARGET_TABLE_NAME, indices = [Index(value = [SqlModel.targetNum], unique = true)])
class TargetEntity() : Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = SqlModel.targetRegion, typeAffinity = ColumnInfo.TEXT)
    var targetRegion = ""

    @ColumnInfo(name = SqlModel.targetRegionNum, typeAffinity = ColumnInfo.INTEGER)
    var targetRegionNum = 0

    @ColumnInfo(name = SqlModel.targetName, typeAffinity = ColumnInfo.TEXT)
    var targetName = ""

    @ColumnInfo(name = SqlModel.targetNum, typeAffinity = ColumnInfo.INTEGER)
    var targetNum = 0

    @ColumnInfo(name = SqlModel.targetCoordinateX, typeAffinity = ColumnInfo.REAL)
    var targetCoordinateX = 0F

    @ColumnInfo(name = SqlModel.targetCoordinateY, typeAffinity = ColumnInfo.REAL)
    var targetCoordinateY = 0F

    @ColumnInfo(name = SqlModel.targetType, typeAffinity = ColumnInfo.TEXT)
    var targetType = ""

    @ColumnInfo(name = SqlModel.targetTypeNum, typeAffinity = ColumnInfo.INTEGER)
    var targetTypeNum = 0

}
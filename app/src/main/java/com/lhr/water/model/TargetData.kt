package com.lhr.water.model

import androidx.room.Entity
import androidx.room.Index
import com.lhr.water.room.SqlModel
import java.io.Serializable

@Entity(tableName = SqlModel.TARGET_TABLE_NAME, indices = [Index(value = [SqlModel.targetNum], unique = true)])
class TargetData(targetRegion: String, targetRegionNum: Int, targetName: String, targetNum: Int,
                 targetCoordinateX: Float, targetCoordinateY: Float, targetType: String, targetTypeNum: Int) : Serializable {

    var targetRegion = targetRegion

    var targetRegionNum = targetRegionNum

    var targetName = targetName

    var targetNum = targetNum

    var targetCoordinateX = targetCoordinateX

    var targetCoordinateY = targetCoordinateY

    var targetType = targetType

    var targetTypeNum = targetTypeNum
}
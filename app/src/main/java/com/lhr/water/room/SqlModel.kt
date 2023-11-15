package com.lhr.water.room

class SqlModel {
    companion object {
        const val DB_NAME : String = "WaterInfo.db"
        const val FORM_TABLE_NAME : String = "Form"
        const val TARGET_TABLE_NAME : String = "TargetInfo"
        const val SQLITE_SEQUENCE : String = "sqlite_sequence"
        const val id : String = "id"
        //Target
        const val targetRegion : String = "targetRegion"
        const val targetRegionNum : String = "targetRegionNum"
        const val targetName : String = "targetName"
        const val targetNum : String = "targetNum"
        const val targetCoordinateX : String = "targetCoordinateX"
        const val targetCoordinateY : String = "targetCoordinateY"
        const val targetType : String = "targetType"
        const val targetTypeNum : String = "targetTypeNum"
        //Form
        const val reportId : String = "reportId"
        const val formContent : String = "formContent"
    }
}
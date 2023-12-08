package com.lhr.water.room

class SqlModel {
    companion object {
        const val DB_NAME : String = "WaterInfo.db"
        const val FORM_TABLE_NAME : String = "Form"
        const val TARGET_TABLE_NAME : String = "TargetInfo"
        const val STORAGE_CONTENT_NAME : String = "StorageContent"
        const val SQLITE_SEQUENCE : String = "sqlite_sequence"
        const val id : String = "id"
        //Form
        const val formNumber : String = "formNumber"
        const val formContent : String = "formContent"
        //地圖資訊(無儲存物)
        const val regionName : String = "regionName"
        const val mapName : String = "mapName"
        const val storageNum : String = "storageNum"
        const val storageName : String = "storageName"
        const val storageX : String = "storageX"
        const val storageY : String = "storageY"

        const val reportTitle : String = "reportTitle"
        const val itemInformation : String = "itemInformation"
    }
}
package com.lhr.water.room

class SqlModel {
    companion object {
        const val DB_NAME: String = "WaterInfo.db"
        const val FORM_TABLE_NAME: String = "Form"
        const val INVENTORY_TABLE_NAME: String = "inventory"
        const val REGION_TABLE_NAME: String = "Region"
        const val MAP_TABLE_NAME: String = "Map"
        const val STORAGE_TABLE_NAME: String = "Storage"
        const val STORAGE_RECORD_TABLE_NAME: String = "StorageRecord"
        const val CHECKOUT_TABLE_NAME: String = "Checkout"
        const val SQLITE_SEQUENCE: String = "sqlite_sequence"
        const val id: String = "id"

        //Form
        const val formNumber: String = "formNumber"
        const val formContent: String = "formContent"

        //地圖資訊(無儲存物)
        const val regionName: String = "regionName"
        const val regionNumber: String = "regionNumber"
        const val deptName: String = "deptName"
        const val deptNumber: String = "deptNumber"

        const val storageId: String = "storageId"
        const val mapSeq: String = "mapSeq"
        const val mapName: String = "mapName"
        const val storageNum: String = "storageNum"
        const val storageName: String = "storageName"
        const val storageX: String = "storageX"
        const val storageY: String = "storageY"
        const val formType: String = "formType"
        const val reportTitle: String = "reportTitle"
        const val date: String = "inputDate"
        const val type: String = "type"
        const val itemDetail: String = "itemDetail"
        const val InvtStat: String = "InvtStat"
        const val userId: String = "userId"
        const val InvtDevi: String = "InvtDevi"
        const val materialNumber: String = "materialNumber"
        const val materialName: String = "materialName"
        const val materialSpec: String = "materialSpec"
        const val materialUnit: String = "materialUnit"
        const val length: String = "length"
        const val quantity: String = "quantity"
        const val inputTime: String = "inputTime"
        const val checkoutTime: String = "checkoutTime"

    }
}
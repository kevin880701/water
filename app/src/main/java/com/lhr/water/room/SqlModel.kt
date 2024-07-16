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
        const val dealStatus: String = "dealStatus"
        const val reportId: String = "reportId"
        const val date: String = "date"
        const val formContent: String = "formContent"

        //地圖資訊(無儲存物)
        const val regionName: String = "regionName"
        const val regionNumber: String = "regionNumber"
        const val deptName: String = "deptName"
        const val deptNumber: String = "deptNumber"

        // InventoryEntity
        const val inventoryUnit: String = "inventoryUnit"
        const val seq: String = "seq"
        const val formId: String = "formId"
        const val actualQuantity: String = "actualQuantity"
        const val checkDate: String = "checkDate"
        const val lastUseDate: String = "lastUseDate"
        const val approvedDate: String = "approvedDate"
        const val updatedAt: String = "updatedAt"

        const val storageId: String = "storageId"
        const val mapSeq: String = "mapSeq"
        const val storageName: String = "storageName"
        const val storageX: String = "storageX"
        const val storageY: String = "storageY"
        const val formType: String = "formType"
        const val reportTitle: String = "reportTitle"
        const val recordDate: String = "recordDate"
        const val storageArrivalId: String = "storageArrivalId"
        const val itemId: String = "itemId"

        const val itemDetail: String = "itemDetail"
        const val materialStatus: String = "materialStatus"
        const val userId: String = "userId"
        const val materialNumber: String = "materialNumber"
        const val materialName: String = "materialName"
        const val materialSpec: String = "materialSpec"
        const val materialUnit: String = "materialUnit"
        const val quantity: String = "quantity"
        const val inputTime: String = "inputTime"
        const val checkoutTime: String = "checkoutTime"

    }
}
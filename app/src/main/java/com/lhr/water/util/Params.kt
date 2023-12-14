package com.lhr.water.util

object MapPageStatus {
    const val RegionPage = "Region"
    const val MapPage = "Map"
}

object TransferStatus {
    const val transferInput = "transferInput"
    const val transferOutput = "transferOutput"
    const val notTransfer = "notTransfer"
}

object FormName {
    const val deliveryFormName = "交貨通知單"
    const val checkFormName = "財務驗收單"
    const val pickingFormName = "材料領料單"
    const val transferFormName = "材料調撥單"
    const val returningFormName = "材料退料單"
    const val inventoryFormName = "盤點"
}


object DealStatus {
    const val waitDeal = "待處理"
    const val nowDeal = "處理中"
    const val completeDeal = "處理完成"
}
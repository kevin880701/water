package com.lhr.water.data

// 處理到一半的貨物
data class TempDealGoodsData(
    val reportTitle: String,
    val formNumber: String,
    var regionName: String,
    var mapName: String,
    var storageName: String,
    var date: String,
    val itemDetail: ItemDetail,
    var quantity: Int,
)
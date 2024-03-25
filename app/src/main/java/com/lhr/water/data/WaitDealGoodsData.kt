package com.lhr.water.data

// 將表單改為待處理後的待處理貨物列表
data class WaitDealGoodsData(
    val reportTitle: String,
    val formNumber: String,
    val itemDetail: ItemDetail,
)
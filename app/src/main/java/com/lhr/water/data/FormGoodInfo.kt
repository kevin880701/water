package com.lhr.water.data

data class FormGoodInfo(
    val goodsNumber: String?, //材料編號
    val goodsName: String?,  //材料名稱
    val specification: String?,  //規格
    val unit: String?,// 單位
    val allocationQuantity: String?, // 核撥數量
    val allocationSequenceNumber: String?, // 請撥序號
    val actualAllocationQuantity: String?, // 實撥數量
    val actualReceiptQuantity: String?, // 實收數量
    val comments: String? // 備註
)
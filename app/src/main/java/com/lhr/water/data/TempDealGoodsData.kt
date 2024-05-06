package com.lhr.water.data

// 處理到一半的貨物
data class TempDealGoodsData(
    val storageId: Int,  //InvtSlotId: 儲位編號
    val reportTitle: String, //InvtFromType: 入庫單據種類; 1-交貨，2-驗收，3-調撥，4-領料，5-退料，6-盤點
    val formNumber: String,  //InvtFromNo: 入庫單據編號
    var materialNumber: Int, //InvtMNo: 材料編號
    val InvtStat: Int, //InvtStat: 材料狀態; 1: 已交貨，2: 已驗收，3: 已移出
    val userId: String, //InvtUserId: 操作的使用者ID
    var InvtDevi: Int = 2,
    var quantity: Int, //InvtNum: 數量
    var date: String, //CreatedAt: 紀錄創建時間（出庫入庫時間，格式YYYYMMDD-HHMMSS）
)







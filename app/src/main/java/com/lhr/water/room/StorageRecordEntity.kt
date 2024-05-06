package com.lhr.water.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = SqlModel.STORAGE_RECORD_TABLE_NAME, indices = [Index(value = [SqlModel.id])])
class StorageRecordEntity(
    storageId: Int,  //InvtSlotId: 儲位編號
    reportTitle: Int, //InvtFromType: 入庫單據種類; 1-交貨，2-驗收，3-調撥，4-領料，5-退料，6-盤點
    formNumber: String,  //InvtFromNo: 入庫單據編號
    materialName: String, //MNoMName: 材料名稱
    materialNumber: String, //InvtMNo: 材料編號
    InvtStat: Int, //InvtStat: 材料狀態; 1: 已交貨，2: 已驗收，3: 已移出
    userId: String, //InvtUserId: 操作的使用者ID
    InvtDevi: Int = 2,
    quantity: Int, //InvtNum: 數量
    date: String, //CreatedAt: 紀錄創建時間（出庫入庫時間，格式YYYYMMDD-HHMMSS）
) : Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = SqlModel.storageId, typeAffinity = ColumnInfo.INTEGER)
    var storageId = storageId

    @ColumnInfo(name = SqlModel.reportTitle, typeAffinity = ColumnInfo.INTEGER)
    var reportTitle = reportTitle

    @ColumnInfo(name = SqlModel.formNumber, typeAffinity = ColumnInfo.TEXT)
    var formNumber = formNumber

    @ColumnInfo(name = SqlModel.materialName, typeAffinity = ColumnInfo.TEXT)
    var materialName = materialName

    @ColumnInfo(name = SqlModel.materialNumber, typeAffinity = ColumnInfo.TEXT)
    var materialNumber = materialNumber

    @ColumnInfo(name = SqlModel.InvtStat, typeAffinity = ColumnInfo.INTEGER)
    var InvtStat = InvtStat

    @ColumnInfo(name = SqlModel.userId, typeAffinity = ColumnInfo.TEXT)
    var userId = userId

    @ColumnInfo(name = SqlModel.InvtDevi, typeAffinity = ColumnInfo.INTEGER)
    var InvtDevi = InvtDevi

    @ColumnInfo(name = SqlModel.quantity, typeAffinity = ColumnInfo.INTEGER)
    var quantity = quantity

    @ColumnInfo(name = SqlModel.date, typeAffinity = ColumnInfo.TEXT)
    var date = date
}
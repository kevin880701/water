package com.lhr.water.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = SqlModel.STORAGE_RECORD_TABLE_NAME, indices = [Index(value = [SqlModel.id])])
class StorageRecordEntity(
    storageId: Int,  //InvtSlotId: 儲位編號
    formType: String, //InvtFromType: 入庫單據種類; 1-交貨，2-驗收，3-調撥，4-領料，5-退料，6-盤點
    formNumber: String,  //InvtFromNo: 入庫單據編號
    materialName: String, //MNoMName: 材料名稱
    materialNumber: String, //InvtMNo: 材料編號
    materialStatus: String, //InvtStat: 材料狀態; 1: 已交貨，2: 已驗收，3: 已移出
    userId: String, //InvtUserId: 操作的使用者ID
    quantity: String, //InvtNum: 數量
    recordDate: String, //CreatedAt: 紀錄創建時間（出庫入庫時間，格式YYYY-MM-DD-HH-MM-SS）
    storageArrivalId: String, //CreatedAt: 紀錄創建時間（出庫入庫時間，格式YYYY-MM-DD-HH-MM-SS）
    isUpdate: Boolean = true,
    itemId: Int
) : Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = SqlModel.storageId, typeAffinity = ColumnInfo.INTEGER)
    @SerializedName("storageId") var storageId = storageId

    @ColumnInfo(name = SqlModel.formType, typeAffinity = ColumnInfo.TEXT)
    @SerializedName("storageFromType") var formType = formType

    @ColumnInfo(name = SqlModel.formNumber, typeAffinity = ColumnInfo.TEXT)
    @SerializedName("storageFromNo") var formNumber = formNumber

    @ColumnInfo(name = SqlModel.materialName, typeAffinity = ColumnInfo.TEXT)
    @SerializedName("storageMaterialName") var materialName = materialName

    @ColumnInfo(name = SqlModel.materialNumber, typeAffinity = ColumnInfo.TEXT)
    @SerializedName("storageMaterialNo") var materialNumber = materialNumber

    @ColumnInfo(name = SqlModel.quantity, typeAffinity = ColumnInfo.TEXT)
    @SerializedName("storageMaterialQuantity") var quantity = quantity

    @ColumnInfo(name = SqlModel.materialStatus, typeAffinity = ColumnInfo.TEXT)
    @SerializedName("InvtStat") var materialStatus = materialStatus

    @ColumnInfo(name = SqlModel.userId, typeAffinity = ColumnInfo.TEXT)
    @SerializedName("storageUserId") var userId = userId

    @ColumnInfo(name = SqlModel.recordDate, typeAffinity = ColumnInfo.TEXT)
    @SerializedName("createdAt") var recordDate = recordDate

    @ColumnInfo(name = SqlModel.storageArrivalId, typeAffinity = ColumnInfo.TEXT)
    @SerializedName("storageArrivalId") var storageArrivalId = storageArrivalId

    @ColumnInfo(name = SqlModel.itemId, typeAffinity = ColumnInfo.INTEGER)
    @SerializedName("itemId") var itemId = itemId

    @ColumnInfo(name = "isUpdate", typeAffinity = ColumnInfo.INTEGER)
    var isUpdate = isUpdate
}
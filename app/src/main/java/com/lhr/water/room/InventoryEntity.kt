package com.lhr.water.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = SqlModel.INVENTORY_TABLE_NAME, indices = [Index(value = [SqlModel.formNumber], unique = true)])
class InventoryEntity(

    formId: Int,
    formNumber: String,
    dealStatus: String,
    reportId: String,
    reportTitle: String,
    date: String,
    deptName: String,
    seq: String,
    updatedAt: String,
    materialNumber: String,
    materialName: String,
    materialSpec: String,
    inventoryUnit: String,
    materialUnit: String,
    actualQuantity: String,
    checkDate: String,
    lastUseDate: String,
    approvedDate: String,
    isUpdate: Boolean = true
) : Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    var id = 0

    @ColumnInfo(name = SqlModel.formId, typeAffinity = ColumnInfo.INTEGER)
    @SerializedName("formId")
    var formId = formId

    @ColumnInfo(name = SqlModel.actualQuantity, typeAffinity = ColumnInfo.TEXT)
    @SerializedName("actualQuantity")
    var actualQuantity = actualQuantity

    @ColumnInfo(name = SqlModel.approvedDate, typeAffinity = ColumnInfo.TEXT)
    @SerializedName("approvedDate")
    val approvedDate = approvedDate

    @ColumnInfo(name = SqlModel.checkDate, typeAffinity = ColumnInfo.TEXT)
    @SerializedName("checkDate")
    val checkDate = checkDate

    @ColumnInfo(name = SqlModel.date, typeAffinity = ColumnInfo.TEXT)
    @SerializedName("date")
    var date = date

    @ColumnInfo(name = SqlModel.dealStatus, typeAffinity = ColumnInfo.TEXT)
    @SerializedName("dealStatus")
    var dealStatus = dealStatus

    @ColumnInfo(name = SqlModel.deptName, typeAffinity = ColumnInfo.TEXT)
    @SerializedName("deptName")
    val deptName = deptName

    @ColumnInfo(name = SqlModel.formNumber, typeAffinity = ColumnInfo.TEXT)
    @SerializedName("formNumber")
    var formNumber = formNumber

    @ColumnInfo(name = SqlModel.inventoryUnit, typeAffinity = ColumnInfo.TEXT)
    @SerializedName("inventoryUnit")
    val inventoryUnit = inventoryUnit

    @ColumnInfo(name = SqlModel.lastUseDate, typeAffinity = ColumnInfo.TEXT)
    @SerializedName("lastUseDate")
    val lastUseDate = lastUseDate

    @ColumnInfo(name = SqlModel.materialName, typeAffinity = ColumnInfo.TEXT)
    @SerializedName("materialName")
    val materialName = materialName

    @ColumnInfo(name = SqlModel.materialNumber, typeAffinity = ColumnInfo.TEXT)
    @SerializedName("materialNumber")
    val materialNumber = materialNumber

    @ColumnInfo(name = SqlModel.materialSpec, typeAffinity = ColumnInfo.TEXT)
    @SerializedName("materialSpec")
    val materialSpec = materialSpec

    @ColumnInfo(name = SqlModel.materialUnit, typeAffinity = ColumnInfo.TEXT)
    @SerializedName("materialUnit")
    val materialUnit = materialUnit

    @ColumnInfo(name = SqlModel.reportId, typeAffinity = ColumnInfo.TEXT)
    @SerializedName("reportId")
    var reportId = reportId

    @ColumnInfo(name = SqlModel.reportTitle, typeAffinity = ColumnInfo.TEXT)
    @SerializedName("reportTitle")
    var reportTitle = reportTitle

    @ColumnInfo(name = SqlModel.seq, typeAffinity = ColumnInfo.TEXT)
    @SerializedName("seq")
    val seq = seq

    @ColumnInfo(name = SqlModel.updatedAt, typeAffinity = ColumnInfo.TEXT)
    @SerializedName("updatedAt")
    val updatedAt = updatedAt

    @ColumnInfo(name = "isUpdate", typeAffinity = ColumnInfo.INTEGER)
    var isUpdate = isUpdate

}
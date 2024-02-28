package com.lhr.water.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = SqlModel.INVENTORY_TABLE_NAME, indices = [Index(value = [SqlModel.formNumber], unique = true)])
class InventoryEntity : Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = SqlModel.formNumber, typeAffinity = ColumnInfo.TEXT)
    var formNumber = ""

    @ColumnInfo(name = SqlModel.formContent, typeAffinity = ColumnInfo.TEXT)
    var formContent = ""
}
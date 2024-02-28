package com.lhr.water.room

import androidx.room.*
import com.lhr.water.room.SqlModel.Companion.INVENTORY_TABLE_NAME
import com.lhr.water.room.SqlModel.Companion.formContent

@Dao
interface InventoryDao {
    @Query("SELECT $formContent FROM $INVENTORY_TABLE_NAME")
    fun getInventoryForms(): List<String>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNewForm(inventoryEntity: InventoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(inventoryEntity: InventoryEntity)

    @Query("DELETE FROM $INVENTORY_TABLE_NAME")
    fun clearTable()
}
package com.lhr.water.room

import androidx.room.*
import com.lhr.water.room.SqlModel.Companion.STORAGE_TABLE_NAME

@Dao
interface StorageDao {
    @Query("SELECT * FROM $STORAGE_TABLE_NAME")
    fun getAll(): List<StorageEntity>

    @Query("SELECT COUNT(*) FROM $STORAGE_TABLE_NAME")
    fun getRowCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStorageEntities(storageEntities: List<StorageEntity>)

    @Query("SELECT COUNT(*) FROM $STORAGE_TABLE_NAME")
    fun getRecordCount(): Int

    @Query("DELETE FROM $STORAGE_TABLE_NAME")
    fun clearTable()
}
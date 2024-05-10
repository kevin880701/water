package com.lhr.water.room

import androidx.room.*
import com.lhr.water.room.SqlModel.Companion.STORAGE_RECORD_TABLE_NAME

@Dao
interface StorageRecordDao {
    @Query("SELECT * FROM $STORAGE_RECORD_TABLE_NAME")
    fun getAll(): List<StorageRecordEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStorageRecordEntities(storageContentEntities: List<StorageRecordEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStorageItem(storageContentEntities: StorageRecordEntity)

    @Query("SELECT * FROM $STORAGE_RECORD_TABLE_NAME WHERE storageId = :storageId")
    fun getStorageContentByStorageId(storageId: Int): List<StorageRecordEntity>

    @Query("SELECT COUNT(*) FROM $STORAGE_RECORD_TABLE_NAME")
    fun getRecordCount(): Int

    @Query("DELETE FROM $STORAGE_RECORD_TABLE_NAME")
    fun clearTable()

}
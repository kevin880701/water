package com.lhr.water.room

import androidx.room.*
import com.lhr.water.room.SqlModel.Companion.STORAGE_RECORD_TABLE_NAME

@Dao
interface StorageRecordDao {
    @Query("SELECT * FROM $STORAGE_RECORD_TABLE_NAME")
    fun getAllStorageContent(): List<StorageRecordEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStorageRecordList(storageContentEntities: List<StorageRecordEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStorageItem(storageContentEntities: StorageRecordEntity)

    @Query("SELECT * FROM $STORAGE_RECORD_TABLE_NAME WHERE regionName = :region AND mapName = :map AND storageNum = :storage")
    fun getStorageContentByConditions(region: String, map: String, storage: String): List<StorageRecordEntity>
}
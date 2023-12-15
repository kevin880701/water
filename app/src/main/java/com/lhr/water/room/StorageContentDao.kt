package com.lhr.water.room

import androidx.room.*
import com.lhr.water.room.SqlModel.Companion.STORAGE_CONTENT_NAME

@Dao
interface StorageContentDao {
    @Query("SELECT * FROM $STORAGE_CONTENT_NAME")
    fun getAllStorageContent(): List<StorageContentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStorageItemList(storageContentEntities: List<StorageContentEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStorageItem(storageContentEntities: StorageContentEntity)

    @Query("SELECT * FROM $STORAGE_CONTENT_NAME WHERE regionName = :region AND mapName = :map AND storageNum = :storage")
    fun getStorageContentByConditions(region: String, map: String, storage: String): List<StorageContentEntity>
}
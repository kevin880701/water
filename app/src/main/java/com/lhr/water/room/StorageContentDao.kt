package com.lhr.water.room

import androidx.room.*
import com.lhr.water.room.SqlModel.Companion.STORAGE_CONTENT_TABLE_NAME
import com.lhr.water.room.SqlModel.Companion.STORAGE_RECORD_TABLE_NAME

@Dao
interface StorageContentDao {
    @Query("SELECT * FROM $STORAGE_CONTENT_TABLE_NAME")
    fun getAllStorageContent(): List<StorageContentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(storageContentEntity: List<StorageContentEntity>)

    @Query("SELECT * FROM $STORAGE_CONTENT_TABLE_NAME WHERE " +
            "${SqlModel.regionName} = :regionName AND " +
            "${SqlModel.mapName} = :mapName AND " +
            "${SqlModel.storageName} = :storageName AND " +
            "${SqlModel.materialName} = :materialName AND " +
            "${SqlModel.materialNumber} = :materialNumber AND " +
            "${SqlModel.materialSpec} = :materialSpec AND " +
            "${SqlModel.materialUnit} = :materialUnit")
    fun getStorageContentByConditions(
        regionName: String,
        mapName: String,
        storageName: String,
        materialName: String,
        materialNumber: String,
        materialSpec: String,
        materialUnit: String,
    ): StorageContentEntity?

    @Query("SELECT * FROM $STORAGE_CONTENT_TABLE_NAME WHERE regionName = :region AND mapName = :map AND storageName = :storageName")
    fun getStorageContentByConditions(region: String, map: String, storageName: String): List<StorageContentEntity>

}
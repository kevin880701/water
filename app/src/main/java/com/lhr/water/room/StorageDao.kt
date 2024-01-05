package com.lhr.water.room

import androidx.room.*
import com.lhr.water.room.SqlModel.Companion.STORAGE_TABLE_NAME

@Dao
interface StorageDao {
    @Query("SELECT * FROM $STORAGE_TABLE_NAME")
    fun getAllStorage(): List<StorageEntity>

    @Query("SELECT COUNT(*) FROM $STORAGE_TABLE_NAME")
    fun getRowCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStorageEntities(storageEntities: List<StorageEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStorageEntity(storageEntity: StorageEntity)

    @Query("UPDATE $STORAGE_TABLE_NAME SET storageName = :newStorageName WHERE " +
            "regionName = :regionName AND mapName = :mapName AND storageName = :oldStorageName")
    fun updateStorage(
        regionName: String,
        mapName: String,
        oldStorageName: String,
        newStorageName: String
    )

    @Query(
        "DELETE FROM $STORAGE_TABLE_NAME WHERE " +
                "${SqlModel.regionName} = :regionName AND " +
                "${SqlModel.mapName} = :mapName AND " +
                "${SqlModel.storageName} = :storageName"
    )
    fun deleteByRegionMapAndStorage(regionName: String, mapName: String, storageName: String)
}
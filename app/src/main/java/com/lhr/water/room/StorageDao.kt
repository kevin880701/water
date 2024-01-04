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
    fun insertTargetEntities(storageEntities: List<StorageEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTargetEntity(storageEntity: StorageEntity)

    @Query("DELETE FROM $STORAGE_TABLE_NAME WHERE " +
            "${SqlModel.regionName} = :regionName AND " +
            "${SqlModel.mapName} = :mapName AND " +
            "${SqlModel.storageNum} = :storageNum")
    fun deleteByRegionMapAndStorage(regionName: String, mapName: String, storageNum: String)
}
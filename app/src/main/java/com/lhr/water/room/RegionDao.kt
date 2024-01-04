package com.lhr.water.room

import androidx.room.*
import com.lhr.water.room.SqlModel.Companion.REGION_TABLE_NAME

@Dao
interface RegionDao {
    @Query("SELECT * FROM $REGION_TABLE_NAME")
    fun getAllRegion(): List<RegionEntity>

    @Query("SELECT COUNT(*) FROM $REGION_TABLE_NAME")
    fun getRowCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRegionEntities(targetEntities: List<RegionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRegionEntity(targetEntities: RegionEntity)
}
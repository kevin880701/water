package com.lhr.water.room

import androidx.room.*
import com.lhr.water.room.SqlModel.Companion.MAP_TABLE_NAME
import com.lhr.water.room.SqlModel.Companion.REGION_TABLE_NAME

@Dao
interface MapDao {
    @Query("SELECT * FROM $MAP_TABLE_NAME")
    fun getAllMap(): List<MapEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMapEntities(mapEntities: List<MapEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMapEntity(mapEntities: MapEntity)
}
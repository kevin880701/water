package com.lhr.water.room

import androidx.room.*
import com.lhr.water.room.SqlModel.Companion.TARGET_TABLE_NAME

@Dao
interface TargetDao {
    @Query("SELECT * FROM $TARGET_TABLE_NAME")
    fun getAll(): List<TargetEntity>

    @Query("SELECT COUNT(*) FROM $TARGET_TABLE_NAME")
    fun getRowCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTargetEntities(targetEntities: List<TargetEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTargetEntity(targetEntities: TargetEntity)
}
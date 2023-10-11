package com.lhr.water.room

import androidx.room.*
import com.lhr.water.room.SqlModel.Companion.TARGET_TABLE_NAME

@Dao
interface TargetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun importCsv(item: TargetEntity)

    @Query("SELECT * FROM " + TARGET_TABLE_NAME)
    fun getAll(): List<TargetEntity>


}
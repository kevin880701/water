package com.lhr.water.room

import androidx.room.*
import com.lhr.water.room.SqlModel.Companion.TARGET_TABLE_NAME

@Dao
interface StorageContentDao {
    @Query("SELECT * FROM $TARGET_TABLE_NAME")
    fun getAll(): List<TargetEntity>

    @Query("SELECT COUNT(*) FROM $TARGET_TABLE_NAME")
    fun getRowCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStorageItem(storageContentEntities: List<StorageContentEntity>)
}
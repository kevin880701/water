package com.lhr.water.room

import androidx.room.*
import com.lhr.water.room.SqlModel.Companion.FORM_TABLE_NAME
import com.lhr.water.room.SqlModel.Companion.formContent

@Dao
interface FormDao {

    @Query("SELECT * FROM $FORM_TABLE_NAME")
    fun getAll(): List<FormEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(formEntity: FormEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFormEntities(targetEntities: List<FormEntity>)

    @Query("DELETE FROM $FORM_TABLE_NAME")
    fun clearTable()

    @Update
    fun update(formEntity: FormEntity)

    @Query("SELECT * FROM $FORM_TABLE_NAME WHERE isUpdate = 0")
    fun getAllNotUpdated(): List<FormEntity>

}
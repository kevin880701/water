package com.lhr.water.room

import androidx.room.*
import com.lhr.water.room.SqlModel.Companion.FORM_TABLE_NAME
import com.lhr.water.room.SqlModel.Companion.formContent

@Dao
interface FormDao {

    @Query("SELECT $formContent FROM $FORM_TABLE_NAME")
    fun getAll(): List<String>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNewForm(formEntity: FormEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(formEntity: FormEntity)

    @Query("DELETE FROM $FORM_TABLE_NAME")
    fun clearTable()
}
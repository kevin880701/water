package com.lhr.water.room

import androidx.room.*
import com.lhr.water.room.SqlModel.Companion.FORM_TABLE_NAME
import com.lhr.water.room.SqlModel.Companion.formContent

@Dao
interface FormDao {

    @Query("SELECT $formContent FROM $FORM_TABLE_NAME")
    fun getAll(): List<String>

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insert(item: DeliveryEntity)

//    @Query("INSERT INTO $FORM_TABLE_NAME (formContent) VALUES (:formContent)")
//    fun insert(formContent: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(formEntity: FormEntity)
}
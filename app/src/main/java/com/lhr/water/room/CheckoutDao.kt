package com.lhr.water.room

import androidx.room.*
import com.lhr.water.room.SqlModel.Companion.CHECKOUT_TABLE_NAME

@Dao
interface CheckoutDao {
    @Query("SELECT * FROM $CHECKOUT_TABLE_NAME")
    fun getAll(): List<CheckoutEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(storageContentEntity: List<CheckoutEntity>)

    @Query("SELECT * FROM $CHECKOUT_TABLE_NAME WHERE " +
            "${SqlModel.storageId} = :storageId")
    fun getCheckoutByStorageId(
        storageId: Int
    ): CheckoutEntity?
}
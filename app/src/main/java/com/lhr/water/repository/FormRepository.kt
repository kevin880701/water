package com.lhr.water.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.lhr.water.room.FormEntity
import com.lhr.water.room.InventoryEntity
import com.lhr.water.room.SqlDatabase
import com.lhr.water.room.CheckoutEntity
import com.lhr.water.room.StorageRecordEntity
import com.lhr.water.util.formTypeMap

class FormRepository(context: Context) {
    val context = context

    // 表單列表
    var formEntities = MutableLiveData<ArrayList<FormEntity>>(ArrayList<FormEntity>())

    // 盤點表單列表
    var inventoryEntities = MutableLiveData<ArrayList<InventoryEntity>>(ArrayList<InventoryEntity>())

    // 暫存待出入庫的貨物列表（未送出）
    var tempStorageRecordEntities = MutableLiveData<ArrayList<StorageRecordEntity>>(ArrayList<StorageRecordEntity>())

    var checkoutEntities = MutableLiveData<ArrayList<CheckoutEntity>>(ArrayList<CheckoutEntity>())

    var storageRecordEntities = MutableLiveData<ArrayList<StorageRecordEntity>>(ArrayList<StorageRecordEntity>())

    companion object {
        private var instance: FormRepository? = null
        fun getInstance(context: Context): FormRepository {
            if (instance == null) {
                instance = FormRepository(context)
            }
            return instance!!
        }
    }

    init {
        updateData()
    }

    /**
     * 抓取表單全部記錄
     */
    fun updateData() {
        // 取表單資料
        formEntities.postValue(SqlDatabase.getInstance().getFormDao().getAll() as ArrayList)
        // 取盤點表單資料
        inventoryEntities.postValue(SqlDatabase.getInstance().getInventoryDao().getAll() as ArrayList)
        // 取checkout資料
        checkoutEntities.postValue(SqlDatabase.getInstance().getCheckoutDao().getAll() as ArrayList<CheckoutEntity>)
        // 取儲櫃紀錄資料
        storageRecordEntities.postValue(SqlDatabase.getInstance().getStorageRecordDao().getAll() as ArrayList<StorageRecordEntity>)
    }

    /**
     * 根據表單名稱、表單代號、貨物名稱代號篩選暫存待入庫的貨物列表中指定的貨物數量
     * @param targetReportTitle 指定表單名稱
     * @param targetFormNumber 指定表單代號
     * @param materialNumber 指定貨物在表單中的序號(非materialNumber)
     */
    fun getMaterialQuantityByTempWaitInputGoods(
        targetReportTitle: String,
        targetFormNumber: String,
        materialNumber: String
    ): Int {
        var totalQuantity = 0
        // 篩選
        val filteredList = tempStorageRecordEntities.value!!.filter { data ->
            data.formType == formTypeMap[targetReportTitle] &&
            data.formNumber == targetFormNumber &&
                    data.materialNumber == materialNumber

        }

        // 篩選後的List中數量加總
        for (storageContentEntity in filteredList) {
                totalQuantity += storageContentEntity.quantity
        }
        return totalQuantity
    }


    /**
     * 根據表單名稱和表單代號篩選暫存待出入庫的材料列表（tempStorageRecordEntities）
     * @param tempWaitInputGoods 要篩選的待入庫清單
     * @param targetReportTitle 指定表單名稱
     * @param targetFormNumber 指定表單代號
     */
    fun filterTempStorageRecordEntities(
        tempWaitInputGoods: ArrayList<StorageRecordEntity>,
        targetReportTitle: String,
        targetFormNumber: String
    ): ArrayList<StorageRecordEntity> {
        val filteredList = tempWaitInputGoods.filter { data ->
            data.formType == formTypeMap[targetReportTitle] && data.formNumber == targetFormNumber
        }

        val resultArrayList = ArrayList<StorageRecordEntity>()
        filteredList.toCollection(resultArrayList)

        return resultArrayList
    }
}
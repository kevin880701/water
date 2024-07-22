package com.lhr.water.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.lhr.water.data.form.DeliveryForm
import com.lhr.water.data.form.ReceiveForm
import com.lhr.water.data.form.ReturnForm
import com.lhr.water.data.form.TransferForm
import com.lhr.water.network.data.response.UpdateDataResponse
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
    var inventoryEntities =
        MutableLiveData<ArrayList<InventoryEntity>>(ArrayList<InventoryEntity>())

    // 暫存待出入庫的貨物列表（未送出）
    var tempStorageRecordEntities =
        MutableLiveData<ArrayList<StorageRecordEntity>>(ArrayList<StorageRecordEntity>())

    var checkoutEntities = MutableLiveData<ArrayList<CheckoutEntity>>(ArrayList<CheckoutEntity>())

    var storageRecordEntities =
        MutableLiveData<ArrayList<StorageRecordEntity>>(ArrayList<StorageRecordEntity>())

    var isInventoryCompleted = MutableLiveData<Boolean>(false)

    private val sqlDatabase = SqlDatabase.getInstance()

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
        loadSqlData()
    }

    /**
     * 抓取表單全部記錄
     */
    fun loadSqlData() {
        // 取表單資料
        var formList = sqlDatabase.getFormDao().getAll().toMutableList() // 确保得到的是一个 MutableList
        val sortedList = formList.sortedWith(compareByDescending<FormEntity> { it.dealStatus == "處理中" }
            .thenBy { it.dealStatus })
        formEntities.postValue(ArrayList(sortedList))
        // 取盤點表單資料
        inventoryEntities.postValue(sqlDatabase.getInventoryDao().getAll() as ArrayList)
        // 取checkout資料
        checkoutEntities.postValue(
            sqlDatabase.getCheckoutDao().getAll() as ArrayList<CheckoutEntity>
        )
        // 取儲櫃紀錄資料
        storageRecordEntities.postValue(
            sqlDatabase.getStorageRecordDao().getAll() as ArrayList<StorageRecordEntity>
        )
    }


    fun updateSqlData(
        checkoutFormList: List<CheckoutEntity>,
        storageRecordList: List<StorageRecordEntity>,
        deliveryFormList: List<DeliveryForm>,
        transferFormList: List<TransferForm>,
        receiveFormList: List<ReceiveForm>,
        returnFormList: List<ReturnForm>,
        inventoryFormList: List<InventoryEntity>
    ) {

        // 先清除資料表
        sqlDatabase.getFormDao().clearTable()
        sqlDatabase.getCheckoutDao().clearTable()
        sqlDatabase.getStorageRecordDao().clearTable()
        sqlDatabase.getInventoryDao().clearTable()

        // 將updateDataResponse的儲櫃紀錄、月結表插入資料表
        sqlDatabase.getCheckoutDao()
            .insertCheckoutEntities(checkoutFormList)
        sqlDatabase.getStorageRecordDao()
            .insertStorageRecordEntities(storageRecordList)

        //將交貨、領料、退料、調撥轉成FormEntity格式
        val deliveryFormEntities =
            FormEntity.convertFormToFormEntities(deliveryFormList)
        val transferFormEntities =
            FormEntity.convertFormToFormEntities(transferFormList)
        val receiveFormEntities =
            FormEntity.convertFormToFormEntities(receiveFormList)
        val returnFormEntities =
            FormEntity.convertFormToFormEntities(returnFormList)

        // 插入到資料表中
        sqlDatabase.getFormDao().insertFormEntities(deliveryFormEntities)
        sqlDatabase.getFormDao().insertFormEntities(transferFormEntities)
        sqlDatabase.getFormDao().insertFormEntities(receiveFormEntities)
        sqlDatabase.getFormDao().insertFormEntities(returnFormEntities)

        // 插入到資料表中
        sqlDatabase.getInventoryDao()
            .insertInventoryEntities(inventoryFormList)

        // 更新資料
        loadSqlData()
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
            totalQuantity += storageContentEntity.quantity.toInt()
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
package com.lhr.water.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.lhr.water.data.Form
import com.lhr.water.data.Form.Companion.formFromJson
import com.lhr.water.data.Form.Companion.toJsonString
import com.lhr.water.room.FormEntity
import com.lhr.water.room.InventoryEntity
import com.lhr.water.room.SqlDatabase
import com.lhr.water.room.CheckoutEntity
import com.lhr.water.room.StorageRecordEntity
import com.lhr.water.util.FormName.inventoryFormName
import com.lhr.water.util.formTypeMap
import org.json.JSONArray

class FormRepository(context: Context) {
    val context = context

    // 表單列表
    var formEntities = MutableLiveData<ArrayList<FormEntity>>(ArrayList<FormEntity>())

    // 盤點表單列表
    var inventoryEntities = MutableLiveData<ArrayList<InventoryEntity>>(ArrayList<InventoryEntity>())

    // 暫存待出入庫的貨物列表（未送出）
    var tempStorageRecordEntities = MutableLiveData<ArrayList<StorageRecordEntity>>()

    // 儲櫃中所有貨物
    var storageGoods =
        MutableLiveData<ArrayList<CheckoutEntity>>(ArrayList<CheckoutEntity>())

    // 篩選表單代號formNumber的String
    var searchInventoryFormNumber = MutableLiveData<String>()

    var checkoutEntities = ArrayList<CheckoutEntity>()

    var storageRecordEntities = ArrayList<StorageRecordEntity>()

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
        checkoutEntities = SqlDatabase.getInstance().getCheckoutDao().getAll() as ArrayList<CheckoutEntity>
        // 取儲櫃紀錄資料
        storageRecordEntities = SqlDatabase.getInstance().getStorageRecordDao().getAll() as ArrayList<StorageRecordEntity>
    }

    /**
     * 篩選盤點表單內容
     */
//    fun filterInventoryRecord(formList: ArrayList<InventoryEntity>): ArrayList<InventoryEntity>? {
//        return formList.filter { form ->
//            // 根據 "FormClass" 判斷是否在 filterList 中
//            val formNumber = form.formNumber.toString()
//
//            // 如果搜尋框(EditText)中的文本不為空，則判斷 "formNumber" 是否包含該文本
//            val editTextFilterCondition = if (searchFormNumber.value?.isNotEmpty() == true) {
//                formNumber.contains(searchFormNumber.value!!, ignoreCase = true)
//            } else {
//                true // 搜尋框(EditText)，不添加 formNumber 的篩選條件
//            }
//            editTextFilterCondition
//        }?.toMutableList()!! as ArrayList<InventoryEntity>?
//    }

    /**
     * 匯入新json時要同步插入到資料庫中
     * @param jsonArray 要匯入的JSONArray
     */
    fun insertNewForm(jsonArray: JSONArray) {
        // 清空表
        SqlDatabase.getInstance().getFormDao().clearTable()

        // 將 JSONArray 中的數據逐一插入表中
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)

            val form: Form = formFromJson(jsonObject.toString())
            println(form.toJsonString())
            if(form.reportTitle == inventoryFormName){
                val jsonObject = jsonArray.getJSONObject(i)
                val inventoryEntity = InventoryEntity(
                    formNumber = jsonObject.optString("deptName").toString() + jsonObject.optString("date").toString() + jsonObject.optString("seq").toString(),
                    dealStatus = form.dealStatus!!,
                    reportId = form.reportId!!,
                    reportTitle = form.reportTitle!!,
                    dealTime = form.dealTime!!,
                    date = form.date!!,
                    formContent = jsonObject.toString(),
                )
                inventoryEntity.formNumber = jsonObject.optString("deptName").toString() + jsonObject.optString("date").toString() + jsonObject.optString("seq").toString()
                inventoryEntity.formContent = jsonObject.toString()
                SqlDatabase.getInstance().getInventoryDao().insertNewForm(inventoryEntity)
                loadInventoryForm()
            }else{
                val formEntity = FormEntity(
                    formNumber = form.formNumber!!,
                    dealStatus = form.dealStatus!!,
                    reportId = form.reportId!!,
                    reportTitle = form.reportTitle!!,
                    dealTime = form.dealTime!!,
                    date = form.date!!,
                    formContent = form.toJsonString(),
                )
                SqlDatabase.getInstance().getFormDao().insertNewForm(formEntity)
                updateData()
            }
        }
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

    /**
     * 抓取表單全部記錄
     */
    fun loadInventoryForm(): ArrayList<InventoryEntity> {
        val loadFormList: List<InventoryEntity> = SqlDatabase.getInstance().getInventoryDao().getAll()
        inventoryEntities.postValue(loadFormList as ArrayList<InventoryEntity>)
        return loadFormList
    }
}
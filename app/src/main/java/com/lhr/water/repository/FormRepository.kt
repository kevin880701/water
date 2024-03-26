package com.lhr.water.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.lhr.water.R
import com.lhr.water.data.Form
import com.lhr.water.data.Form.Companion.formFromJson
import com.lhr.water.data.Form.Companion.toJsonString
import com.lhr.water.data.TempDealGoodsData
import com.lhr.water.data.WaitDealGoodsData
import com.lhr.water.room.FormEntity
import com.lhr.water.room.InventoryEntity
import com.lhr.water.room.SqlDatabase
import com.lhr.water.room.StorageContentEntity
import com.lhr.water.room.StorageRecordEntity
import com.lhr.water.util.DealStatus.nowDeal
import com.lhr.water.util.FormName.deliveryFormName
import com.lhr.water.util.FormName.inventoryFormName
import com.lhr.water.util.FormName.pickingFormName
import com.lhr.water.util.FormName.returningFormName
import com.lhr.water.util.FormName.transferFormName
import com.lhr.water.util.TransferStatus
import com.lhr.water.util.manager.jsonStringToJson
import com.lhr.water.util.transferStatus
import org.json.JSONArray
import org.json.JSONObject

class FormRepository(context: Context) {
    val context = context

    // 所有表單列表
    var formRecordList: MutableLiveData<ArrayList<Form>> =
        MutableLiveData<ArrayList<Form>>()

    // 待出貨的貨物列表
    var waitOutputGoods: MutableLiveData<ArrayList<WaitDealGoodsData>> =
        MutableLiveData<ArrayList<WaitDealGoodsData>>()
    // 待入庫的貨物列表
    var waitInputGoods: MutableLiveData<ArrayList<WaitDealGoodsData>> =
        MutableLiveData<ArrayList<WaitDealGoodsData>>()

    // 暫存待出貨的貨物列表（未送出）
    var tempWaitOutputGoods: MutableLiveData<ArrayList<TempDealGoodsData>> =
        MutableLiveData<ArrayList<TempDealGoodsData>>()
    // 暫存待入庫的貨物列表（未送出）
    var tempWaitInputGoods: MutableLiveData<ArrayList<TempDealGoodsData>> =
        MutableLiveData<ArrayList<TempDealGoodsData>>()

    // 儲櫃所有進出紀錄
    var storageRecords: MutableLiveData<ArrayList<StorageRecordEntity>> =
        MutableLiveData<ArrayList<StorageRecordEntity>>()
    // 儲櫃中所有貨物
    var storageGoods: MutableLiveData<ArrayList<StorageContentEntity>> =
        MutableLiveData<ArrayList<StorageContentEntity>>()

    // 篩選後的表單
    var formFilterRecordList: MutableLiveData<ArrayList<JSONObject>> =
        MutableLiveData<ArrayList<JSONObject>>()

    // 篩選表單類別FormClass的List
    var filterList = MutableLiveData<ArrayList<String>>()

    // 篩選表單代號formNumber的String
    var searchFormNumber = MutableLiveData<String>()

    // 盤點表單
    var inventoryEntities = MutableLiveData<ArrayList<InventoryEntity>>()
    var inventoryFormList: MutableLiveData<ArrayList<JSONObject>> =
        MutableLiveData<ArrayList<JSONObject>>()

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
        filterList.value = ArrayList(context.resources.getStringArray(R.array.form_array).toList())
        val loadFormList: List<String> = SqlDatabase.getInstance().getFormDao().getAll()
        val formJsonList = ArrayList<JSONObject>()
        for (formData in loadFormList) {
            formJsonList.add(jsonStringToJson(formData))
        }
        waitOutputGoods.value = ArrayList<WaitDealGoodsData>()
        waitInputGoods.value = ArrayList<WaitDealGoodsData>()
        tempWaitInputGoods.value = ArrayList<TempDealGoodsData>()
        tempWaitOutputGoods.value = ArrayList<TempDealGoodsData>()
        storageRecords.value = ArrayList<StorageRecordEntity>()
        storageGoods.value = ArrayList<StorageContentEntity>()
        loadRecord()
//        formRecordList.value = loadRecord()
//        inventoryFormList.value = loadInventoryForm()
        inventoryEntities.value = loadInventoryForm()
    }

    /**
     * 抓取表單全部記錄
     */
    fun loadRecord() {
        val loadFormList: List<String> = SqlDatabase.getInstance().getFormDao().getAll()
        val tempFormList = ArrayList<Form>()
        for (formData in loadFormList) {
            tempFormList.add(formFromJson(formData))
        }
        formRecordList.postValue(tempFormList)
        formFilterRecordList.postValue(filterRecord(tempFormList))
        updateWaitInputGoods(tempFormList)
        updateWaitOutputGoods(tempFormList)
//        return formJsonList
    }

    /**
     * 更新待入庫貨物列表
     */
    fun updateWaitInputGoods(formList: ArrayList<Form>) {
        var waitInputGoodsList = ArrayList<WaitDealGoodsData>()
        for (form in formList) {
            val reportTitle = form.reportTitle
            val dealStatus = form.dealStatus


            var transferStatus = transferStatus(
                reportTitle == transferFormName,
                form
            )

            if ((reportTitle == deliveryFormName ||
                        reportTitle == returningFormName ||
                        transferStatus == TransferStatus.transferInput) && dealStatus == nowDeal
            ) {
                val itemDetails = form.itemDetails
                if (itemDetails != null && itemDetails.size > 0) {
                    for (itemDetail in itemDetails) {
                        val waitDealGoodsData = WaitDealGoodsData(
                            reportTitle = form.reportTitle.toString(),
                            formNumber = form.formNumber.toString(),
                            itemDetail = itemDetail
                        )
                        waitInputGoodsList.add(waitDealGoodsData)
                    }
                }
            }
        }
        // 更新已入庫的貨物
        updateStorageRecords()
        updateStorageGoods()

        // 這裡需要把已入庫的貨物從waitInputGoods中刪除
        waitInputGoodsList.removeAll { waitDealGoodsData ->
            storageRecords.value!!.any { storageContentEntity ->
                // 透過表單代號(formNumber)和物品編號(number)來做篩選
                storageContentEntity.formNumber == waitDealGoodsData.formNumber &&
                        jsonStringToJson(storageContentEntity.itemDetail).getString("number") == waitDealGoodsData.itemDetail.number
            }
        }
        val modWaitInputGoodsList = waitInputGoodsList.map { originalItem ->
            // 創建新的 WaitDealGoodsData
            WaitDealGoodsData(
                reportTitle = originalItem.reportTitle,
                formNumber = originalItem.formNumber,
                itemDetail = originalItem.itemDetail
            )
        } as ArrayList<WaitDealGoodsData>

        waitInputGoods.postValue(modWaitInputGoodsList)
    }


    /**
     * 更新待出庫貨物列表
     */
    fun updateWaitOutputGoods(formList: ArrayList<Form>) {
        var waitOutputGoodsList = ArrayList<WaitDealGoodsData>()
        for (form in formList) {
            val reportTitle = form.reportTitle
            val dealStatus = form.dealStatus

            var transferStatus = transferStatus(
                reportTitle == transferFormName,
                form
            )

            if ((reportTitle == pickingFormName ||
                        reportTitle == returningFormName ||
                        transferStatus == TransferStatus.transferOutput) && dealStatus == nowDeal
            ) {
                val itemDetails = form.itemDetails
                if (itemDetails != null && itemDetails.size > 0) {
                    for (itemDetail in itemDetails) {
                        val waitDealGoodsData = WaitDealGoodsData(
                            reportTitle = reportTitle.toString(),
                            formNumber = form.formNumber.toString(),
                            itemDetail = itemDetail
                        )
                        waitOutputGoodsList.add(waitDealGoodsData)
                    }
                }
            }
        }
        waitOutputGoods.postValue(waitOutputGoodsList)
    }


    /**
     * 更新儲櫃中的所有貨物
     */
    private fun updateStorageRecords() {
        storageRecords.postValue(SqlDatabase.getInstance().getStorageRecordDao().getAllStorageContent() as ArrayList)
    }


    /**
     * 更新儲櫃中的所有貨物
     */
    private fun updateStorageGoods() {
        storageGoods.postValue(SqlDatabase.getInstance().getStorageContentDao().getAllStorageContent() as ArrayList)
    }

    /**
     * 篩選表單內容
     */
    fun filterRecord(formList: ArrayList<Form>): ArrayList<JSONObject>? {
        return formList.filter { form ->
            // 根據 "FormClass" 判斷是否在 filterList 中
            val reportTitle = form.reportTitle.toString()
            val reportTitleFilterCondition = filterList.value?.contains(reportTitle)
            val formNumber = form.formNumber.toString()

            // 如果搜尋框(EditText)中的文本不為空，則判斷 "formNumber" 是否包含該文本
            val editTextFilterCondition = if (searchFormNumber.value?.isNotEmpty() == true) {
                formNumber.contains(searchFormNumber.value!!, ignoreCase = true)
            } else {
                true // 搜尋框(EditText)，不添加 formNumber 的篩選條件
            }
            reportTitleFilterCondition!! && editTextFilterCondition
        }?.toMutableList()!! as ArrayList<JSONObject>?
//        )
    }

    /**
     * 根據 regionName、mapName 和 storageName 查詢指定儲櫃內容物
     * @param regionName 地區名稱
     * @param mapName 地圖名稱
     * @param storageName 儲櫃名稱
     */
    fun getStorageContentByCondition(
        regionName: String,
        mapName: String,
        storageName: String
    ): ArrayList<StorageContentEntity> {
        return SqlDatabase.getInstance().getStorageContentDao()
            .getStorageContentByConditions(regionName, mapName, storageName) as ArrayList
    }

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
                val inventoryEntity = InventoryEntity()
                inventoryEntity.formNumber = jsonObject.optString("deptName").toString() + jsonObject.optString("date").toString() + jsonObject.optString("seq").toString()
                inventoryEntity.formContent = jsonObject.toString()
                SqlDatabase.getInstance().getInventoryDao().insertNewForm(inventoryEntity)
                loadInventoryForm()
            }else{
                val formEntity = FormEntity()
                formEntity.formNumber = form.formNumber.toString()
                formEntity.formContent = form.toJsonString()
                SqlDatabase.getInstance().getFormDao().insertNewForm(formEntity)
                loadRecord()
            }
        }
    }

    /**
     * 根據表單名稱和表單代號篩選待入庫清單
     * @param waitInputGoods 要篩選的待入庫清單
     * @param targetReportTitle 指定表單名稱
     * @param targetFormNumber 指定表單代號
     */
    fun filterWaitInputGoods(
        waitInputGoods: ArrayList<WaitDealGoodsData>,
        targetReportTitle: String,
        targetFormNumber: String
    ): ArrayList<WaitDealGoodsData> {
        val filteredList = waitInputGoods.filter { data ->
            data.reportTitle == targetReportTitle && data.formNumber == targetFormNumber
        }

        val resultArrayList = ArrayList<WaitDealGoodsData>()
        filteredList.toCollection(resultArrayList)

        return resultArrayList
    }


    /**
     * 根據表單名稱、表單代號、貨物名稱代號篩選暫存待入庫的貨物列表中指定的貨物數量
     * @param targetReportTitle 指定表單名稱
     * @param targetFormNumber 指定表單代號
     * @param targetNumber 指定貨物在表單中的序號(非materialNumber)
     */
    fun getMaterialQuantityByTempWaitInputGoods(
        targetReportTitle: String,
        targetFormNumber: String,
        targetNumber: String
    ): Int {
        var totalQuantity = 0
        // 篩選
        val filteredList = tempWaitInputGoods.value!!.filter { data ->
            data.reportTitle == targetReportTitle &&
            data.formNumber == targetFormNumber &&
                    data.itemDetail.number == targetNumber

        }

        // 篩選後的List中數量加總
        for (storageContentEntity in filteredList) {
                totalQuantity += storageContentEntity.quantity
        }
        return totalQuantity
    }


    /**
     * 根據表單名稱和表單代號篩選暫存待入庫的貨物列表（未送出）
     * @param tempWaitInputGoods 要篩選的待入庫清單
     * @param targetReportTitle 指定表單名稱
     * @param targetFormNumber 指定表單代號
     */
    fun filterTempWaitInputGoods(
        tempWaitInputGoods: ArrayList<TempDealGoodsData>,
        targetReportTitle: String,
        targetFormNumber: String
    ): ArrayList<TempDealGoodsData> {
        val filteredList = tempWaitInputGoods.filter { data ->
            data.reportTitle == targetReportTitle && data.formNumber == targetFormNumber
        }

        val resultArrayList = ArrayList<TempDealGoodsData>()
        filteredList.toCollection(resultArrayList)

        return resultArrayList
    }

    /**
     * 根據表單名稱和表單代號篩選待出庫清單
     * @param waitInputGoods 要篩選的待入庫清單
     * @param targetReportTitle 指定表單名稱
     * @param targetFormNumber 指定表單代號
     */
    fun filterWaitOutputGoods(
        waitOutputGoods: ArrayList<WaitDealGoodsData>,
        targetReportTitle: String,
        targetFormNumber: String
    ): ArrayList<WaitDealGoodsData> {
        val filteredList = waitOutputGoods.filter { data ->
            data.reportTitle == targetReportTitle && data.formNumber == targetFormNumber
        }

        val resultArrayList = ArrayList<WaitDealGoodsData>()
        filteredList.toCollection(resultArrayList)

        return resultArrayList
    }


    /**
     * 根據表單名稱和表單代號篩選暫存待出庫的貨物列表（未送出）
     * @param tempWaitOutputGoods 要篩選的待出庫清單
     * @param targetReportTitle 指定表單名稱
     * @param targetFormNumber 指定表單代號
     */
    fun filterTempWaitOutputGoods(
        tempWaitOutputGoods: ArrayList<TempDealGoodsData>,
        targetReportTitle: String,
        targetFormNumber: String
    ): ArrayList<TempDealGoodsData> {
        val filteredList = tempWaitOutputGoods.filter { data ->
            data.reportTitle == targetReportTitle && data.formNumber == targetFormNumber
        }

        val resultArrayList = ArrayList<TempDealGoodsData>()
        filteredList.toCollection(resultArrayList)

        return resultArrayList
    }


    /**
     * 根據表單名稱、表單代號、貨物名稱代號篩選暫存待出庫的貨物列表中指定的貨物數量
     * @param targetReportTitle 指定表單名稱
     * @param targetFormNumber 指定表單代號
     * @param targetNumber 指定貨物在表單中的序號(非materialNumber)
     */
    fun getMaterialQuantityByTempWaitOutputGoods(
        targetReportTitle: String,
        targetFormNumber: String,
        targetNumber: String
    ): Int {
        var totalQuantity = 0
        // 篩選
        val filteredList = tempWaitOutputGoods.value!!.filter { data ->
            data.reportTitle == targetReportTitle &&
                    data.formNumber == targetFormNumber && data.itemDetail.number == targetNumber

        }
        // 篩選後的List中數量加總
        for (storageContentEntity in filteredList) {
            totalQuantity += storageContentEntity.quantity
        }
        return totalQuantity
    }

    fun searchStorageContentByMaterialName(targetMaterialName: String): ArrayList<StorageContentEntity>{
        return storageGoods.value!!.filter { it.materialName.contains(targetMaterialName) } as ArrayList<StorageContentEntity>
    }

    /**
     * 匯入新json時要同步插入到資料庫中
     * @param jsonArray 要匯入的JSONArray
     */
    fun insertInventoryForm(jsonArray: JSONArray) {
        // 清空表
//        SqlDatabase.getInstance().getDeliveryDao().clearTable()
        // 將 JSONArray 中的數據逐一插入表中
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val inventoryEntity = InventoryEntity()
            inventoryEntity.formNumber = jsonObject.optString("deptName").toString() + jsonObject.optString("date").toString() + jsonObject.optString("seq").toString()
            inventoryEntity.formContent = jsonObject.toString()
            SqlDatabase.getInstance().getInventoryDao().insertNewForm(inventoryEntity)
        }
    }

    /**
     * 抓取表單全部記錄
     */
//    fun loadInventoryForm(): ArrayList<JSONObject> {
//        val loadFormList: List<String> = SqlDatabase.getInstance().getInventoryDao().getInventoryForms()
//        val formJsonList = ArrayList<JSONObject>()
//        for (formData in loadFormList) {
//            formJsonList.add(jsonStringToJson(formData))
//        }
////        formRecordList.value = formJsonList
////        formFilterRecordList.value = formJsonList
////        formFilterRecordList.value = filterRecord()
//        inventoryFormList.postValue(formJsonList)
////        formFilterRecordList.postValue(formJsonList)
////        formFilterRecordList.postValue(filterRecord(formJsonList))
////        updateWaitInputGoods(formJsonList)
////        updateWaitOutputGoods(formJsonList)
//        return formJsonList
//    }


    /**
     * 抓取表單全部記錄
     */
    fun loadInventoryForm(): ArrayList<InventoryEntity> {
        val loadFormList: List<InventoryEntity> = SqlDatabase.getInstance().getInventoryDao().getInventoryForms()
//        val formJsonList = ArrayList<JSONObject>()
//        for (formData in loadFormList) {
//            formJsonList.add(jsonStringToJson(formData))
//        }
        inventoryEntities.postValue(loadFormList as ArrayList<InventoryEntity>)
        return loadFormList as ArrayList<InventoryEntity>
    }
}
package com.lhr.water.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.lhr.water.R
import com.lhr.water.data.WaitDealGoodsData
import com.lhr.water.room.FormEntity
import com.lhr.water.room.SqlDatabase
import com.lhr.water.room.StorageContentEntity
import com.lhr.water.room.StorageRecordEntity
import com.lhr.water.util.DealStatus.nowDeal
import com.lhr.water.util.FormName.deliveryFormName
import com.lhr.water.util.FormName.pickingFormName
import com.lhr.water.util.FormName.returningFormName
import com.lhr.water.util.FormName.transferFormName
import com.lhr.water.util.TransferStatus
import com.lhr.water.util.manager.jsonStringToJson
import com.lhr.water.util.transferStatus
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber

class FormRepository(context: Context) {
    val context = context

    // 所有表單列表
    var formRecordList: MutableLiveData<ArrayList<JSONObject>> =
        MutableLiveData<ArrayList<JSONObject>>()

    // 待出貨的貨物列表
    var waitOutputGoods: MutableLiveData<ArrayList<WaitDealGoodsData>> =
        MutableLiveData<ArrayList<WaitDealGoodsData>>()

    // 待入庫的貨物列表
    var waitInputGoods: MutableLiveData<ArrayList<WaitDealGoodsData>> =
        MutableLiveData<ArrayList<WaitDealGoodsData>>()

    // 暫存待出貨的貨物列表（未送出）
    var tempWaitOutputGoods: MutableLiveData<ArrayList<StorageRecordEntity>> =
        MutableLiveData<ArrayList<StorageRecordEntity>>()

    // 暫存待入庫的貨物列表（未送出）
    var tempWaitInputGoods: MutableLiveData<ArrayList<StorageRecordEntity>> =
        MutableLiveData<ArrayList<StorageRecordEntity>>()

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
        val loadFormList: List<String> = SqlDatabase.getInstance().getDeliveryDao().getAll()
        val formJsonList = ArrayList<JSONObject>()
        for (formData in loadFormList) {
            formJsonList.add(jsonStringToJson(formData))
        }
        waitOutputGoods.value = ArrayList<WaitDealGoodsData>()
        waitInputGoods.value = ArrayList<WaitDealGoodsData>()
        tempWaitOutputGoods.value = ArrayList<StorageRecordEntity>()
        tempWaitInputGoods.value = ArrayList<StorageRecordEntity>()
        storageRecords.value = ArrayList<StorageRecordEntity>()
        storageGoods.value = ArrayList<StorageContentEntity>()
        formRecordList.value = loadRecord()
    }

    /**
     * 抓取表單全部記錄
     */
    fun loadRecord(): ArrayList<JSONObject> {
        val loadFormList: List<String> = SqlDatabase.getInstance().getDeliveryDao().getAll()
        val formJsonList = ArrayList<JSONObject>()
        for (formData in loadFormList) {
            formJsonList.add(jsonStringToJson(formData))
        }
        formRecordList.value = formJsonList
        formFilterRecordList.value = formJsonList
        formFilterRecordList.value = filterRecord()
        updateWaitInputGoods(formJsonList)
        updateWaitOutputGoods(formJsonList)
        return formJsonList
    }

    /**
     * 更新待入庫貨物列表
     */
    fun updateWaitInputGoods(formRecordList: ArrayList<JSONObject>) {
        var waitInputGoodsList = ArrayList<WaitDealGoodsData>()
        for (jsonObject in formRecordList) {
            val reportTitle = jsonObject.optString("reportTitle", "")
            val dealStatus = jsonObject.optString("dealStatus", "")


            var transferStatus = transferStatus(
                reportTitle == transferFormName,
                jsonObject
            )

            if ((reportTitle == deliveryFormName ||
                        reportTitle == returningFormName ||
                        transferStatus == TransferStatus.transferInput) && dealStatus == nowDeal
            ) {
                val itemDetailArray = jsonObject.optJSONArray("itemDetail")
                if (itemDetailArray != null && itemDetailArray.length() > 0) {
                    for (i in 0 until itemDetailArray.length()) {
                        val itemDetailObject = itemDetailArray.getJSONObject(i)
                        val waitDealGoodsData = WaitDealGoodsData(
                            reportTitle = jsonObject.optString("reportTitle", ""),
                            formNumber = jsonObject.optString("formNumber", ""),
                            itemInformation = itemDetailObject
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
                        jsonStringToJson(storageContentEntity.itemInformation).getString("number") == waitDealGoodsData.itemInformation.getString(
                    "number"
                )
            }
        }
        val modWaitInputGoodsList = waitInputGoodsList.map { originalItem ->
            val modifiedItemInformation = JSONObject(originalItem.itemInformation.toString())
            modifiedItemInformation.put("reportTitle", originalItem.reportTitle)
            modifiedItemInformation.put("formNumber", originalItem.formNumber)

            // 创建新的 WaitDealGoodsData 实例
            WaitDealGoodsData(
                reportTitle = originalItem.reportTitle,
                formNumber = originalItem.formNumber,
                itemInformation = modifiedItemInformation
            )
        } as ArrayList<WaitDealGoodsData>

        waitInputGoods.value = modWaitInputGoodsList
    }

    /**
     * 更新儲櫃中的所有貨物
     */
    private fun updateStorageRecords() {
        storageRecords.value =
            SqlDatabase.getInstance().getStorageRecordDao().getAllStorageContent() as ArrayList
    }


    /**
     * 更新儲櫃中的所有貨物
     */
    private fun updateStorageGoods() {
        storageGoods.value =
            SqlDatabase.getInstance().getStorageContentDao().getAllStorageContent() as ArrayList
    }

    /**
     * 更新待出庫貨物列表
     */
    fun updateWaitOutputGoods(formRecordList: ArrayList<JSONObject>) {
        var waitOutputGoodsList = ArrayList<WaitDealGoodsData>()
        for (jsonObject in formRecordList) {
            val reportTitle = jsonObject.optString("reportTitle", "")
            val dealStatus = jsonObject.optString("dealStatus", "")

            var transferStatus = transferStatus(
                reportTitle == transferFormName,
                jsonObject
            )

            if ((reportTitle == pickingFormName ||
                        reportTitle == returningFormName ||
                        transferStatus == TransferStatus.transferOutput) && dealStatus == nowDeal
            ) {
                val itemDetailArray = jsonObject.optJSONArray("itemDetail")
                if (itemDetailArray != null && itemDetailArray.length() > 0) {
                    for (i in 0 until itemDetailArray.length()) {
                        val itemDetailObject = itemDetailArray.getJSONObject(i)
                        val waitDealGoodsData = WaitDealGoodsData(
                            reportTitle = jsonObject.optString("reportTitle", ""),
                            formNumber = jsonObject.optString("formNumber", ""),
                            itemInformation = itemDetailObject
                        )
                        waitOutputGoodsList.add(waitDealGoodsData)
                    }
                }
            }
        }
        waitOutputGoods.value = waitOutputGoodsList
    }

    /**
     * 篩選表單內容
     */
    fun filterRecord(): ArrayList<JSONObject>? {
        return formRecordList.value?.filter { jsonObject ->
            // 根據 "FormClass" 判斷是否在 filterList 中
            val reportTitle = jsonObject.optString("reportTitle")
            val reportTitleFilterCondition = filterList.value?.contains(reportTitle)

            val formNumber = jsonObject.optString("formNumber")

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
//        SqlDatabase.getInstance().getDeliveryDao().clearTable()

        // 將 JSONArray 中的數據逐一插入表中
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)

            val formEntity = FormEntity()
            formEntity.formNumber = jsonObject.optString("formNumber").toString()
            formEntity.formContent = jsonObject.toString()
            SqlDatabase.getInstance().getDeliveryDao().insertNewForm(formEntity)
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
            jsonStringToJson(data.itemInformation).getString("number") == targetNumber

        }
        // 篩選後的List中數量加總
        for (storageContentEntity in filteredList) {
            val itemInformationJson = JSONObject(storageContentEntity.itemInformation)
            if (itemInformationJson.has("quantity")) {
                totalQuantity += itemInformationJson.getInt("quantity")
            }
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
        tempWaitInputGoods: ArrayList<StorageRecordEntity>,
        targetReportTitle: String,
        targetFormNumber: String
    ): ArrayList<StorageRecordEntity> {
        val filteredList = tempWaitInputGoods.filter { data ->
            data.reportTitle == targetReportTitle && data.formNumber == targetFormNumber
        }

        val resultArrayList = ArrayList<StorageRecordEntity>()
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
        tempWaitOutputGoods: ArrayList<StorageRecordEntity>,
        targetReportTitle: String,
        targetFormNumber: String
    ): ArrayList<StorageRecordEntity> {
        val filteredList = tempWaitOutputGoods.filter { data ->
            data.reportTitle == targetReportTitle && data.formNumber == targetFormNumber
        }

        val resultArrayList = ArrayList<StorageRecordEntity>()
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
                    data.formNumber == targetFormNumber &&
                    jsonStringToJson(data.itemInformation).getString("number") == targetNumber

        }
        // 篩選後的List中數量加總
        for (storageContentEntity in filteredList) {
            val itemInformationJson = JSONObject(storageContentEntity.itemInformation)
            if (itemInformationJson.has("quantity")) {
                totalQuantity += itemInformationJson.getInt("quantity")
            }
        }
        return totalQuantity
    }

    fun searchStorageContentByMaterialName(targetMaterialName: String): ArrayList<StorageContentEntity>{
        return storageGoods.value!!.filter { it.materialName.contains(targetMaterialName) } as ArrayList<StorageContentEntity>
    }
}
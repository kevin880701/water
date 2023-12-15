package com.lhr.water.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.lhr.water.R
import com.lhr.water.data.WaitDealGoodsData
import com.lhr.water.room.FormEntity
import com.lhr.water.room.SqlDatabase
import com.lhr.water.room.StorageContentEntity
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
    fun updateWaitInputGoods(formRecordList: ArrayList<JSONObject>){
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
        updateStorageGoods()
        // 這裡需要把已入庫的貨物從waitInputGoods中刪除
        waitInputGoodsList.removeAll { waitDealGoodsData ->
            storageGoods.value!!.any { storageContentEntity ->
                // 透過表單代號(formNumber)和物品編號(number)來做篩選
                storageContentEntity.formNumber == waitDealGoodsData.formNumber &&
                        jsonStringToJson(storageContentEntity.itemInformation).getString("number") == waitDealGoodsData.itemInformation.getString("number")
            }
        }
        val modWaitInputGoodsList = waitInputGoodsList.map { originalItem ->
            val modifiedItemInformation = originalItem.itemInformation
            modifiedItemInformation.put("reportTitle", originalItem.reportTitle)
            modifiedItemInformation.put("formNumber", originalItem.formNumber)

            // 创建新的 WaitDealGoodsData 实例
            WaitDealGoodsData(
                reportTitle = originalItem.reportTitle,
                formNumber = originalItem.formNumber,
                itemInformation = modifiedItemInformation
            )
        } as ArrayList<WaitDealGoodsData>

        Timber.d("" + modWaitInputGoodsList.size + "")
        for (i in modWaitInputGoodsList){
            Timber.d(i.formNumber)
            Timber.d(i.reportTitle)
            Timber.d(i.itemInformation.toString())
        }
        waitInputGoods.value = modWaitInputGoodsList
    }

    /**
     * 更新儲櫃中的所有貨物
     */
    private fun updateStorageGoods(){
        storageGoods.value = SqlDatabase.getInstance().getStorageContentDao().getAllStorageContent() as ArrayList
//        storageGoods.postValue(SqlDatabase.getInstance().getStorageContentDao().getAllStorageContent() as ArrayList)
    }


    /**
     * 更新待出庫貨物列表
     */
    fun updateWaitOutputGoods(formRecordList: ArrayList<JSONObject>){
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
     * 根據 regionName、mapName 和 storageNum 查詢指定儲櫃內容物
     * @param regionName 地區名稱
     * @param mapName 地圖名稱
     * @param storageNum 儲櫃代號
     */
    fun getStorageContentByCondition(regionName: String, mapName: String, storageNum: String): ArrayList<StorageContentEntity> {
        return SqlDatabase.getInstance().getStorageContentDao().getStorageContentByConditions(regionName, mapName, storageNum) as ArrayList
    }

    /**
     * 匯入新json時要清掉舊的SQL內容並插入新的
     * @param jsonArray 要匯入的JSONArray
     */    fun clearAndInsertData(jsonArray: JSONArray) {
        // 清空表
        SqlDatabase.getInstance().getDeliveryDao().clearTable()

        // 將 JSONArray 中的數據逐一插入表中
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)

            val formEntity = FormEntity()
            formEntity.formNumber = jsonObject.optString("formNumber").toString()
            formEntity.formContent = jsonObject.toString()
            SqlDatabase.getInstance().getDeliveryDao().insertOrUpdate(formEntity)
        }
    }
}
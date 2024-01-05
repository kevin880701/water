package com.lhr.water.ui.formContent

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.lhr.water.repository.FormRepository
import com.lhr.water.room.SqlDatabase
import com.lhr.water.room.StorageContentEntity
import com.lhr.water.room.StorageRecordEntity
import com.lhr.water.ui.base.APP
import com.lhr.water.util.manager.jsonStringToJson
import org.json.JSONArray

class FormContentViewModel(context: Context, formRepository: FormRepository): AndroidViewModel(context.applicationContext as APP) {
    var context = context
    val formRepository = formRepository


    fun getStorageGoods(): ArrayList<StorageRecordEntity> {
        return formRepository.storageRecords.value!!
    }

    fun getTempWaitInputGoods(): ArrayList<StorageRecordEntity> {
        return formRepository.tempWaitInputGoods.value!!
    }


    /**
     * 將選擇貨物加入儲櫃中並更新資料庫
     * @param targetReportTitle 表單名稱
     * @param targetFormNumber 表單代號
     */
    fun inputStorageContent(targetReportTitle: String,
                            targetFormNumber: String){
        val tempWaitGoods = formRepository.tempWaitInputGoods.value!!.filter { record ->
            record.reportTitle == targetReportTitle && record.formNumber == targetFormNumber
        }
        var storageContentEntities = ArrayList<StorageContentEntity>()

        tempWaitGoods.forEach { record ->
            // 檢查資料庫中符合條件的資料
            val existingSqlEntity = SqlDatabase.getInstance().getStorageContentDao().getStorageContentByConditions(
                regionName = record.regionName,
                mapName = record.mapName,
                storageName = record.storageName,
                materialName = jsonStringToJson(record.itemInformation).getString("materialName"),
                materialNumber = jsonStringToJson(record.itemInformation).getString("materialNumber"),
                materialSpec = jsonStringToJson(record.itemInformation).getString("materialSpec"),
                materialUnit = jsonStringToJson(record.itemInformation).getString("materialUnit")
            )

            // 尋找符合條件的記錄
            val existingCurrentEntity = storageContentEntities.find {
                it.regionName == record.regionName &&
                it.mapName == record.mapName &&
                it.storageName == record.storageName &&
                it.materialName == jsonStringToJson(record.itemInformation).getString("materialName") &&
                it.materialNumber == jsonStringToJson(record.itemInformation).getString("materialNumber") &&
                it.materialSpec == jsonStringToJson(record.itemInformation).getString("materialSpec") &&
                it.materialUnit == jsonStringToJson(record.itemInformation).getString("materialUnit")
            }

            if (existingCurrentEntity != null) {
                // 如果找到符合條件的記錄，更新 quantity
                existingCurrentEntity.quantity += jsonStringToJson(record.itemInformation).getInt("quantity")
                existingSqlEntity?.let {
                    existingCurrentEntity.quantity += it.quantity
                }
            } else {
                // 如果未找到符合條件的記錄，添加新的記錄
                storageContentEntities.add(StorageContentEntity(
                    regionName = record.regionName,
                    mapName = record.mapName,
                    storageName = record.storageName,
                    materialName = jsonStringToJson(record.itemInformation).getString("materialName"),
                    materialNumber = jsonStringToJson(record.itemInformation).getString("materialNumber"),
                    materialSpec = jsonStringToJson(record.itemInformation).getString("materialSpec"),
                    materialUnit = jsonStringToJson(record.itemInformation).getString("materialUnit"),
                    quantity = if (existingSqlEntity != null) {
                        jsonStringToJson(record.itemInformation).getInt("quantity") + existingSqlEntity.quantity
                    }else{
                        jsonStringToJson(record.itemInformation).getInt("quantity")
                    }
                ))
            }
        }
        SqlDatabase.getInstance().getStorageContentDao().insertOrUpdate(storageContentEntities)
    }


    /**
     * 將選擇貨物從儲櫃中匯出並更新資料庫
     * @param targetReportTitle 表單名稱
     * @param targetFormNumber 表單代號
     */
    fun outputStorageContent(targetReportTitle: String,
                            targetFormNumber: String){
        val tempWaitGoods = formRepository.tempWaitOutputGoods.value!!.filter { record ->
            record.reportTitle == targetReportTitle && record.formNumber == targetFormNumber
        }
        var storageContentEntities = ArrayList<StorageContentEntity>()

        tempWaitGoods.forEach { record ->
            var quantity = 0
            // 檢查資料庫中符合條件的資料
            val existingSqlEntity = SqlDatabase.getInstance().getStorageContentDao().getStorageContentByConditions(
                regionName = record.regionName,
                mapName = record.mapName,
                storageName = record.storageName,
                materialName = jsonStringToJson(record.itemInformation).getString("materialName"),
                materialNumber = jsonStringToJson(record.itemInformation).getString("materialNumber"),
                materialSpec = jsonStringToJson(record.itemInformation).getString("materialSpec"),
                materialUnit = jsonStringToJson(record.itemInformation).getString("materialUnit")
            )

            // 尋找符合條件的記錄
            val existingCurrentEntity = storageContentEntities.find {
                it.regionName == record.regionName &&
                it.mapName == record.mapName &&
                it.storageName == record.storageName &&
                it.materialName == jsonStringToJson(record.itemInformation).getString("materialName") &&
                it.materialNumber == jsonStringToJson(record.itemInformation).getString("materialNumber") &&
                it.materialSpec == jsonStringToJson(record.itemInformation).getString("materialSpec") &&
                it.materialUnit == jsonStringToJson(record.itemInformation).getString("materialUnit")
            }
            quantity = if(existingCurrentEntity == null){
                existingSqlEntity!!.quantity - jsonStringToJson(record.itemInformation).getInt("quantity")
            }else{
                existingCurrentEntity.quantity - jsonStringToJson(record.itemInformation).getInt("quantity")
            }

            storageContentEntities.add(StorageContentEntity(
                regionName = record.regionName,
                mapName = record.mapName,
                storageName = record.storageName,
                materialName = jsonStringToJson(record.itemInformation).getString("materialName"),
                materialNumber = jsonStringToJson(record.itemInformation).getString("materialNumber"),
                materialSpec = jsonStringToJson(record.itemInformation).getString("materialSpec"),
                materialUnit = jsonStringToJson(record.itemInformation).getString("materialUnit"),
                quantity = quantity
            ))
        }
        SqlDatabase.getInstance().getStorageContentDao().insertOrUpdate(storageContentEntities)
    }

    /**
     * 從暫存入庫清單(tempWaitInputGoods)中取出要insert進資料庫裡的貨物清單
     * @param itemDetailArray 要insert的貨物陣列
     * @param formNumber 表單代號
     * @param reportTitle 表單名稱
     */
    fun getInsertGoodsFromTempWaitDealGoods(
        itemDetailArray: JSONArray,
        formNumber: String,
        reportTitle: String
    ): ArrayList<StorageRecordEntity> {
        val matchingEntities = ArrayList<StorageRecordEntity>()

        for (i in 0 until itemDetailArray.length()) {
            val itemDetail = itemDetailArray.getJSONObject(i)
            val targetNumber = itemDetail.getString("number")

            // 使用filter查找並添加符合條件的項到 matchingEntities
            matchingEntities.addAll(formRepository.tempWaitInputGoods.value!!.filter { entity ->
                entity.formNumber == formNumber &&
                        entity.reportTitle == reportTitle &&
                        jsonStringToJson(entity.itemInformation)["number"].toString() == targetNumber
            })
        }
        return matchingEntities
    }
}
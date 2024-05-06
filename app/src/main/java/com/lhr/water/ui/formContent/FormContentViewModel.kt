package com.lhr.water.ui.formContent

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.lhr.water.data.ItemDetail
import com.lhr.water.data.ItemDetail.Companion.toJsonString
import com.lhr.water.data.TempDealGoodsData
import com.lhr.water.repository.FormRepository
import com.lhr.water.room.SqlDatabase
import com.lhr.water.room.CheckoutEntity
import com.lhr.water.room.StorageRecordEntity
import com.lhr.water.ui.base.APP
import com.lhr.water.util.getCurrentDate

class FormContentViewModel(context: Context, formRepository: FormRepository): AndroidViewModel(context.applicationContext as APP) {
    var context = context
    val formRepository = formRepository


    fun getStorageGoods(): ArrayList<StorageRecordEntity> {
        return formRepository.storageRecords.value!!
    }

    fun getTempWaitInputGoods(): ArrayList<TempDealGoodsData> {
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
        var storageContentEntities = ArrayList<CheckoutEntity>()

        tempWaitGoods.forEach { record ->
            // 檢查資料庫中符合條件的資料
            val existingSqlEntity = SqlDatabase.getInstance().getCheckoutDao().getCheckoutByStorageId(
                regionName = record.regionName,
                mapName = record.mapName,
                storageName = record.storageName,
                materialName = record.itemDetail.materialName.toString(),
                materialNumber = record.itemDetail.materialNumber.toString(),
                materialSpec = record.itemDetail.materialSpec.toString(),
                materialUnit = record.itemDetail.materialUnit.toString()
            )

            // 尋找符合條件的記錄
            val existingCurrentEntity = storageContentEntities.find {
                it.storageId == record.regionName &&
                it.mapName == record.mapName &&
                it.storageName == record.storageName &&
                it.materialName == record.itemDetail.materialName.toString() &&
                it.materialNumber == record.itemDetail.materialNumber.toString() &&
                it.materialSpec == record.itemDetail.materialSpec.toString() &&
                it.materialUnit == record.itemDetail.materialUnit.toString()
            }

            if (existingCurrentEntity != null) {
                // 如果找到符合條件的記錄，更新 quantity
                existingCurrentEntity.quantity += record.quantity
                existingSqlEntity?.let {
                    existingCurrentEntity.quantity += it.quantity
                }
            } else {
                // 如果未找到符合條件的記錄，添加新的記錄
                storageContentEntities.add(CheckoutEntity(
                    regionName = record.regionName,
                    mapName = record.mapName,
                    storageName = record.storageName,
                    formNumber = targetFormNumber,
                    date = getCurrentDate(),
                    type = 0, //進貨給狀態0
                    materialName = record.itemDetail.materialName.toString(),
                    materialNumber = record.itemDetail.materialNumber.toString(),
                    materialSpec = record.itemDetail.materialSpec.toString(),
                    materialUnit = record.itemDetail.materialUnit.toString(),
                    quantity = if (existingSqlEntity != null) {
                        record.quantity + existingSqlEntity.quantity
                    }else{
                        record.quantity
                    }
                ))
            }
        }
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
        var storageContentEntities = ArrayList<CheckoutEntity>()

        tempWaitGoods.forEach { record ->
            var quantity = 0
            // 檢查資料庫中符合條件的資料
            val existingSqlEntity = SqlDatabase.getInstance().getCheckoutDao().getCheckoutByStorageId(
                regionName = record.regionName,
                mapName = record.mapName,
                storageName = record.storageName,
                materialName = record.itemDetail.materialName.toString(),
                materialNumber = record.itemDetail.materialNumber.toString(),
                materialSpec = record.itemDetail.materialSpec.toString(),
                materialUnit = record.itemDetail.materialUnit.toString()
            )

            // 尋找符合條件的記錄
            val existingCurrentEntity = storageContentEntities.find {
                it.storageId == record.regionName &&
                it.mapName == record.mapName &&
                it.storageName == record.storageName &&
                it.materialName == record.itemDetail.materialName &&
                it.materialNumber == record.itemDetail.materialNumber.toString() &&
                it.materialSpec == record.itemDetail.materialSpec.toString() &&
                it.materialUnit == record.itemDetail.materialUnit.toString()
            }
            quantity = if(existingCurrentEntity == null){
                existingSqlEntity!!.quantity - record.quantity
            }else{
                existingCurrentEntity.quantity - record.quantity
            }

            storageContentEntities.add(CheckoutEntity(
                regionName = record.regionName,
                mapName = record.mapName,
                storageName = record.storageName,
                formNumber = targetFormNumber,
                date = getCurrentDate(),
                type = 1, //出貨是1
                materialName = record.itemDetail.materialName.toString(),
                materialNumber = record.itemDetail.materialNumber.toString(),
                materialSpec = record.itemDetail.materialSpec.toString(),
                materialUnit = record.itemDetail.materialUnit.toString(),
                quantity = quantity
            ))
        }
    }

    /**
     * 從暫存入庫清單(tempWaitInputGoods)中取出要insert進資料庫裡的貨物清單
     * @param itemDetails 要insert的貨物陣列
     * @param formNumber 表單代號
     * @param reportTitle 表單名稱
     */
    fun getInsertGoodsFromTempWaitDealGoods(
        itemDetails: ArrayList<ItemDetail>,
        formNumber: String,
        reportTitle: String
    ): ArrayList<StorageRecordEntity> {
        val matchingEntities = ArrayList<StorageRecordEntity>()

        for (i in 0 until itemDetails.size) {
            val itemDetail = itemDetails[i]
            val targetNumber = itemDetail.number

            // 使用filter查找並添加符合條件的項到 matchingEntities
            var tempList = formRepository.tempWaitInputGoods.value!!.filter { entity ->
                entity.formNumber == formNumber &&
                        entity.reportTitle == reportTitle &&
                        entity.itemDetail.number == targetNumber
            }
            for(tempDealGoodsData in tempList){
                var storageContentEntity = StorageRecordEntity()
                storageContentEntity.regionName = tempDealGoodsData.regionName
                storageContentEntity.mapName = tempDealGoodsData.mapName
                storageContentEntity.storageName = tempDealGoodsData.storageName
                storageContentEntity.formNumber = tempDealGoodsData.formNumber
                storageContentEntity.reportTitle = tempDealGoodsData.reportTitle
                storageContentEntity.date = getCurrentDate()
                storageContentEntity.quantity = tempDealGoodsData.quantity
                storageContentEntity.itemDetail = tempDealGoodsData.itemDetail.toJsonString()

                matchingEntities.add(storageContentEntity)
            }
        }
        return matchingEntities
    }
}
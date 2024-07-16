package com.lhr.water.ui.formContent

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.lhr.water.data.form.BaseItem
import com.lhr.water.repository.FormRepository
import com.lhr.water.room.StorageRecordEntity
import com.lhr.water.ui.base.APP

class FormContentViewModel(context: Context, formRepository: FormRepository) :
    AndroidViewModel(context.applicationContext as APP) {
    var context = context
    val formRepository = formRepository

    /**
     * 從暫存入庫清單(tempWaitInputGoods)中取出要insert進資料庫裡的貨物清單，return的List是要加入SQL的，同時把加入SQL的資料從暫存tempWaitInputGoods刪除
     * @param itemDetailList 要insert的貨物陣列
     * @param formNumber 表單代號
     */
    fun getInsertStorageRecord(
        itemDetailList: List<BaseItem>,
        formNumber: String,
    ): ArrayList<StorageRecordEntity> {
        val matchedItems = ArrayList<StorageRecordEntity>()
        val tempList = (formRepository.tempStorageRecordEntities.value ?: emptyList()).toMutableList()

        // 遍歷itemDetailList
        for (itemDetail in itemDetailList) {
            // 在tempList中查找所有匹配的元素
            val matchedItemsInTempList = tempList.filter { it.formNumber == formNumber && it.materialNumber == itemDetail.materialNumber }

            // 將匹配的元素添加到matchedItems列表中
            matchedItems.addAll(matchedItemsInTempList)

            // 從tempList中移除所有匹配的元素
            tempList.removeAll(matchedItemsInTempList)
        }
        formRepository.tempStorageRecordEntities.postValue(ArrayList(tempList))
        return matchedItems
    }
}
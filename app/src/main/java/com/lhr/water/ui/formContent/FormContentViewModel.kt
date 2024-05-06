package com.lhr.water.ui.formContent

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.lhr.water.data.ItemDetail
import com.lhr.water.repository.FormRepository
import com.lhr.water.room.SqlDatabase
import com.lhr.water.room.CheckoutEntity
import com.lhr.water.room.StorageRecordEntity
import com.lhr.water.ui.base.APP
import com.lhr.water.util.formTypeMap
import com.lhr.water.util.getCurrentDate

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
        itemDetailList: ArrayList<ItemDetail>,
        formNumber: String,
    ): ArrayList<StorageRecordEntity> {
        val matchedItems = ArrayList<StorageRecordEntity>()
        var tempList = formRepository.tempWaitInputGoods.value!!
        // 遍历itemDetailList
        for (itemDetail in itemDetailList) {
            // 在tempWaitOutputGoods中查找匹配的元素
            val matchedItem =
                tempList.find { it.formNumber == formNumber && it.materialNumber == itemDetail.materialNumber }
            // 如果找到匹配的元素，则将其移动到matchedItems列表中
            matchedItem?.let {
                matchedItems.add(it)
                tempList.remove(it)
            }
        }
        formRepository.tempWaitInputGoods.postValue(tempList)
        return matchedItems
    }
}
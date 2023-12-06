package com.lhr.water.ui.storageGoodInput

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.lhr.water.data.WaitDealGoodsData
import com.lhr.water.repository.FormRepository
import com.lhr.water.room.SqlDatabase
import com.lhr.water.room.StorageContentEntity
import com.lhr.water.ui.base.APP
import com.lhr.water.util.adapter.StorageInputAdapter
import com.lhr.water.util.getCurrentDate
import java.text.SimpleDateFormat
import java.util.Date

class StorageGoodInputViewModel(context: Context, formRepository: FormRepository): AndroidViewModel(context.applicationContext as APP) {
    var formRepository = formRepository

    companion object {
    }

    fun getWaitInputGoods(): ArrayList<WaitDealGoodsData> {
        return formRepository.waitInputGoods.value!!
    }


    /**
     * 將選擇貨物加入儲櫃中並更新資料庫和表單列表
     */
    fun inputGoods(storageInputAdapter: StorageInputAdapter, region: String, map: String, storageNum: String) {
        // 遍歷數據集，檢查每個位置的 CheckBox 是否被選中
        var storageContentEntities = ArrayList<StorageContentEntity>()
        for (i in getWaitInputGoods().indices) {
            val isChecked = storageInputAdapter.isSelected(i)

            // CheckBox 在位置 i 處於選中狀態
            if (isChecked) {
                // 需要為貨物加上地區、地圖、儲櫃代號、報表名稱、報表代號、入庫時間欄位
                var waitInputGoodsJson = getWaitInputGoods()[i].itemInformation
                waitInputGoodsJson.put("regionName", region)
                waitInputGoodsJson.put("mapName", map)
                waitInputGoodsJson.put("storageNum", storageNum)
                waitInputGoodsJson.put("reportId", getWaitInputGoods()[i].reportId)
                waitInputGoodsJson.put("reportTitle", getWaitInputGoods()[i].reportTitle)
                // 入庫時間記錄到民國年月日就好
                waitInputGoodsJson.put("inputDate", getCurrentDate())

                var storageContentEntity = StorageContentEntity()
                storageContentEntity.regionName = region
                storageContentEntity.mapName = map
                storageContentEntity.storageNum = storageNum
                storageContentEntity.reportId = getWaitInputGoods()[i].reportId
                storageContentEntity.reportTitle = getWaitInputGoods()[i].reportTitle
                storageContentEntity.itemInformation = waitInputGoodsJson.toString()
                storageContentEntities.add(storageContentEntity)
            }
        }
        if(storageContentEntities.size > 0){
            SqlDatabase.getInstance().getStorageContentDao().insertStorageItem(storageContentEntities)
        }
        formRepository.updateWaitDealGoods(formRepository.formRecordList.value!!)
    }
}
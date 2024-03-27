package com.lhr.water.ui.form

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lhr.water.R
import com.lhr.water.data.Form
import com.lhr.water.data.TempDealGoodsData
import com.lhr.water.data.WaitDealGoodsData
import com.lhr.water.repository.FormRepository
import com.lhr.water.repository.RegionRepository
import com.lhr.water.room.MapEntity
import com.lhr.water.room.RegionEntity
import com.lhr.water.room.StorageContentEntity
import com.lhr.water.room.StorageEntity
import com.lhr.water.ui.base.APP
import com.lhr.water.util.getCurrentDate

class FormViewModel(
    context: Context,
    regionRepository: RegionRepository,
    formRepository: FormRepository
) : AndroidViewModel(context.applicationContext as APP) {

    val regionRepository = regionRepository
    val formRepository = formRepository
    // 篩選表單代號formNumber的String
    var searchFormNumber = MutableLiveData<String>()
    // 篩選表單類別FormClass的List
    var filterList = MutableLiveData<ArrayList<String>>()


    init {
        filterList.value = ArrayList(context.resources.getStringArray(R.array.form_array).toList())
        searchFormNumber.value = ""
    }

    /**
     * 篩選表單內容
     */
    fun filterRecord(formList: ArrayList<Form>,searchFormNumber: String,filterList: ArrayList<String>): ArrayList<Form>? {
        return formList.filter { form ->
            // 根據 "FormClass" 判斷是否在 filterList 中
            val reportTitle = form.reportTitle.toString()
            val reportTitleFilterCondition = filterList.contains(reportTitle)
            val formNumber = form.formNumber.toString()

            // 如果搜尋框(EditText)中的文本不為空，則判斷 "formNumber" 是否包含該文本
            val editTextFilterCondition = if (searchFormNumber.isNotEmpty()) {
                formNumber.contains(searchFormNumber, ignoreCase = true)
            } else {
                true // 搜尋框(EditText)，不添加 formNumber 的篩選條件
            }
            reportTitleFilterCondition!! && editTextFilterCondition
        }?.toMutableList()!! as ArrayList<Form>?
    }

    fun getRegionNameList(regionEntities: ArrayList<RegionEntity>): ArrayList<String> {
        return regionRepository.getRegionNameList(regionEntities)
    }

    fun getMapNameList(regionName: String, mapEntities: ArrayList<MapEntity>): ArrayList<String> {
        return regionRepository.getMapNameList(regionName, mapEntities)
    }

    fun getStorageNameList(
        regionName: String,
        mapName: String,
        storageEntities: ArrayList<StorageEntity>
    ): ArrayList<StorageEntity> {
        return regionRepository.getStorageDetailList(regionName, mapName, storageEntities)
    }


    /**
     * 將選擇貨物加入儲櫃中並更新暫存待入庫的貨物列表
     * @param waitDealGoodsData 貨物資訊
     * @param region 地區名稱
     * @param map 地區名稱
     * @param storageName 櫥櫃名稱
     * @param materialQuantity 選擇貨物數量
     */
    fun inputInTempGoods(
        waitDealGoodsData: WaitDealGoodsData,
        region: String,
        map: String,
        storageName: String,
        materialQuantity: String
    ) {

        // 需要為貨物加上地區、地圖、儲櫃名稱、報表名稱、報表代號、入庫時間欄位
        var tempDealGoodsData = TempDealGoodsData(
            reportTitle = waitDealGoodsData.reportTitle,
            formNumber =  waitDealGoodsData.formNumber,
            regionName = region,
            mapName =  map,
            storageName = storageName,
            date = getCurrentDate(),
            itemDetail = waitDealGoodsData.itemDetail,
            quantity = materialQuantity.toInt(),
        )

        // 更新暫存進貨列表
        val currentList = formRepository.tempWaitInputGoods.value ?: ArrayList()
        currentList.add(tempDealGoodsData)
        formRepository.tempWaitInputGoods.postValue(currentList)
    }


    /**
     * 將選擇貨物加入儲櫃中並更新暫存待入庫的貨物列表
     * @param waitDealGoodsData 貨物資訊
     * @param region 地區名稱
     * @param map 地區名稱
     * @param storageName 儲櫃名稱
     * @param materialQuantity 貨物數量
     */
    fun outputInTempGoods(
        waitDealGoodsData: WaitDealGoodsData,
        region: String,
        map: String,
        storageName: String,
        materialQuantity: String
    ) {
        // 需要為貨物加上地區、地圖、儲櫃名稱、報表名稱、報表代號、入庫時間欄位
        var tempDealGoodsData = TempDealGoodsData(
            reportTitle = waitDealGoodsData.reportTitle,
            formNumber =  waitDealGoodsData.formNumber,
            regionName = region,
            mapName =  map,
            storageName = storageName,
            date = getCurrentDate(),
            itemDetail = waitDealGoodsData.itemDetail,
            quantity = materialQuantity.toInt(),
        )

        // 更新暫存進貨列表
        val currentList = formRepository.tempWaitOutputGoods.value ?: ArrayList()
        currentList.add(tempDealGoodsData)
        formRepository.tempWaitOutputGoods.postValue(currentList)
    }

    fun getOutputGoodsStorageInformation(
        materialName: String,
        materialNumber: String
    ): ArrayList<StorageContentEntity> {
        var storageContentList = formRepository.storageGoods.value?.filter { entity ->
            materialName == entity.materialName && materialNumber == entity.materialNumber
        }
        return storageContentList as ArrayList
    }

    fun getOutputGoodsRegion(storageContentList: ArrayList<StorageContentEntity>): ArrayList<RegionEntity> {
        return storageContentList?.distinctBy { it.regionName }
            ?.map { RegionEntity(it.regionName) } as ArrayList<RegionEntity>
    }

    fun getOutputGoodsMap(storageContentList: ArrayList<StorageContentEntity>): ArrayList<MapEntity> {
        // 取出不重複的 regionName 和 mapName 並轉為 MapEntity
        return storageContentList
            .distinctBy { Pair(it.regionName, it.mapName) }
            .map { MapEntity(it.regionName, it.mapName) } as ArrayList<MapEntity>
    }

    fun getOutputGoodsStorage(storageContentList: ArrayList<StorageContentEntity>): ArrayList<StorageEntity> {
        // 取出不重複的 regionName、mapName 和 storageName 並轉為 StorageEntity
        return storageContentList
            .distinctBy { Triple(it.regionName, it.mapName, it.storageName) }
            .map {
                StorageEntity(
                    it.regionName,
                    it.mapName,
                    it.storageName,
                    "",
                    ""
                )
            } as ArrayList<StorageEntity>
    }

    fun getInputGoodsRegion(storageContentList: ArrayList<StorageEntity>): ArrayList<RegionEntity> {
        return storageContentList?.distinctBy { it.regionName }
            ?.map { RegionEntity(it.regionName) } as ArrayList<RegionEntity>
    }

    fun getInputGoodsMap(storageContentList: ArrayList<StorageEntity>): ArrayList<MapEntity> {
        // 取出不重複的 regionName 和 mapName 並轉為 MapEntity
        return storageContentList
            .distinctBy { Pair(it.regionName, it.mapName) }
            .map { MapEntity(it.regionName, it.mapName) } as ArrayList<MapEntity>
    }

    fun getInputGoodsStorage(storageContentList: ArrayList<StorageEntity>): ArrayList<StorageEntity> {
        // 取出不重複的 regionName、mapName 和 storageName 並轉為 StorageEntity
        return storageContentList
            .distinctBy { Triple(it.regionName, it.mapName, it.storageName) }
            .map {
                StorageEntity(
                    it.regionName,
                    it.mapName,
                    it.storageName,
                    "",
                    ""
                )
            } as ArrayList<StorageEntity>
    }

    fun filterWaitInputGoods(
        targetReportTitle: String,
        targetFormNumber: String
    ) = formRepository.filterWaitInputGoods(
        formRepository.waitInputGoods.value!!,
        targetReportTitle,
        targetFormNumber
    )

    fun filterTempWaitInputGoods(
        targetReportTitle: String,
        targetFormNumber: String
    ) = formRepository.filterTempWaitInputGoods(
        formRepository.tempWaitInputGoods.value!!,
        targetReportTitle,
        targetFormNumber
    )


    fun filterWaitOutputGoods(
        targetReportTitle: String,
        targetFormNumber: String
    ) = formRepository.filterWaitOutputGoods(
        formRepository.waitOutputGoods.value!!,
        targetReportTitle,
        targetFormNumber
    )

    fun filterTempWaitOutputGoods(
        targetReportTitle: String,
        targetFormNumber: String
    ) = formRepository.filterTempWaitOutputGoods(
        formRepository.tempWaitOutputGoods.value!!,
        targetReportTitle,
        targetFormNumber
    )
}
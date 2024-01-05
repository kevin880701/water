package com.lhr.water.ui.history

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.lhr.water.data.WaitDealGoodsData
import com.lhr.water.repository.FormRepository
import com.lhr.water.repository.RegionRepository
import com.lhr.water.room.MapEntity
import com.lhr.water.room.RegionEntity
import com.lhr.water.room.StorageContentEntity
import com.lhr.water.room.StorageEntity
import com.lhr.water.room.StorageRecordEntity
import com.lhr.water.ui.base.APP
import com.lhr.water.util.getCurrentDate
import org.json.JSONObject

class HistoryViewModel(
    context: Context,
    regionRepository: RegionRepository,
    formRepository: FormRepository
) : AndroidViewModel(context.applicationContext as APP) {

    val regionRepository = regionRepository
    val formRepository = formRepository

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
     * @param materialQuantity 貨物數量
     */
    fun inputInTempGoods(
        waitDealGoodsData: WaitDealGoodsData,
        region: String,
        map: String,
        storageName: String,
        materialQuantity: String
    ) {

        // 需要為貨物加上地區、地圖、儲櫃代號、報表名稱、報表代號、入庫時間欄位
        var waitInputGoodsJson = waitDealGoodsData.itemInformation

        waitInputGoodsJson.put("regionName", region)
        waitInputGoodsJson.put("mapName", map)
        waitInputGoodsJson.put("storageName", storageName)
        waitInputGoodsJson.put("formNumber", waitDealGoodsData.formNumber)
        waitInputGoodsJson.put("reportTitle", waitDealGoodsData.reportTitle)
        // 入庫時間記錄到民國年月日就好
        waitInputGoodsJson.put("inputDate", getCurrentDate())
        waitInputGoodsJson.put("quantity", materialQuantity)

        var storageContentEntity = StorageRecordEntity()
        storageContentEntity.regionName = region
        storageContentEntity.mapName = map
        storageContentEntity.storageName = storageName
        storageContentEntity.formNumber = waitDealGoodsData.formNumber
        storageContentEntity.reportTitle = waitDealGoodsData.reportTitle
        storageContentEntity.itemInformation = waitInputGoodsJson.toString()

        // 更新暫存進貨列表
        val currentList = formRepository.tempWaitInputGoods.value ?: ArrayList()
        currentList.add(storageContentEntity)
        formRepository.tempWaitInputGoods.postValue(currentList)
    }


    /**
     * 將選擇貨物加入儲櫃中並更新暫存待入庫的貨物列表
     * @param waitDealGoodsData 貨物資訊
     * @param region 地區名稱
     * @param map 地區名稱
     * @param storageName 櫥櫃代號
     * @param materialQuantity 貨物數量
     */
    fun outputInTempGoods(
        waitDealGoodsData: WaitDealGoodsData,
        region: String,
        map: String,
        storageName: String,
        materialQuantity: String
    ) {
        // 需要為貨物加上地區、地圖、儲櫃代號、報表名稱、報表代號、入庫時間欄位
        var waitOutputGoodsJson = waitDealGoodsData.itemInformation

        waitOutputGoodsJson.put("regionName", region)
        waitOutputGoodsJson.put("mapName", map)
        waitOutputGoodsJson.put("storageName", storageName)
        waitOutputGoodsJson.put("formNumber", waitDealGoodsData.formNumber)
        waitOutputGoodsJson.put("reportTitle", waitDealGoodsData.reportTitle)
        // 入庫時間記錄到民國年月日就好
        waitOutputGoodsJson.put("outputDate", getCurrentDate())
        waitOutputGoodsJson.put("quantity", materialQuantity)

        var storageContentEntity = StorageRecordEntity()
        storageContentEntity.regionName = region
        storageContentEntity.mapName = map
        storageContentEntity.storageName = storageName
        storageContentEntity.formNumber = waitDealGoodsData.formNumber
        storageContentEntity.reportTitle = waitDealGoodsData.reportTitle
        storageContentEntity.itemInformation = waitOutputGoodsJson.toString()

        // 更新暫存進貨列表
        val currentList = formRepository.tempWaitOutputGoods.value ?: ArrayList()
        currentList.add(storageContentEntity)
        formRepository.tempWaitOutputGoods.postValue(currentList)
    }

    fun getOutputGoodsStorageInformation(
        materialName: String,
        materialNumber: String
    ): ArrayList<StorageRecordEntity> {
        var storageContentList = formRepository.storageRecords.value?.filter { entity ->
            entity.itemInformation?.let { itemInfo ->
                // 將itemInformation轉換為JsonObject
                val json = JSONObject(itemInfo)
                // 判斷是否與目標值匹配
                materialName == json.optString("materialName") && materialNumber == json.optString("materialNumber")
            } ?: false
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
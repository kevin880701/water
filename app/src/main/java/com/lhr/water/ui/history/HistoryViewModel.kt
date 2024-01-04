package com.lhr.water.ui.history

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.lhr.water.data.MapDetail
import com.lhr.water.data.RegionInformation
import com.lhr.water.data.StorageDetail
import com.lhr.water.data.WaitDealGoodsData
import com.lhr.water.repository.FormRepository
import com.lhr.water.repository.RegionRepository
import com.lhr.water.room.StorageRecordEntity
import com.lhr.water.ui.base.APP
import com.lhr.water.util.getCurrentDate
import org.json.JSONObject
import timber.log.Timber

class HistoryViewModel(
    context: Context,
    regionRepository: RegionRepository,
    formRepository: FormRepository
) : AndroidViewModel(context.applicationContext as APP) {

    val regionRepository = regionRepository
    val formRepository = formRepository

    companion object {
    }

    init {
    }

    fun getRegionNameList(storageInformationList: ArrayList<RegionInformation>): ArrayList<String> {
        return regionRepository.getRegionNameList()
    }

    fun getMapNameList(regionName: String, storageInformationList: ArrayList<RegionInformation>): ArrayList<String> {
        return regionRepository.getMapNameList(regionName)
    }

    fun getStorageNameList(regionName: String, mapName: String, storageInformationList: ArrayList<RegionInformation>): ArrayList<StorageDetail> {
        return regionRepository.getStorageDetailList(regionName, mapName, storageInformationList)
    }


    /**
     * 將選擇貨物加入儲櫃中並更新暫存待入庫的貨物列表
     * @param waitDealGoodsData 貨物資訊
     * @param region 地區名稱
     * @param map 地區名稱
     * @param storageNum 櫥櫃代號
     * @param materialQuantity 貨物數量
     */
    fun inputInTempGoods(
        waitDealGoodsData: WaitDealGoodsData,
        region: String,
        map: String,
        storageNum: String,
        materialQuantity: String
    ) {

        // 需要為貨物加上地區、地圖、儲櫃代號、報表名稱、報表代號、入庫時間欄位
        var waitInputGoodsJson = waitDealGoodsData.itemInformation

        waitInputGoodsJson.put("regionName", region)
        waitInputGoodsJson.put("mapName", map)
        waitInputGoodsJson.put("storageNum", storageNum)
        waitInputGoodsJson.put("formNumber", waitDealGoodsData.formNumber)
        waitInputGoodsJson.put("reportTitle", waitDealGoodsData.reportTitle)
        // 入庫時間記錄到民國年月日就好
        waitInputGoodsJson.put("inputDate", getCurrentDate())
        waitInputGoodsJson.put("quantity", materialQuantity)

        var storageContentEntity = StorageRecordEntity()
        storageContentEntity.regionName = region
        storageContentEntity.mapName = map
        storageContentEntity.storageNum = storageNum
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
     * @param storageNum 櫥櫃代號
     * @param materialQuantity 貨物數量
     */
    fun outputInTempGoods(
        waitDealGoodsData: WaitDealGoodsData,
        region: String,
        map: String,
        storageNum: String,
        materialQuantity: String
    ) {
        // 需要為貨物加上地區、地圖、儲櫃代號、報表名稱、報表代號、入庫時間欄位
        var waitOutputGoodsJson = waitDealGoodsData.itemInformation

        waitOutputGoodsJson.put("regionName", region)
        waitOutputGoodsJson.put("mapName", map)
        waitOutputGoodsJson.put("storageNum", storageNum)
        waitOutputGoodsJson.put("formNumber", waitDealGoodsData.formNumber)
        waitOutputGoodsJson.put("reportTitle", waitDealGoodsData.reportTitle)
        // 入庫時間記錄到民國年月日就好
        waitOutputGoodsJson.put("outputDate", getCurrentDate())
        waitOutputGoodsJson.put("quantity", materialQuantity)

        var storageContentEntity = StorageRecordEntity()
        storageContentEntity.regionName = region
        storageContentEntity.mapName = map
        storageContentEntity.storageNum = storageNum
        storageContentEntity.formNumber = waitDealGoodsData.formNumber
        storageContentEntity.reportTitle = waitDealGoodsData.reportTitle
        storageContentEntity.itemInformation = waitOutputGoodsJson.toString()

        // 更新暫存進貨列表
        val currentList = formRepository.tempWaitOutputGoods.value ?: ArrayList()
        currentList.add(storageContentEntity)
        formRepository.tempWaitOutputGoods.postValue(currentList)
    }

    fun getOutputGoodsStorageInformation(materialName: String, materialNumber: String): ArrayList<StorageRecordEntity>{
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

    fun getOutputGoodsWhere(jsonObject: JSONObject): ArrayList<RegionInformation>{
        var storageContentList = formRepository.storageGoods.value?.filter { entity ->
            entity.materialName == jsonObject.getString("materialName") &&
                    entity.materialNumber == jsonObject.getString("materialNumber") &&
                    entity.materialSpec == jsonObject.getString("materialSpec") &&
                    entity.materialUnit == jsonObject.getString("materialUnit")
        }

        // 使用 groupBy 將資料分組
        val groupedByRegion = storageContentList?.groupBy { it.regionName }
        // 將分組後的資料轉換為 List<RegionInformation>
        val regionInformationList = groupedByRegion?.entries?.map { entry ->
            val regionName = entry.key
            val mapDetails = entry.value.groupBy { it.mapName }.entries.map { mapEntry ->
                val mapName = mapEntry.key
                val storageDetails = mapEntry.value.map { storageContentEntity ->
                    StorageDetail(
                        storageContentEntity.storageNum,
                        regionRepository.findStorageName(regionName, mapName, storageContentEntity.storageNum), // 使用 StorageContentEntity 中的相關屬性
                        "storageContentEntity.storageX",
                        "storageContentEntity.storageY"
                    )
                }
                MapDetail(mapName, storageDetails)
            }
            RegionInformation(regionName, mapDetails)
        }

        return regionInformationList!! as ArrayList
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
        targetFormNumber: String) = formRepository.filterTempWaitInputGoods(
        formRepository.tempWaitInputGoods.value!!,
        targetReportTitle,
        targetFormNumber)


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
        targetFormNumber: String) = formRepository.filterTempWaitOutputGoods(
        formRepository.tempWaitOutputGoods.value!!,
        targetReportTitle,
        targetFormNumber)
}
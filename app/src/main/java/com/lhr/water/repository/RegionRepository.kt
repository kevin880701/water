package com.lhr.water.repository

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.lhr.water.data.MapDetail
import com.lhr.water.data.StorageDetail
import com.lhr.water.data.RegionInformation
import com.lhr.water.room.SqlDatabase
import com.lhr.water.room.StorageContentEntity
import com.lhr.water.room.TargetEntity
import com.lhr.water.util.manager.jsonStringToJson
import java.io.InputStreamReader

class RegionRepository private constructor(private val context: Context) {
    var storageInformationList: ArrayList<RegionInformation>
    companion object {
        private var instance: RegionRepository? = null
        fun getInstance(context: Context): RegionRepository {
            if (instance == null) {
                instance = RegionRepository(context)
            }
            return instance!!
        }
    }

    init {
        storageInformationList = getStorageInformationFromSQL()
    }



    /**
     * 從資料庫取得儲櫃資料
     */
    private fun getStorageInformationFromSQL(): ArrayList<RegionInformation> {
        var targetEntities = SqlDatabase.getInstance().getTargetDao().getAll()
        val groupedByRegionMap = targetEntities.groupBy { it.regionName }

        val regionInformationList = ArrayList<RegionInformation>()

        for ((regionName, entitiesInRegion) in groupedByRegionMap) {
            val mapDetails = entitiesInRegion.groupBy { it.mapName }
                .map { (mapName, entitiesInMap) ->
                    MapDetail(
                        MapName = mapName,
                        StorageDetail = entitiesInMap.map {
                            StorageDetail(
                                StorageNum = it.storageNum,
                                StorageName = it.storageName,
                                StorageX = it.storageX,
                                StorageY = it.storageY
                            )
                        }
                    )
                }

            regionInformationList.add(
                RegionInformation(
                    RegionName = regionName,
                    MapDetail = mapDetails
                )
            )
        }
        return regionInformationList
    }

    /**
     * 判斷Region資料表是否為空，如果為空代表第一次開。需從StorageInformation.json插入資料到資料庫
     */
    fun checkRegionExist() {
        var count = SqlDatabase.getInstance().getTargetDao().getRowCount()
        if(count == 0){
            val regionEntities = mutableListOf<TargetEntity>()
            var regionInformationList = getStorageInformationFromAssets()

            for (regionInformation in regionInformationList) {
                val regionName = regionInformation.RegionName

                for (mapDetail in regionInformation.MapDetail) {
                    val mapName = mapDetail.MapName

                    for (storageDetail in mapDetail.StorageDetail) {
                        val storageNum = storageDetail.StorageNum
                        val storageName = storageDetail.StorageName
                        val storageX = storageDetail.StorageX
                        val storageY = storageDetail.StorageY

                        val regionEntity = TargetEntity().apply {
                            this.regionName = regionName
                            this.mapName = mapName
                            this.storageNum = storageNum
                            this.storageName = storageName
                            this.storageX = storageX
                            this.storageY = storageY
                        }
                        regionEntities.add(regionEntity)
                    }
                }
            }
            SqlDatabase.getInstance().getTargetDao().insertTargetEntities(regionEntities)
        }
        storageInformationList = getStorageInformationFromSQL()
    }

    /**
     * 從 assets 中獲取"StorageInformation.json"
     */
    private fun getStorageInformationFromAssets(): ArrayList<RegionInformation> {
        return try {
//             從 assets 中獲取 InputStream
            val inputStream = context.assets.open("StorageInformation.json")

//             使用 Gson 解析 JSON 数据
            val type = object : TypeToken<List<RegionInformation>>() {}.type
            var storageInformation: List<RegionInformation> =
                Gson().fromJson(InputStreamReader(inputStream), type)

            // 關閉 InputStream
            inputStream.close()

            storageInformation as ArrayList<RegionInformation>
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            Log.e("JsonSyntaxException", "Error parsing JSON: ${e.message}")
            ArrayList()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("JsonSyntaxException", "Error parsing JSON: ${e.message}")
            ArrayList()
        }
    }

    /**
     * 區域列表
     */
    fun getRegionNameList(storageInformationList: ArrayList<RegionInformation>): ArrayList<String> {
        return storageInformationList.map { it.RegionName } as ArrayList<String>
    }


    /**
     * 根據區域名稱列出地圖列表
     * @param regionName 區域名稱
     */
    fun getMapNameList(regionName: String, storageInformationList: ArrayList<RegionInformation>): ArrayList<String> {
        // 查找 StorageInformation 中指定的 regionName
        val regionStorageInformation = storageInformationList.find { it.RegionName == regionName }
        // 提取該 regionName 下的 mapName 列表
        return regionStorageInformation?.MapDetail?.map { it.MapName } as ArrayList<String>
    }


    /**
     * 根據區域名稱和地圖名稱列出儲櫃列表
     * @param regionName 區域名稱
     * @param mapName 地圖名稱
     */
    fun getStorageDetailList(regionName: String, mapName: String, storageInformationList: ArrayList<RegionInformation>): ArrayList<StorageDetail> {
        // 查找 StorageInformation 中指定 regionName
        val regionStorageInformation =
            storageInformationList.find { it.RegionName == regionName }

        // 查找指定 mapName 的 StorageDetail
        val mapStorageDetail =
            regionStorageInformation?.MapDetail?.find { it.MapName == mapName }

        // 提取該 mapName 下的 ItemDetail 列表
        return mapStorageDetail?.StorageDetail as ArrayList<StorageDetail>
    }


    /**
     * 根據區域名稱、地圖名稱、儲櫃名稱找出對應的儲櫃代號StorageNum
     * @param regionName 區域名稱
     * @param mapName 地圖名稱
     * @param storageName 儲櫃名稱
     */
    fun findStorageNum(
        regionName: String,
        mapName: String,
        storageName: String
    ): String {
        val region = storageInformationList.find { it.RegionName == regionName }

        return region?.MapDetail
            ?.find { it.MapName == mapName }
            ?.StorageDetail
            ?.find { it.StorageName == storageName }
            ?.StorageNum as String
    }



    /**
     * 根據區域名稱、地圖名稱、儲櫃代號找出對應的儲櫃名稱StorageName
     * @param regionName 區域名稱
     * @param mapName 地圖名稱
     * @param storageNum 儲櫃代號
     */
    fun findStorageName(
        regionName: String,
        mapName: String,
        storageNum: String
    ): String {
        val region = storageInformationList.find { it.RegionName == regionName }

        return region?.MapDetail
            ?.find { it.MapName == mapName }
            ?.StorageDetail
            ?.find { it.StorageNum == storageNum }
            ?.StorageName as String
    }


    /**
     * 根據區域名稱、地圖名稱、儲櫃代號、貨物代號找出對應的數量
     * @param regionName 區域名稱
     * @param mapName 地圖名稱
     * @param storageNum 儲櫃代號
     * @param materialNum 貨物代號
     */
    fun getMaterialQuantity(
        regionName: String,
        mapName: String,
        storageNum: String,
        materialNum: String,
        storageInformationList: ArrayList<StorageContentEntity>
    ): String {
        val matchingItem = storageInformationList.find { item ->
            item.regionName == regionName &&
                    item.mapName == mapName &&
                    item.storageNum == storageNum &&
                    jsonStringToJson(item.itemInformation)["materialNum"] == materialNum
        }

        return matchingItem!!.let {
            jsonStringToJson(it.itemInformation)["receivedQuantity"].toString()
        }
    }
}
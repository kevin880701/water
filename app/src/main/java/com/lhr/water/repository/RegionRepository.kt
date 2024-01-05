package com.lhr.water.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.lhr.water.data.RegionInformation
import com.lhr.water.room.MapEntity
import com.lhr.water.room.RegionEntity
import com.lhr.water.room.SqlDatabase
import com.lhr.water.room.StorageRecordEntity
import com.lhr.water.room.StorageEntity
import com.lhr.water.util.manager.jsonStringToJson
import com.lhr.water.util.manager.toMapEntities
import com.lhr.water.util.manager.toRegionEntity
import org.json.JSONArray
import java.io.InputStreamReader
import java.nio.charset.Charset

class RegionRepository private constructor(private val context: Context) {
    // 所有區域列表
    var regionEntities: MutableLiveData<ArrayList<RegionEntity>> =
        MutableLiveData<ArrayList<RegionEntity>>()
    // 所有地圖列表
    var mapEntities: MutableLiveData<ArrayList<MapEntity>> =
        MutableLiveData<ArrayList<MapEntity>>()
    // 所有儲櫃單列表
    var storageEntities: MutableLiveData<ArrayList<StorageEntity>> =
        MutableLiveData<ArrayList<StorageEntity>>()

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
        storageEntities.value = SqlDatabase.getInstance().getStorageDao().getAllStorage() as ArrayList
        regionEntities.value = SqlDatabase.getInstance().getRegionDao().getAllRegion() as ArrayList
        mapEntities.value = SqlDatabase.getInstance().getMapDao().getAllMap() as ArrayList
    }

    /**
     * 判斷Region資料表是否為空，如果為空代表第一次開。需從MapInformation.json插入資料到資料庫
     */
    fun loadStorageInformation() {
        var count = SqlDatabase.getInstance().getRegionDao().getRowCount()
        if (count == 0) {
            getMapInformationFromAssets()
        }
        storageEntities.value = SqlDatabase.getInstance().getStorageDao().getAllStorage() as ArrayList
        regionEntities.value = SqlDatabase.getInstance().getRegionDao().getAllRegion() as ArrayList
        mapEntities.value = SqlDatabase.getInstance().getMapDao().getAllMap() as ArrayList
    }

    /**
     * 從 assets 中獲取"StorageInformation.json"
     */
    private fun getStorageInformationFromAssets(): ArrayList<RegionInformation> {
        return try {
            // 從 assets 中獲取 InputStream
            val inputStream = context.assets.open("StorageInformation.json")

            // 使用 Gson 解析 JSON 数据
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
     * 從 assets 中獲取"MapInformation.json"
     */
    fun getMapInformationFromAssets() {
        try {
            // 從 assets 中獲取 InputStream
            val inputStream = context.assets.open("MapInformation.json")
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)

            // 關閉 InputStream
            inputStream.close()
            var mapInformation = String(buffer, Charset.forName("UTF-8"))
            var jsonArray = JSONArray(mapInformation)
            val tempRegionEntities = ArrayList<RegionEntity>()
            val tempMapEntities = ArrayList<MapEntity>()

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val regionEntity = jsonObject.toRegionEntity()
                tempRegionEntities.add(regionEntity)

                val mapEntitiesFromJson = jsonObject.toMapEntities()
                tempMapEntities.addAll(mapEntitiesFromJson)
            }
            regionEntities.value = tempRegionEntities
            mapEntities.value = tempMapEntities
            SqlDatabase.getInstance().getRegionDao().insertRegionEntities(tempRegionEntities)
            SqlDatabase.getInstance().getMapDao().insertMapEntities(tempMapEntities)

        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            Log.e("JsonSyntaxException", "Error parsing JSON: ${e.message}")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("JsonSyntaxException", "Error parsing JSON: ${e.message}")
        }
    }

    /**
     * 區域列表
     */
    fun getRegionNameList(regionEntities: ArrayList<RegionEntity>): ArrayList<String> {
//        return storageInformationList.map { it.RegionName } as ArrayList<String>
        return regionEntities.map { it.regionName } as ArrayList<String>
    }


    /**
     * 根據區域名稱列出地圖列表
     * @param regionName 區域名稱
     */
    fun getMapNameList(
        targetRegionName: String,
        mapEntities: ArrayList<MapEntity>
    ): ArrayList<String> {
        return mapEntities.filter { it.regionName == targetRegionName }
            ?.map { it.mapName } as ArrayList<String>
    }


    /**
     * 根據區域名稱和地圖名稱列出儲櫃列表
     * @param regionName 區域名稱
     * @param mapName 地圖名稱
     */
    fun getStorageDetailList(
        targetRegionName: String,
        targetMapName: String,
        storageEntities: ArrayList<StorageEntity>
    ): ArrayList<StorageEntity> {
        val filteredStorageList: ArrayList<StorageEntity> = ArrayList()
        filteredStorageList.addAll(storageEntities.filter { storageEntity ->
            storageEntity.regionName == targetRegionName && storageEntity.mapName == targetMapName
        })
        // 使用 filter 函數來篩選符合條件的 StorageEntity
        return filteredStorageList
    }

    /**
     * 根據區域名稱、地圖名稱、儲櫃代號、貨物代號找出對應的數量
     * @param regionName 區域名稱
     * @param mapName 地圖名稱
     * @param storageName 儲櫃名稱
     * @param materialNum 貨物代號
     */
    fun getMaterialQuantity(
        regionName: String,
        mapName: String,
        storageName: String,
        materialNum: String,
        storageInformationList: ArrayList<StorageRecordEntity>
    ): String {
        val matchingItem = storageInformationList.find { item ->
            item.regionName == regionName &&
                    item.mapName == mapName &&
                    item.storageName == storageName &&
                    jsonStringToJson(item.itemInformation)["materialNumber"] == materialNum
        }

        return matchingItem!!.let {
            jsonStringToJson(it.itemInformation)["receivedQuantity"].toString()
        }
    }
}
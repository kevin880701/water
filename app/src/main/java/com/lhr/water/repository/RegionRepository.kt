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
import com.lhr.water.room.StorageContentEntity
import com.lhr.water.room.StorageEntity
import com.lhr.water.util.MapDataList
import org.json.JSONArray
import java.io.InputStreamReader
import java.nio.charset.Charset

class RegionRepository private constructor(private val context: Context) {
    // 使用者可看到的區域列表
    var regionEntities = ArrayList<RegionEntity>()

    // 所有地圖列表
    var mapEntities: MutableLiveData<ArrayList<MapEntity>> =
        MutableLiveData<ArrayList<MapEntity>>()
    // 所有儲櫃單列表
//    var storageEntities: MutableLiveData<ArrayList<StorageEntity>> =
//        MutableLiveData<ArrayList<StorageEntity>>()

    var storageEntities: ArrayList<StorageEntity> = arrayListOf(
        StorageEntity(
            id = 1,
            deptNumber = "0D60",
            mapSeq = 1,
            storageName = "儲櫃1",
            storageX = 100,
            storageY = 100,
        )
    )

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
        mapEntities.value = SqlDatabase.getInstance().getMapDao().getAllMap() as ArrayList
    }


    fun filterRegionEntity(specifiedDeptNumber: String) {
        val filteredList = MapDataList.filter { entity ->
            entity.deptNumber == specifiedDeptNumber
        } as ArrayList<RegionEntity>
        regionEntities = filteredList
    }

    /**
     * 區域列表
     */
    fun getRegionNameList(regionEntities: ArrayList<RegionEntity>): ArrayList<String> {
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
     * 根據區域名稱、地圖名稱、儲櫃名稱、貨物代號找出對應的數量
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
        storageInformationList: ArrayList<StorageContentEntity>
    ): String {
        val matchingItem = storageInformationList.find { item ->
            item.regionName == regionName &&
                    item.mapName == mapName &&
                    item.storageName == storageName &&
                    item.materialNumber == materialNum
        }
        if (matchingItem != null) {
            println("@@@@@@：${matchingItem.quantity}")
        }

        return matchingItem!!.let {
            matchingItem.quantity.toString()
        }
    }
}
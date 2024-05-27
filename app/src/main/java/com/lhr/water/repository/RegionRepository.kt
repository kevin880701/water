package com.lhr.water.repository

import android.content.Context
import com.google.gson.Gson
import com.lhr.water.data.form.DeliveryForm
import com.lhr.water.data.form.ReceiveForm
import com.lhr.water.data.form.ReturnForm
import com.lhr.water.data.form.TransferForm
import com.lhr.water.network.data.response.UpdateDataResponse
import com.lhr.water.room.CheckoutEntity
import com.lhr.water.room.FormEntity
import com.lhr.water.room.InventoryEntity
import com.lhr.water.room.RegionEntity
import com.lhr.water.room.SqlDatabase
import com.lhr.water.room.StorageEntity
import com.lhr.water.room.StorageRecordEntity
import com.lhr.water.util.MapDataList

class RegionRepository private constructor(private val context: Context) {
    // 使用者可看到的區域列表
    var regionEntities = ArrayList<RegionEntity>()

    var storageEntities = ArrayList<StorageEntity>()

    private val sqlDatabase = SqlDatabase.getInstance()

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
        loadSqlData()
    }

    fun loadSqlData(){
        storageEntities = SqlDatabase.getInstance().getStorageDao().getAll() as ArrayList<StorageEntity>
    }

    fun updateSqlData(
        storageList: List<StorageEntity>
    ) {
        // 先清除資料表
        sqlDatabase.getStorageDao().clearTable()

        // 將updateDataResponse的儲櫃資訊插入資料表
        sqlDatabase.getStorageDao()
            .insertStorageEntities(storageList)

        // 更新資料
        loadSqlData()

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
     * 根據儲櫃ID查找對應的儲櫃資訊StorageEntity。
     * @param storageId 儲櫃ID
     * @return 符合儲存ID的儲存實體
     */
   fun findStorageEntityByStorageId(storageId :Int): StorageEntity {
       val foundStorageEntity = storageEntities.find { it.storageId == storageId }
       return foundStorageEntity!!
   }

    /**
     * 根據部門代號和地圖序號查找對應的RegionEntity。
     * @param deptNumber 部門代號
     * @param mapSeq 地圖序號
     * @return 符合部門代號和地圖序號的RegionEntity
     */
    fun findRegionEntityByDeptNumberAndMapSeq(deptNumber :String, mapSeq :Int): RegionEntity {
        val foundRegionEntity = MapDataList.find { it.deptNumber == deptNumber && it.mapSeq == mapSeq }

        return foundRegionEntity!!
    }
}
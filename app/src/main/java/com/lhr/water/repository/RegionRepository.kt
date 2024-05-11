package com.lhr.water.repository

import android.content.Context
import com.lhr.water.data.fakerCheckoutEntities
import com.lhr.water.data.fakerStorageEntities
import com.lhr.water.data.fakerStorageRecordEntities
import com.lhr.water.room.RegionEntity
import com.lhr.water.room.CheckoutEntity
import com.lhr.water.room.SqlDatabase
import com.lhr.water.room.StorageEntity
import com.lhr.water.room.StorageRecordEntity
import com.lhr.water.util.MapDataList

class RegionRepository private constructor(private val context: Context) {
    // 使用者可看到的區域列表
    var regionEntities = ArrayList<RegionEntity>()

    var storageEntities = ArrayList<StorageEntity>()

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
        importFakeDataIfEmpty()
        updateData()
    }

    fun updateData(){
        storageEntities = SqlDatabase.getInstance().getStorageDao().getAll() as ArrayList<StorageEntity>
    }


    fun importFakeDataIfEmpty() {
        var storageDao = SqlDatabase.getInstance().getStorageDao()
        var checkoutDao = SqlDatabase.getInstance().getCheckoutDao()
        var storageRecordDao = SqlDatabase.getInstance().getStorageRecordDao()
        if (storageDao.getRecordCount() == 0) {
            // 匯入假資料
            storageDao.insertStorageEntities(fakerStorageEntities)
        }

        if (checkoutDao.getRecordCount() == 0) {
            // 匯入假資料
            checkoutDao.insertCheckoutEntities(fakerCheckoutEntities)
        }

        if (storageRecordDao.getRecordCount() == 0) {
            // 匯入假資料
            storageRecordDao.insertStorageRecordEntities(fakerStorageRecordEntities)
        }
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
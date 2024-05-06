package com.lhr.water.repository

import android.content.Context
import com.lhr.water.room.RegionEntity
import com.lhr.water.room.CheckoutEntity
import com.lhr.water.room.SqlDatabase
import com.lhr.water.room.StorageEntity
import com.lhr.water.room.StorageRecordEntity
import com.lhr.water.util.MapDataList

class RegionRepository private constructor(private val context: Context) {
    // 使用者可看到的區域列表
    var regionEntities = ArrayList<RegionEntity>()

    var storageEntities: ArrayList<StorageEntity> = arrayListOf(
        StorageEntity(
            id = 1,
            deptNumber = "0D60",
            mapSeq = 1,
            storageName = "儲櫃1",
            storageX = 100,
            storageY = 100,
        ),
        StorageEntity(
            id = 2,
            deptNumber = "0D60",
            mapSeq = 2,
            storageName = "儲櫃2",
            storageX = 100,
            storageY = 100,
        ),
        StorageEntity(
            id = 3,
            deptNumber = "0510",
            mapSeq = 7,
            storageName = "儲櫃3",
            storageX = 100,
            storageY = 100,
        ),
        StorageEntity(
            id = 4,
            deptNumber = "0B10",
            mapSeq = 1,
            storageName = "儲櫃4",
            storageX = 100,
            storageY = 100,
        )
    )

    var checkoutEntities: ArrayList<CheckoutEntity> = arrayListOf(
        CheckoutEntity(
            storageId = 1,
            materialName = "材料1",
            materialNumber = "1",
            quantity = 10,
            inputTime = "20240406-182631",
            checkoutTime = "20240501-000000",
        ),
        CheckoutEntity(
            storageId = 1,
            materialName = "材料2",
            materialNumber = "2",
            quantity = 10,
            inputTime = "20240406-182639",
            checkoutTime = "20240501-000000",
        )
    )


    var storageRecordEntities: ArrayList<StorageRecordEntity> = arrayListOf(
        StorageRecordEntity(
            storageId = 1,
            reportTitle = 1,
            formNumber = "M0001",
            materialName = "材料1",
            materialNumber = "1",
            InvtStat = 1,
            userId = "U0001",
            quantity = 10,
            date = "20240506-182631",
        ),
        StorageRecordEntity(
            storageId = 1,
            reportTitle = 5,
            formNumber = "M0002",
            materialName = "材料1",
            materialNumber = "1",
            InvtStat = 3,
            userId = "U0001",
            quantity = 5,
            date = "20240507-182631",
        ),
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
        // 取checkout資料，暫時用假資料
//        checkoutEntities = SqlDatabase.getInstance().getCheckoutDao().getAll() as ArrayList<CheckoutEntity>
        // 取儲櫃紀錄資料，暫時用假資料
//        storageRecordEntities = SqlDatabase.getInstance().getStorageRecordDao().getAll() as ArrayList<StorageRecordEntity>
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
     * 根據區域名稱、地圖名稱、儲櫃名稱、貨物代號找出對應的數量
     * @param regionName 區域名稱
     * @param mapName 地圖名稱
     * @param storageName 儲櫃名稱
     * @param materialNum 貨物代號
     */
//    fun getMaterialQuantity(
//        regionName: String,
//        mapName: String,
//        storageName: String,
//        materialNum: String,
//        storageInformationList: ArrayList<CheckoutEntity>
//    ): String {
//        val matchingItem = storageInformationList.find { item ->
//            item.storageId == regionName &&
//                    item.mapName == mapName &&
//                    item.storageName == storageName &&
//                    item.materialNumber == materialNum
//        }
//
//        return matchingItem!!.let {
//            matchingItem.quantity.toString()
//        }
//    }
}
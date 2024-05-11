package com.lhr.water.ui.map

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lhr.water.repository.RegionRepository
import com.lhr.water.repository.FormRepository
import com.lhr.water.repository.UserRepository
import com.lhr.water.room.RegionEntity
import com.lhr.water.room.StorageEntity
import com.lhr.water.room.StorageRecordEntity
import com.lhr.water.ui.base.APP
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MapViewModel(context: Context, var regionRepository: RegionRepository, var formRepository: FormRepository, var userRepository: UserRepository): AndroidViewModel(context.applicationContext as APP) {

    var storageEntityList = MutableLiveData<ArrayList<StorageEntity>>()

    fun setStorageEntityList(regionEntity: RegionEntity){
        val filteredList = regionRepository.storageEntities.filter { entity ->
            entity.deptNumber == regionEntity.deptNumber && entity.mapSeq == regionEntity.mapSeq
        } as ArrayList<StorageEntity>
        storageEntityList.value = filteredList
    }

    fun getStorageContentList(storageId: Int): ArrayList<StorageRecordEntity>{
        // 獲取當前年月
        val currentYearMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"))

        // 過濾出指定storageId的資料並且是本月的 CheckoutEntity
        val filteredCheckoutEntities = formRepository.checkoutEntities.value!!.filter {
            it.storageId == storageId &&
                    it.checkoutTime.startsWith(currentYearMonth)
        }

        // 將符合條件的 CheckoutEntity 轉換為 StorageRecordEntity，用於之後計算數量
        val convertedStorageRecordEntities = filteredCheckoutEntities.map { checkoutEntity ->
            StorageRecordEntity(
                storageId = checkoutEntity.storageId,
                formType = 0,
                formNumber = "",
                materialName = checkoutEntity.materialName,
                materialNumber = checkoutEntity.materialNumber,
                outputTime = "",
                inputTime = checkoutEntity.inputTime,
                materialStatus = 2,
                userId = userRepository.userInfo.userId,
                quantity = checkoutEntity.quantity,
                recordDate = checkoutEntity.inputTime
            )
        }.toMutableList() as ArrayList<StorageRecordEntity>

//--------------------------------------------------------------------------
        // 過濾出指定storageId的資料並且是本月的 StorageRecordEntity
        var fullStorageRecordEntities = ArrayList<StorageRecordEntity>()
        fullStorageRecordEntities.addAll(formRepository.storageRecordEntities.value!!)
        fullStorageRecordEntities.addAll(convertedStorageRecordEntities)
        val filteredStorageRecordEntities = fullStorageRecordEntities.filter {
            it.storageId == storageId &&
                    it.recordDate.startsWith(currentYearMonth)
        }

        // 創建InvtStat=3 的數據的列表
        val invtStat3Records = filteredStorageRecordEntities.filter { it.materialStatus == 3 }
        // 創建InvtStat=2 的數據的列表
        val invtStat2Records = filteredStorageRecordEntities.filter { it.materialStatus == 2 }
        // 創建InvtStat=1 的數據的列表
        val invtStat1Records = filteredStorageRecordEntities.filter { it.materialStatus == 1 }

        // 根據 materialName、materialNumber、date 進行分組
        val groupedInvtStat3Records = invtStat3Records.groupBy {
            Triple(it.materialName, it.materialNumber, it.inputTime)
        }
        val groupedInvtStat2Records = invtStat2Records.groupBy {
            Triple(it.materialName, it.materialNumber, it.inputTime)
        }
        val groupedInvtStat1Records = invtStat1Records.groupBy {
            Triple(it.materialName, it.materialNumber, it.inputTime)
        }

        // 將各自materialName、materialNumber、date的quantity加總
        val processedInvtStat3Records = groupedInvtStat3Records.mapValues { (_, records) ->
            records.sumOf { it.quantity }
        }.toList()

        val processedInvtStat2Records = groupedInvtStat2Records.mapValues { (_, records) ->
            records.sumOf { it.quantity }
        }.toList()

        val processedInvtStat1Records = groupedInvtStat1Records.mapValues { (_, records) ->
            records.sumOf { it.quantity }
        }.toList()

        // 創建一個新的 ArrayList 用於存儲結果
        val resultStorageRecordEntities = ArrayList<StorageRecordEntity>()

        // 對 InvtStat = 2和3 的數據進行處理
        processedInvtStat2Records.forEach { (triple, quantity2) ->
            val (materialName, materialNumber, inputTime) = triple
            val quantity3 = processedInvtStat3Records.find { it.first == triple }?.second ?: 0 // 查找与当前项相同的 processedInvtStat3Records 中的数量，如果不存在则默认为 0
            // 計算驗收-移出
            val totalQuantity = quantity2 - quantity3

            // 如果 totalQuantity 不等於 0，則將該項加入 resultStorageRecordEntities
            if (totalQuantity != 0) {
                resultStorageRecordEntities.add(StorageRecordEntity(
                    storageId = 0, 
                    formType = 0, 
                    formNumber = "", 
                    materialName = materialName,
                    materialNumber = materialNumber,
                    outputTime = "",
                    inputTime = inputTime,
                    materialStatus = 2,
                    userId = "", 
                    quantity = totalQuantity,
                    recordDate = inputTime
                ))
            }
        }

        // 對 InvtStat=1 的數據進行處理
        processedInvtStat1Records.forEach { (key, quantity) ->
            val (materialName, materialNumber, inputTime) = key
            resultStorageRecordEntities.add(StorageRecordEntity(
                storageId = 0, 
                formType = 0, 
                formNumber = "", 
                materialName = materialName,
                materialNumber = materialNumber,
                outputTime = "",
                inputTime = inputTime,
                materialStatus = 1,
                userId = "", 
                quantity = quantity,
                recordDate = inputTime
            ))
        }

        return resultStorageRecordEntities
    }
}
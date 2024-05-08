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

        // 過濾出符合條件的 CheckoutEntity
        val filteredCheckoutEntities = formRepository.checkoutEntities.filter {
            it.storageId == storageId &&
                    it.checkoutTime.startsWith(currentYearMonth)
        }

        // 將符合條件的 CheckoutEntity 轉換為 StorageRecordEntity
        val convertedStorageRecordEntities = filteredCheckoutEntities.map { checkoutEntity ->
            StorageRecordEntity(
                storageId = checkoutEntity.storageId,
                formType = 0,
                formNumber = "",
                materialName = checkoutEntity.materialName,
                materialNumber = checkoutEntity.materialNumber,
                InvtStat = 2,
                userId = userRepository.userData.userId,
                InvtDevi = 2,
                quantity = checkoutEntity.quantity,
                date = checkoutEntity.inputTime
            )
        }.toMutableList() as ArrayList<StorageRecordEntity>

//--------------------------------------------------------------------------
        // 過濾出符合條件的 StorageRecordEntity
        var fullStorageRecordEntities = ArrayList<StorageRecordEntity>()
        fullStorageRecordEntities.addAll(formRepository.storageRecordEntities)
        fullStorageRecordEntities.addAll(convertedStorageRecordEntities)
        val filteredStorageRecordEntities = fullStorageRecordEntities.filter {
            it.storageId == storageId &&
                    it.date.startsWith(currentYearMonth)
        }

        // 創建InvtStat=2 的數據的列表
        val invtStat2Records = filteredStorageRecordEntities.filter { it.InvtStat == 2 }

        // 創建InvtStat=1 的數據的列表
        val invtStat1Records = filteredStorageRecordEntities.filter { it.InvtStat == 1 }

        // 根據 materialName、materialNumber、date 進行分組
        val groupedInvtStat2Records = invtStat2Records.groupBy {
            Triple(it.materialName, it.materialNumber, it.date)
        }
        val groupedInvtStat1Records = invtStat1Records.groupBy {
            Triple(it.materialName, it.materialNumber, it.date)
        }

        // 創建一個新的 ArrayList 用於存儲結果
        val resultStorageRecordEntities = ArrayList<StorageRecordEntity>()

        // 對 InvtStat=2 的數據進行處理
        groupedInvtStat2Records.forEach { (key, records) ->
            val totalQuantity = records.sumBy { it.quantity }

            // 取第一個記錄作為模板
            val firstRecord = records.firstOrNull()

            // 創建一個新的 StorageRecordEntity 並添加到結果列表中
            firstRecord?.let { record ->
                val newRecord = StorageRecordEntity(
                    storageId = record.storageId,
                    formType = record.formType,
                    formNumber = record.formNumber,
                    materialName = record.materialName,
                    materialNumber = record.materialNumber,
                    InvtStat = 2,
                    userId = record.userId,
                    InvtDevi = record.InvtDevi,
                    quantity = totalQuantity,
                    date = record.date
                )
                resultStorageRecordEntities.add(newRecord)
            }
        }

        // 對 InvtStat=1 的數據進行處理
        groupedInvtStat1Records.forEach { (key, records) ->
            val totalQuantity = records.sumBy { it.quantity }

            // 取第一個記錄作為模板
            val firstRecord = records.firstOrNull()

            // 創建一個新的 StorageRecordEntity 並添加到結果列表中
            firstRecord?.let { record ->
                val newRecord = StorageRecordEntity(
                    storageId = record.storageId,
                    formType = record.formType,
                    formNumber = record.formNumber,
                    materialName = record.materialName,
                    materialNumber = record.materialNumber,
                    InvtStat = 1,
                    userId = record.userId,
                    InvtDevi = record.InvtDevi,
                    quantity = totalQuantity,
                    date = record.date
                )
                resultStorageRecordEntities.add(newRecord)
            }
        }

        return resultStorageRecordEntities
    }
}
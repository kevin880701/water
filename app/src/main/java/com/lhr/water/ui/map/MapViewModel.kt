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
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class MapViewModel(context: Context, var regionRepository: RegionRepository, var formRepository: FormRepository, var userRepository: UserRepository): AndroidViewModel(context.applicationContext as APP) {

    var storageEntityList = MutableLiveData<ArrayList<StorageEntity>>()

    fun setStorageEntityList(regionEntity: RegionEntity){
        val filteredList = regionRepository.storageEntities.filter { entity ->
            entity.deptNumber == regionEntity.deptNumber && entity.mapSeq == regionEntity.mapSeq
        } as ArrayList<StorageEntity>
        storageEntityList.value = filteredList
    }

    fun getStorageContentList(storageId: Int): ArrayList<StorageRecordEntity>{
        // 获取当前年月
        val currentYearMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"))

// 过滤出符合条件的 CheckoutEntity
        val filteredCheckoutEntities = regionRepository.checkoutEntities.filter {
            it.storageId == storageId &&
                    it.checkoutTime.startsWith(currentYearMonth)
        }

// 将符合条件的 CheckoutEntity 转换为 StorageRecordEntity
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
        // 过滤出符合条件的 StorageRecordEntity
        var fullStorageRecordEntities = ArrayList<StorageRecordEntity>()
        fullStorageRecordEntities.addAll(regionRepository.storageRecordEntities)
        fullStorageRecordEntities.addAll(convertedStorageRecordEntities)

        // 过滤出符合条件的 StorageRecordEntity
        val filteredStorageRecordEntities = fullStorageRecordEntities.filter {
            it.storageId == storageId &&
                    it.date.startsWith(currentYearMonth)
        }

// 创建用于存储 InvtStat=2 的数据的列表
        val invtStat2Records = filteredStorageRecordEntities.filter { it.InvtStat == 2 }

// 创建用于存储 InvtStat=1 的数据的列表
        val invtStat1Records = filteredStorageRecordEntities.filter { it.InvtStat == 1 }

// 根据 materialName、materialNumber、date 进行分组
        val groupedInvtStat2Records = invtStat2Records.groupBy {
            Triple(it.materialName, it.materialNumber, it.date)
        }

// 根据 materialName、materialNumber、date 进行分组
        val groupedInvtStat1Records = invtStat1Records.groupBy {
            Triple(it.materialName, it.materialNumber, it.date)
        }

// 创建一个新的 ArrayList 用于存储结果
        val resultStorageRecordEntities = ArrayList<StorageRecordEntity>()

// 对 InvtStat=2 的数据进行处理
        groupedInvtStat2Records.forEach { (key, records) ->
            val totalQuantity = records.sumBy { it.quantity }

            // 取第一个记录作为模板
            val firstRecord = records.firstOrNull()

            // 创建一个新的 StorageRecordEntity 并添加到结果列表中
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

// 对 InvtStat=1 的数据进行处理
        groupedInvtStat1Records.forEach { (key, records) ->
            val totalQuantity = records.sumBy { it.quantity }

            // 取第一个记录作为模板
            val firstRecord = records.firstOrNull()

            // 创建一个新的 StorageRecordEntity 并添加到结果列表中
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
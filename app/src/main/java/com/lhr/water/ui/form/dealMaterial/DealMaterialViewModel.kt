package com.lhr.water.ui.form.dealMaterial

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lhr.water.data.form.BaseItem
import com.lhr.water.repository.FormRepository
import com.lhr.water.repository.RegionRepository
import com.lhr.water.repository.UserRepository
import com.lhr.water.room.FormEntity
import com.lhr.water.room.RegionEntity
import com.lhr.water.room.StorageEntity
import com.lhr.water.room.StorageRecordEntity
import com.lhr.water.ui.base.APP
import com.lhr.water.util.MapDataList
import com.lhr.water.util.formTypeMap
import com.lhr.water.util.getCurrentDate
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DealMaterialViewModel(
    context: Context,
    var regionRepository: RegionRepository,
    var formRepository: FormRepository,
    var userRepository: UserRepository
) : AndroidViewModel(context.applicationContext as APP) {

    // WaitDealMaterialDialog
    var selectRegion = MutableLiveData<RegionEntity>()
    var selectDept = MutableLiveData<RegionEntity>()
    var selectStorage = MutableLiveData<StorageEntity>()
    var selectInputTime = MutableLiveData<String>()
    val currentQuantity = MutableLiveData<String>()


    init {
    }

    // 出庫相關

    fun getOutputRegionList(filterStorageRecordEntities: ArrayList<StorageRecordEntity>): ArrayList<RegionEntity> {

        // 找到指定商品在哪些儲櫃後，找出對應區域、部門並轉成ArrayList<RegionEntity>
        val resultRegionEntities = arrayListOf<RegionEntity>()

        // 遍歷 resultRecords
        filterStorageRecordEntities.forEach { storageRecord ->
            // 根據 storageId 找到對應的 StorageEntity
            val storageEntity = regionRepository.storageEntities.find {
                println("it.storageId：${it.storageId}")
                println("storageRecord.storageId：${storageRecord.storageId}")
                it.storageId == storageRecord.storageId }

            // 如果找到了對應的 StorageEntity，使用 StorageEntity 的 deptNumber 和 mapSeq 找到對應的 RegionEntity
            storageEntity?.let { entity ->
                val regionEntity = MapDataList.find { it.deptNumber == entity.deptNumber && it.mapSeq == entity.mapSeq }

                // 如果找到了對應的 RegionEntity，將找到的 RegionEntity 添加到 resultRegionEntities
                regionEntity?.let {
                    resultRegionEntities.add(it)
                }
            }
        }

        return resultRegionEntities
    }


    //抓指定貨物在那些儲櫃，有多少數量
    fun getSpecifiedMaterialStorageRecordEntities(materialNumber: String): ArrayList<StorageRecordEntity> {

        // 獲取當前年月
        val currentYearMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"))

        // 過濾出指定storageId的資料並且是本月的 CheckoutEntity
        val filteredCheckoutEntities = formRepository.checkoutEntities.value!!.filter {
            it.materialNumber == materialNumber &&
                    it.checkoutTime.startsWith(currentYearMonth)
        }

        // 將符合條件的 CheckoutEntity 轉換為 StorageRecordEntity，用於之後計算數量
        val convertedStorageRecordEntities = filteredCheckoutEntities.map { checkoutEntity ->
            StorageRecordEntity(
                storageId = checkoutEntity.storageId,
                formType = "0",
                formNumber = "",
                materialName = checkoutEntity.materialName,
                materialNumber = checkoutEntity.materialNumber,
                materialStatus = "2",
                userId = userRepository.userInfo.value!!.userId,
                quantity = checkoutEntity.quantity.toString(),
                recordDate = checkoutEntity.inputTime,
                storageArrivalId = "",
                itemId = 0
            )
        }.toMutableList() as ArrayList<StorageRecordEntity>

//--------------------------------------------------------------------------
        // 過濾出指定materialNumber的資料並且是本月的 StorageRecordEntity
        var fullStorageRecordEntities = ArrayList<StorageRecordEntity>()
        fullStorageRecordEntities.addAll(formRepository.storageRecordEntities.value!!)
        fullStorageRecordEntities.addAll(convertedStorageRecordEntities)
        val filteredStorageRecordEntities = fullStorageRecordEntities.filter {
            it.materialNumber == materialNumber &&
                    it.recordDate.startsWith(currentYearMonth)
        }

        // 創建InvtStat=2 的數據的列表
        val invtStat2Records = filteredStorageRecordEntities.filter { it.materialStatus == "2" }
        // 創建InvtStat=3 的數據的列表
        val invtStat3Records = filteredStorageRecordEntities.filter { it.materialStatus == "3" }

        // 根據 storageId、materialName、materialNumber、date 進行分組
        val groupedRecords2 = invtStat2Records.groupBy { record ->
            "${record.materialName}-${record.materialNumber}-${record.recordDate}-${record.storageId}"
        }
        val groupedRecords3 = invtStat3Records.groupBy { record ->
            "${record.materialName}-${record.materialNumber}-${record.recordDate}-${record.storageId}"
        }

        // 用於儲存計算完的List
        val integratedRecords2 = ArrayList<StorageRecordEntity>()
        val integratedRecords3 = ArrayList<StorageRecordEntity>()

        // 遍歷分組記錄的 Map，將其 quantity 相加
        groupedRecords2.forEach { (_, records) ->
            var totalQuantity = 0

            records.forEach { record ->
                totalQuantity += record.quantity.toInt()
            }

            // 創建整合後的記錄StorageRecordEntity，並添加到列表中
            records.firstOrNull()?.let { firstRecord ->
                integratedRecords2.add(
                    StorageRecordEntity(
                        storageId = firstRecord.storageId,
                        formType = firstRecord.formType,
                        formNumber = firstRecord.formNumber,
                        materialName = firstRecord.materialName,
                        materialNumber = firstRecord.materialNumber,
                        materialStatus = firstRecord.materialStatus,
                        userId = firstRecord.userId,
                        quantity = totalQuantity.toString(),
                        recordDate = firstRecord.recordDate,
                        storageArrivalId = firstRecord.recordDate,
                        itemId = 0
                    )
                )
            }
        }

        groupedRecords3.forEach { (_, records) ->
            var totalQuantity = 0

            records.forEach { record ->
                totalQuantity += record.quantity.toInt()
            }

            // 創建整合後的記錄StorageRecordEntity，並添加到列表中
            records.firstOrNull()?.let { firstRecord ->
                integratedRecords3.add(
                    StorageRecordEntity(
                        storageId = firstRecord.storageId,
                        formType = firstRecord.formType,
                        formNumber = firstRecord.formNumber,
                        materialName = firstRecord.materialName,
                        materialNumber = firstRecord.materialNumber,
                        materialStatus = firstRecord.materialStatus,
                        userId = firstRecord.userId,
                        quantity = totalQuantity.toString(),
                        recordDate = firstRecord.recordDate,
                        storageArrivalId = "",
                        itemId = 0
                    )
                )
            }
        }

        val resultRecords = ArrayList<StorageRecordEntity>()

        // 遍歷 integratedRecords2 中的每個記錄
        integratedRecords2.forEach { record2 ->
            // 用來判斷是否找到匹配的記錄
            var foundMatch = false

            // 遍歷 integratedRecords3 中的每個記錄
            integratedRecords3.forEach { record3 ->
                // 檢查是否存在匹配的記錄
                if (record2.materialName == record3.materialName &&
                    record2.materialNumber == record3.materialNumber &&
                    record2.recordDate == record3.recordDate &&
                    record2.storageId == record3.storageId
                ) {
                    // 找到匹配的記錄，將 record2 的數量減去 record3 的數量，並添加到結果列表中
                    val quantityDiff = record2.quantity.toInt() - record3.quantity.toInt()
                    if (quantityDiff != 0) {
                        resultRecords.add(
                            StorageRecordEntity(
                                storageId = record2.storageId,
                                formType = record2.formType,
                                formNumber = record2.formNumber,
                                materialName = record2.materialName,
                                materialNumber = record2.materialNumber,
                                materialStatus = record2.materialStatus,
                                userId = record2.userId,
                                quantity = quantityDiff.toString(),
                                recordDate = record2.recordDate,
                                storageArrivalId = "",
                                itemId = 0
                            )
                        )
                    }
                    // 設定為 true，表示找到匹配的記錄
                    foundMatch = true
                }
            }

            // 如果沒有找到匹配的記錄，則將 record2 添加到結果列表中
            if (!foundMatch) {
                resultRecords.add(record2)
            }
        }

        return resultRecords
    }


    fun getOutputStorageSpinnerList(specifiedDeptNumber: String, specifiedMapSeq: Int, specifiedMaterialStorageRecordEntities: ArrayList<StorageRecordEntity>): ArrayList<StorageEntity> {
        // 過濾出符合指定 deptNumber 和 mapSeq 的記錄
        val filteredStorageEntities = regionRepository.storageEntities.filter { storageEntity ->
            storageEntity.deptNumber == specifiedDeptNumber && storageEntity.mapSeq == specifiedMapSeq
        }
        // 過濾出 storageId 在 specifiedMaterialStorageRecordEntities 中出現的記錄
        val resultStorageEntities = filteredStorageEntities.filter { storageEntity ->
            specifiedMaterialStorageRecordEntities.any { it.storageId == storageEntity.storageId }
        }

        return ArrayList(resultStorageEntities)
    }


    fun getOutputTimeSpinnerList(storageId: Int, specifiedMaterialStorageRecordEntities: ArrayList<StorageRecordEntity>): ArrayList<String> {
        val inputTimeList = specifiedMaterialStorageRecordEntities
            .filter { it.storageId == storageId }
            .map { it.recordDate }
            .toList()

        return ArrayList(inputTimeList)
    }

    fun getOutputMaxQuantity(needQuantity: Int, formNumber: String, storageId: Int, inputTime: String, specifiedMaterialStorageRecordEntities: ArrayList<StorageRecordEntity>): Int {
        // 根據指定的 storageId 和 inputTime 查找對應的項
        val matchingRecords = specifiedMaterialStorageRecordEntities.filter {
            it.storageId == storageId && it.recordDate == inputTime
        }

        // 暫存清單裡的數量
        val tempQuantity = formRepository.tempStorageRecordEntities.value!!
            .filter { it.formNumber == formNumber }
            .sumOf { it.quantity.toInt() }

        // 如果找到匹配的記錄
        if (matchingRecords.isNotEmpty()) {
            // 計算匹配記錄的總數量
            val totalQuantity = matchingRecords.sumOf { it.quantity.toInt() }

            // 返回較小的值，即所需數量和總數量之間的較小值
            return minOf(needQuantity, totalQuantity - tempQuantity).coerceAtLeast(0)
        } else {
            // 如果未找到匹配的記錄，則返回所需數量
            return 0
        }
    }

    // 入庫相關

    fun getInputRegionList(): ArrayList<RegionEntity> {
        // 只列出使用者可看到的儲櫃
        val filteredStorageEntities = regionRepository.storageEntities.filter { it.deptNumber == userRepository.userInfo.value!!.deptAno}

        // 篩選掉重複的deptNumber和mapSeq
        val resultStorageEntities = filteredStorageEntities.distinctBy { it.deptNumber to it.mapSeq }


        // 根據篩選後的資料找出對應的ArrayList<RegionEntity>
        val resultRegionEntities = mutableListOf<RegionEntity>()
        resultStorageEntities.forEach { storage ->
            val regionEntity = MapDataList.find { it.deptNumber == storage.deptNumber && it.mapSeq == storage.mapSeq }
            regionEntity?.let { resultRegionEntities.add(it) }
        }
        return ArrayList(resultRegionEntities)
    }

    fun getDeptSpinnerList(regionNumber: String, regionEntities: ArrayList<RegionEntity>): ArrayList<RegionEntity> {

        return regionEntities.filter { it.regionNumber == regionNumber} as ArrayList<RegionEntity>
    }

    fun getStorageSpinnerList(specifiedDeptNumber: String, specifiedMapSeq: Int): ArrayList<StorageEntity> {
        val filteredStorageEntities = regionRepository.storageEntities.filter { storageEntity ->
            storageEntity.deptNumber == specifiedDeptNumber && storageEntity.mapSeq == specifiedMapSeq
        }.toMutableList()

        return ArrayList(filteredStorageEntities)
    }


    /**
     * 將選擇貨物加入儲櫃中並更新暫存待入庫的貨物列表
     * @param form 表單資訊
     * @param itemDetail 貨物資訊
     * @param storageEntity 所選儲櫃資訊
     * @param materialQuantity 選擇貨物數量
     */
    fun inputInTempGoods(
        form: FormEntity,
        itemDetail: BaseItem,
        storageEntity: StorageEntity,
        materialQuantity: String,
        isInput: Boolean,
        inputTime: String? = null
    ) {

        // 需要為貨物加上地區、地圖、儲櫃名稱、報表名稱、報表代號、入庫時間欄位
        var tempDealGoodsData = StorageRecordEntity(
            storageId = storageEntity.storageId,
            formType = formTypeMap[form.reportTitle] ?:"0",
            formNumber =  form.formNumber,
            materialName =  itemDetail.materialName,
            materialNumber = itemDetail.materialNumber,
            materialStatus =  if (isInput) {
                if (form.reportTitle == "交貨通知單") "1" else "2"
            } else {
                "3"
            },
            userId = userRepository.userInfo.value!!.userId,
            quantity = materialQuantity,
            recordDate = getCurrentDate(),
            storageArrivalId = "",
            isUpdate = false,
            itemId = itemDetail.itemId
        )

        // 更新暫存進貨列表
        val currentList = formRepository.tempStorageRecordEntities.value ?: ArrayList()
        currentList.add(tempDealGoodsData)
        formRepository.tempStorageRecordEntities.postValue(currentList)
    }

    fun filterTempWaitInputGoods(
        targetReportTitle: String,
        targetFormNumber: String
    ) = formRepository.filterTempStorageRecordEntities(
        formRepository.tempStorageRecordEntities.value!!,
        targetReportTitle,
        targetFormNumber
    )

}
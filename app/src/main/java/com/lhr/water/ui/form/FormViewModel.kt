package com.lhr.water.ui.form

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lhr.water.R
import com.lhr.water.data.Form
import com.lhr.water.data.ItemDetail
import com.lhr.water.repository.FormRepository
import com.lhr.water.repository.RegionRepository
import com.lhr.water.repository.UserRepository
import com.lhr.water.room.RegionEntity
import com.lhr.water.room.CheckoutEntity
import com.lhr.water.room.StorageEntity
import com.lhr.water.room.StorageRecordEntity
import com.lhr.water.ui.base.APP
import com.lhr.water.util.MapDataList
import com.lhr.water.util.formTypeMap
import com.lhr.water.util.getCurrentDate
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class FormViewModel(
    context: Context,
    var regionRepository: RegionRepository,
    var formRepository: FormRepository,
    var userRepository: UserRepository
) : AndroidViewModel(context.applicationContext as APP) {

    // 篩選表單代號formNumber的String
    var searchFormNumber = MutableLiveData<String>()
    // 篩選表單類別FormClass的List
    var filterList = MutableLiveData<ArrayList<String>>()

    // WaitDealMaterialDialog
    var selectRegion = MutableLiveData<RegionEntity>()
    var selectDept = MutableLiveData<RegionEntity>()
    var selectStorage = MutableLiveData<StorageEntity>()


    init {
        filterList.value = ArrayList(context.resources.getStringArray(R.array.form_array).toList())
        searchFormNumber.value = ""
    }

    /**
     * 篩選表單內容
     */
    fun filterRecord(formList: ArrayList<Form>,searchFormNumber: String,filterList: ArrayList<String>): ArrayList<Form>? {
        return formList.filter { form ->
            // 根據 "FormClass" 判斷是否在 filterList 中
            val reportTitle = form.reportTitle.toString()
            val reportTitleFilterCondition = filterList.contains(reportTitle)
            val formNumber = form.formNumber.toString()

            // 如果搜尋框(EditText)中的文本不為空，則判斷 "formNumber" 是否包含該文本
            val editTextFilterCondition = if (searchFormNumber.isNotEmpty()) {
                formNumber.contains(searchFormNumber, ignoreCase = true)
            } else {
                true // 搜尋框(EditText)，不添加 formNumber 的篩選條件
            }
            reportTitleFilterCondition!! && editTextFilterCondition
        }?.toMutableList()!! as ArrayList<Form>?
    }


    fun getInputRegionList(): ArrayList<RegionEntity> {
        // 只列出使用者可看到的儲櫃
        val filteredStorageEntities = regionRepository.storageEntities.filter { it.deptNumber == userRepository.userData.deptAno}

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

    fun getOutputRegionList(materialNumber: String): ArrayList<RegionEntity> {

        // 獲取當前年月
        val currentYearMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"))

        // 過濾出指定storageId的資料並且是本月的 CheckoutEntity
        val filteredCheckoutEntities = formRepository.checkoutEntities.filter {
            it.materialNumber == materialNumber &&
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
                inputTime = checkoutEntity.inputTime,
                InvtStat = 2,
                userId = userRepository.userData.userId,
                InvtDevi = 2,
                quantity = checkoutEntity.quantity,
                recordDate = checkoutEntity.inputTime
            )
        }.toMutableList() as ArrayList<StorageRecordEntity>

//--------------------------------------------------------------------------
        // 過濾出指定materialNumber的資料並且是本月的 StorageRecordEntity
        var fullStorageRecordEntities = ArrayList<StorageRecordEntity>()
        fullStorageRecordEntities.addAll(formRepository.storageRecordEntities)
        fullStorageRecordEntities.addAll(convertedStorageRecordEntities)
        val filteredStorageRecordEntities = fullStorageRecordEntities.filter {
            it.materialNumber == materialNumber &&
                    it.recordDate.startsWith(currentYearMonth)
        }

        // 創建InvtStat=2 的數據的列表
        val invtStat2Records = filteredStorageRecordEntities.filter { it.InvtStat == 2 }
        // 創建InvtStat=3 的數據的列表
        val invtStat3Records = filteredStorageRecordEntities.filter { it.InvtStat == 3 }

        // 根據 storageId、materialName、materialNumber、date 進行分組
        val groupedRecords2 = invtStat2Records.groupBy { record ->
            "${record.materialName}-${record.materialNumber}-${record.inputTime}-${record.storageId}"
        }
        val groupedRecords3 = invtStat3Records.groupBy { record ->
            "${record.materialName}-${record.materialNumber}-${record.inputTime}-${record.storageId}"
        }

        // 用於儲存計算完的List
        val integratedRecords2 = ArrayList<StorageRecordEntity>()
        val integratedRecords3 = ArrayList<StorageRecordEntity>()

        // 遍歷分組記錄的 Map，將其 quantity 相加
        groupedRecords2.forEach { (_, records) ->
            var totalQuantity = 0

            records.forEach { record ->
                totalQuantity += record.quantity
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
                        inputTime = firstRecord.inputTime,
                        InvtStat = firstRecord.InvtStat,
                        userId = firstRecord.userId,
                        quantity = totalQuantity,  // 整合后的总量
                        recordDate = firstRecord.recordDate
                    )
                )
            }
        }

        groupedRecords3.forEach { (_, records) ->
            var totalQuantity = 0

            records.forEach { record ->
                totalQuantity += record.quantity
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
                        inputTime = firstRecord.inputTime,
                        InvtStat = firstRecord.InvtStat,
                        userId = firstRecord.userId,
                        quantity = totalQuantity,  // 整合后的总量
                        recordDate = firstRecord.recordDate
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
                    record2.inputTime == record3.inputTime &&
                    record2.storageId == record3.storageId
                ) {
                    // 找到匹配的記錄，將 record2 的數量減去 record3 的數量，並添加到結果列表中
                    val quantityDiff = record2.quantity - record3.quantity
                    if (quantityDiff != 0) {
                        resultRecords.add(
                            StorageRecordEntity(
                                storageId = record2.storageId,
                                formType = record2.formType,
                                formNumber = record2.formNumber,
                                materialName = record2.materialName,
                                materialNumber = record2.materialNumber,
                                inputTime = record2.inputTime,
                                InvtStat = record2.InvtStat,
                                userId = record2.userId,
                                quantity = quantityDiff,
                                recordDate = record2.recordDate
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

        // 找到指定商品在哪些儲櫃後，找出對應區域、部門並轉成ArrayList<RegionEntity>
        val resultRegionEntities = arrayListOf<RegionEntity>()

        // 遍歷 resultRecords
        resultRecords.forEach { storageRecord ->
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
        form: Form,
        itemDetail: ItemDetail,
        storageEntity: StorageEntity,
        materialQuantity: String
    ) {

        // 需要為貨物加上地區、地圖、儲櫃名稱、報表名稱、報表代號、入庫時間欄位
        var tempDealGoodsData = StorageRecordEntity(
            storageId = storageEntity.storageId,
            formType = formTypeMap[form.reportTitle!!]?.let { it }?:0,
            formNumber =  form.formNumber!!,
            materialName =  itemDetail.materialName.toString(),
            materialNumber = itemDetail.materialNumber!!,
            inputTime = getCurrentDate(),
            InvtStat =  1,
            userId = userRepository.userData.userId,
            quantity = materialQuantity.toInt(),
            recordDate = getCurrentDate(),
        )

        // 更新暫存進貨列表
        val currentList = formRepository.tempStorageRecordEntities.value ?: ArrayList()
        currentList.add(tempDealGoodsData)
        formRepository.tempStorageRecordEntities.postValue(currentList)
    }

    fun getOutputGoodsStorageInformation(
        materialName: String,
        materialNumber: String
    ): ArrayList<CheckoutEntity> {
        var storageContentList = formRepository.storageGoods.value?.filter { entity ->
            materialName == entity.materialName && materialNumber == entity.materialNumber
        }
        return storageContentList as ArrayList
    }

    fun getOutputGoodsRegion(storageContentList: ArrayList<CheckoutEntity>): ArrayList<RegionEntity> {
        return storageContentList?.distinctBy { it.storageId }
             as ArrayList<RegionEntity>
    }

    fun getInputMaterialRegion(storageContentList: ArrayList<StorageEntity>): ArrayList<RegionEntity> {
        return storageContentList?.distinctBy { it.deptNumber }
             as ArrayList<RegionEntity>
    }

    fun getInputMaterialStorage(storageContentList: ArrayList<StorageEntity>): ArrayList<StorageEntity> {
        // 取出不重複的 regionName、mapName 和 storageName 並轉為 StorageEntity
        return storageContentList
            .distinctBy { it.deptNumber }
             as ArrayList<StorageEntity>
    }

    fun filterTempWaitInputGoods(
        targetReportTitle: String,
        targetFormNumber: String
    ) = formRepository.filterTempWaitInputGoods(
        formRepository.tempStorageRecordEntities.value!!,
        targetReportTitle,
        targetFormNumber
    )

}
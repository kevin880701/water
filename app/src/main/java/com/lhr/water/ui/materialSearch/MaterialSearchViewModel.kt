package com.lhr.water.ui.materialSearch

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lhr.water.repository.FormRepository
import com.lhr.water.repository.UserRepository
import com.lhr.water.room.StorageRecordEntity
import com.lhr.water.ui.base.APP
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MaterialSearchViewModel(
    context: Context,
    val formRepository: FormRepository, var userRepository: UserRepository
) : AndroidViewModel(context.applicationContext as APP) {

    // 貨物列表使用暫存清單，避免需要一直重複計算
    var tempStorageRecordEntities = MutableLiveData<ArrayList<StorageRecordEntity>>(ArrayList<StorageRecordEntity>())

    // 篩選materialName的String
    var searchMaterialName = MutableLiveData<String>("")

    init {
        tempStorageRecordEntities.postValue(getStorageContentList())
    }


    fun getStorageContentList(): ArrayList<StorageRecordEntity>{

        // 獲取當前年月
        val currentYearMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"))

        // 過濾出本月的 CheckoutEntity
        val filteredCheckoutEntities = formRepository.checkoutEntities.value!!.filter {
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
                    it.recordDate.startsWith(currentYearMonth)
        }

        // 創建InvtStat=1 的數據的列表
        val invtStat1Records = filteredStorageRecordEntities.filter { it.materialStatus == "1" }
        // 創建InvtStat=2 的數據的列表
        val invtStat2Records = filteredStorageRecordEntities.filter { it.materialStatus == "2" }
        // 創建InvtStat=3 的數據的列表
        val invtStat3Records = filteredStorageRecordEntities.filter { it.materialStatus == "3" }

        // 根據 storageId、materialName、materialNumber、date 進行分組
        val groupedRecords1 = invtStat1Records.groupBy { record ->
            "${record.materialName}-${record.materialNumber}-${record.recordDate}-${record.storageId}"
        }
        val groupedRecords2 = invtStat2Records.groupBy { record ->
            "${record.materialName}-${record.materialNumber}-${record.recordDate}-${record.storageId}"
        }
        val groupedRecords3 = invtStat3Records.groupBy { record ->
            "${record.materialName}-${record.materialNumber}-${record.recordDate}-${record.storageId}"
        }

        // 用於儲存計算完的List
        val integratedRecords1 = ArrayList<StorageRecordEntity>()
        val integratedRecords2 = ArrayList<StorageRecordEntity>()
        val integratedRecords3 = ArrayList<StorageRecordEntity>()

        // 遍歷分組記錄的 Map，將其 quantity 相加
        groupedRecords1.forEach { (_, records) ->
            var totalQuantity = 0

            records.forEach { record ->
                totalQuantity += record.quantity.toInt()
            }

            // 創建整合後的記錄StorageRecordEntity，並添加到列表中
            records.firstOrNull()?.let { firstRecord ->
                integratedRecords1.add(
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
                        quantity = totalQuantity.toString(),  // 整合後的數量
                        recordDate = firstRecord.recordDate,
                        storageArrivalId = "",
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
                        quantity = totalQuantity.toString(),  // 整合後的數量
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

        resultRecords.addAll(integratedRecords1)

        return resultRecords
    }



    /**
     * 篩選貨物名稱
     */
    fun filterRecord(storageRecordEntities: ArrayList<StorageRecordEntity>, searchQuery: String): ArrayList<StorageRecordEntity>? {
        return storageRecordEntities.filter {
            it.materialName.contains(searchQuery, ignoreCase = true) ||
                    it.materialNumber.contains(searchQuery, ignoreCase = true)
        } as ArrayList<StorageRecordEntity>
    }

}
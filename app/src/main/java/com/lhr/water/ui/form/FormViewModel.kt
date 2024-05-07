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


    fun getAllRegionList(): ArrayList<RegionEntity> {
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
            InvtStat =  1,
            userId = userRepository.userData.userId,
            quantity = materialQuantity.toInt(),
            date = getCurrentDate(),
        )

        // 更新暫存進貨列表
        val currentList = formRepository.tempWaitInputGoods.value ?: ArrayList()
        currentList.add(tempDealGoodsData)
        formRepository.tempWaitInputGoods.postValue(currentList)
    }


    /**
     * 將選擇貨物加入儲櫃中並更新暫存待入庫的貨物列表
     * @param form 表單資訊
     * @param itemDetail 貨物資訊
     * @param storageEntity 所選儲櫃資訊
     * @param materialQuantity 選擇貨物數量
     */
    fun outputInTempGoods(
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
            InvtStat =  1,
            userId = userRepository.userData.userId,
            quantity = materialQuantity.toInt(),
            date = getCurrentDate(),
        )

        // 更新暫存進貨列表
        val currentList = formRepository.tempWaitOutputGoods.value ?: ArrayList()
        currentList.add(tempDealGoodsData)
        formRepository.tempWaitOutputGoods.postValue(currentList)
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
        formRepository.tempWaitInputGoods.value!!,
        targetReportTitle,
        targetFormNumber
    )

    fun filterTempWaitOutputGoods(
        targetReportTitle: String,
        targetFormNumber: String
    ) = formRepository.filterTempWaitOutputGoods(
        formRepository.tempWaitOutputGoods.value!!,
        targetReportTitle,
        targetFormNumber
    )
}
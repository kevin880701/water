package com.lhr.water.ui.map

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lhr.water.repository.RegionRepository
import com.lhr.water.data.StorageDetail
import com.lhr.water.repository.FormRepository
import com.lhr.water.room.StorageContentEntity
import com.lhr.water.room.StorageRecordEntity
import com.lhr.water.ui.base.APP

class MapViewModel(context: Context, regionRepository: RegionRepository, formRepository: FormRepository): AndroidViewModel(context.applicationContext as APP) {

    val regionRepository = regionRepository
    val formRepository = formRepository
    var storageDetailList = MutableLiveData<ArrayList<StorageDetail>>()

    fun setStorageDetailList(regionName: String, mapName: String){
        storageDetailList.value = regionRepository.getStorageDetailList(regionName, mapName, regionRepository.storageInformationList)
    }

    fun getStorageContent(regionName: String, mapName: String, storageNum: String): ArrayList<StorageContentEntity>{
        return formRepository.getStorageContentByCondition(regionName, mapName, storageNum)
    }
}
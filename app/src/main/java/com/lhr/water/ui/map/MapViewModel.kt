package com.lhr.water.ui.map

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lhr.water.repository.RegionRepository
import com.lhr.water.repository.FormRepository
import com.lhr.water.room.StorageContentEntity
import com.lhr.water.room.StorageEntity
import com.lhr.water.ui.base.APP

class MapViewModel(context: Context, regionRepository: RegionRepository, formRepository: FormRepository): AndroidViewModel(context.applicationContext as APP) {

    val regionRepository = regionRepository
    val formRepository = formRepository
    var storageEntityList = MutableLiveData<ArrayList<StorageEntity>>()

    fun setStorageDetailList(regionName: String, mapName: String){
        storageEntityList.value = regionRepository.getStorageDetailList(regionName, mapName, regionRepository.storageEntities.value!!)
    }

    fun getStorageContent(regionName: String, mapName: String, storageName: String): ArrayList<StorageContentEntity>{
        return formRepository.getStorageContentByCondition(regionName, mapName, storageName)
    }
}
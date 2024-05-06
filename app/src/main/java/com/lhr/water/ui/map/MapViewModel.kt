package com.lhr.water.ui.map

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lhr.water.repository.RegionRepository
import com.lhr.water.repository.FormRepository
import com.lhr.water.room.RegionEntity
import com.lhr.water.room.CheckoutEntity
import com.lhr.water.room.StorageEntity
import com.lhr.water.ui.base.APP

class MapViewModel(context: Context, regionRepository: RegionRepository, formRepository: FormRepository): AndroidViewModel(context.applicationContext as APP) {

    val regionRepository = regionRepository
    val formRepository = formRepository
    var storageEntityList = MutableLiveData<ArrayList<StorageEntity>>()

    fun setStorageDetailList(regionEntity: RegionEntity){
        val filteredList = regionRepository.storageEntities.filter { entity ->
            entity.deptNumber == regionEntity.deptNumber && entity.mapSeq == regionEntity.mapSeq
        } as ArrayList<StorageEntity>
        storageEntityList.value = filteredList
    }
}
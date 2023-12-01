package com.lhr.water.ui.map

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lhr.water.repository.RegionRepository
import com.lhr.water.data.StorageDetail
import com.lhr.water.ui.base.APP

class MapViewModel(context: Context, regionRepository: RegionRepository): AndroidViewModel(context.applicationContext as APP) {

    var regionRepository = regionRepository
//    var targetDataArrayList = MutableLiveData<ArrayList<TargetData>>()
    var storageDetailList = MutableLiveData<ArrayList<StorageDetail>>()

    companion object {
    }

    init {

    }


    fun setStorageDetailList(regionName: String, mapName: String){
        storageDetailList.value = regionRepository.getStorageDetailList(regionName, mapName)
    }
    fun setTargetDataArrayList(region: String, map: String){
//        targetDataArrayList.value =
//            FakerData.regionList.filter { it.regionName == region && it.mapName == map }
//                .map {
//                    TargetData(
//                        it.regionName,
//                        it.regionNumber,
//                        it.mapName,
//                        it.mapNumber,
//                        it.storageName,
//                        it.storageNumber,
//                        it.targetCoordinateX,
//                        it.targetCoordinateY,
//                        it.targetType,
//                        it.targetTypeNum
//                    )
//            } as ArrayList<TargetData>
    }
}
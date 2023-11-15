package com.lhr.water.ui.map

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lhr.water.model.FakerData
import com.lhr.water.model.TargetData
import com.lhr.water.ui.base.APP

class MapViewModel(context: Context): AndroidViewModel(context.applicationContext as APP) {

    var targetDataArrayList = MutableLiveData<ArrayList<TargetData>>()
    companion object {
    }

    fun setTargetDataArrayList(region: String, map: String){
        targetDataArrayList.value =
            FakerData.regionList.filter { it.regionName == region && it.mapName == map }
                .map {
                    TargetData(
                        it.regionName,
                        it.regionNumber,
                        it.mapName,
                        it.mapNumber,
                        it.storageName,
                        it.storageNumber,
                        it.targetCoordinateX,
                        it.targetCoordinateY,
                        it.targetType,
                        it.targetTypeNum
                    )
            } as ArrayList<TargetData>
    }
}
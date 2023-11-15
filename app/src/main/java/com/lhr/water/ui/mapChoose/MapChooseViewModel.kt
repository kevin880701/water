package com.lhr.water.ui.mapChoose

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lhr.water.model.FakerData
import com.lhr.water.ui.base.APP

class MapChooseViewModel(context: Context) :
    AndroidViewModel(context.applicationContext as APP) {

    var regionList: MutableLiveData<ArrayList<String>> =
        MutableLiveData<ArrayList<String>>().apply {
            postValue(FakerData.regionList.map { it.regionName }.distinct() as ArrayList<String>)
        }

    var mapList = MutableLiveData<ArrayList<String>>()
    fun setMapList(region: String) {
        mapList.value = FakerData.regionList.filter { it.regionName == region }
            .map { it.mapName }.distinct() as ArrayList<String>
    }
}
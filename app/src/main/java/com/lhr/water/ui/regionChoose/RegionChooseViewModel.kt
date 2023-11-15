package com.lhr.water.ui.regionChoose

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lhr.water.model.FakerData
import com.lhr.water.ui.base.APP

class RegionChooseViewModel(context: Context): AndroidViewModel(context.applicationContext as APP) {

    var regionList: MutableLiveData<ArrayList<String>> =
        MutableLiveData<ArrayList<String>>().apply { postValue(FakerData.regionList.map { it.regionName }.distinct() as ArrayList<String>) }

}
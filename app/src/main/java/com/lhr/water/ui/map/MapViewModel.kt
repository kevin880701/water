package com.lhr.water.ui.map

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lhr.water.data.MapRepository
import com.lhr.water.ui.base.APP

class MapViewModel(context: Context, private val mapRepository: MapRepository): AndroidViewModel(context.applicationContext as APP) {

    companion object {
        var currentCategoryIds: MutableLiveData<String> =
            MutableLiveData<String>().apply { postValue("") }
    }
    var title: MutableLiveData<String> = MutableLiveData<String>().apply { value = "Water" }
    var userName: MutableLiveData<String> = MutableLiveData<String>().apply { value = "Hi XXXXX" }

}
package com.lhr.water.ui.main

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lhr.water.ui.base.APP

class MainViewModel(context: Context): AndroidViewModel(context.applicationContext as APP) {

    companion object {
        var currentCategoryIds: MutableLiveData<String> =
            MutableLiveData<String>().apply { postValue("") }
    }
    var title: MutableLiveData<String> = MutableLiveData<String>().apply { value = "Water" }
    var userName: MutableLiveData<String> = MutableLiveData<String>().apply { value = "Hi XXXXX" }

}
package com.lhr.water.ui.main

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lhr.water.repository.FormRepository
import com.lhr.water.ui.base.APP

class MainViewModel(context: Context, formRepository: FormRepository): AndroidViewModel(context.applicationContext as APP) {

    val formRepository = formRepository
    companion object {
        var currentCategoryIds: MutableLiveData<String> =
            MutableLiveData<String>().apply { postValue("") }
    }
    var title: MutableLiveData<String> = MutableLiveData<String>().apply { value = "Water" }
    var userName: MutableLiveData<String> = MutableLiveData<String>().apply { value = "Hi XXXXX" }

    fun updateFormList(){
        formRepository.loadRecord()
    }
}
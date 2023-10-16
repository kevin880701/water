package com.lhr.water.ui.form

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.lhr.water.model.FormData

class FormViewModel(application: Application) : AndroidViewModel(application) {

//    val forms: MutableLiveData<String> = MutableLiveData<String>().apply { value = "" }
    lateinit var forms: MutableLiveData<List<FormData>>

    companion object {
        var currentCategoryIds: MutableLiveData<String> =
            MutableLiveData<String>().apply { postValue("") }
    }
    var title: MutableLiveData<String> = MutableLiveData<String>().apply { value = "" }
    init{
    }

}
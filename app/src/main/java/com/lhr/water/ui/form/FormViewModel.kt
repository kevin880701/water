package com.lhr.water.ui.form

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.lhr.water.data.FormRepository
import com.lhr.water.model.FormData
import com.lhr.water.ui.base.APP

class FormViewModel(context: Context, private val formRepository: FormRepository): AndroidViewModel(context.applicationContext as APP) {

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
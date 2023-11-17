package com.lhr.water.ui.history

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lhr.water.R
import com.lhr.water.model.FakerData
import com.lhr.water.model.TargetData
import com.lhr.water.ui.base.APP

class HistoryViewModel(context: Context): AndroidViewModel(context.applicationContext as APP) {

    var filterList = MutableLiveData<ArrayList<String>>()
    companion object {
    }

    init {
        filterList.value = ArrayList(context.resources.getStringArray(R.array.form_array).toList())
    }
}
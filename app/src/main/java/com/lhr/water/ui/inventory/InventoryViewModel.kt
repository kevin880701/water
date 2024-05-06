package com.lhr.water.ui.inventory

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lhr.water.repository.FormRepository
import com.lhr.water.ui.base.APP

class InventoryViewModel(
    context: Context,
    formRepository: FormRepository
) : AndroidViewModel(context.applicationContext as APP) {
    var formRepository = formRepository

    // 篩選materialName的String
    var searchMaterialName = MutableLiveData<String>()


}
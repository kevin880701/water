package com.lhr.water.ui.storageGoodInput

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lhr.water.data.WaitDealGoodsData
import com.lhr.water.repository.FormRepository
import com.lhr.water.ui.base.APP

class StorageGoodInputViewModel(context: Context, formRepository: FormRepository): AndroidViewModel(context.applicationContext as APP) {
    var formRepository = formRepository

    companion object {
    }

    fun getWaitInputGoods(): ArrayList<WaitDealGoodsData> {
        return formRepository.waitInputGoods.value!!
    }
}
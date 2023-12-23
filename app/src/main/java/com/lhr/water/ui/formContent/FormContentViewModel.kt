package com.lhr.water.ui.formContent

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.lhr.water.repository.FormRepository
import com.lhr.water.room.StorageContentEntity
import com.lhr.water.ui.base.APP

class FormContentViewModel(context: Context, formRepository: FormRepository): AndroidViewModel(context.applicationContext as APP) {
    var context = context
    val formRepository = formRepository


    fun getStorageGoods(): ArrayList<StorageContentEntity> {
        return formRepository.storageGoods.value!!
    }

    fun getTempWaitInputGoods(): ArrayList<StorageContentEntity> {
        return formRepository.tempWaitInputGoods.value!!
    }

}
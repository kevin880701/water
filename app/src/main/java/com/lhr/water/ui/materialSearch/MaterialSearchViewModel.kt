package com.lhr.water.ui.materialSearch

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.lhr.water.repository.FormRepository
import com.lhr.water.ui.base.APP

class MaterialSearchViewModel(
    context: Context,
    val formRepository: FormRepository
) : AndroidViewModel(context.applicationContext as APP) {


    companion object {
    }

    init {
    }

}
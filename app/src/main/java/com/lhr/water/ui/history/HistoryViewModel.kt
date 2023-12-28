package com.lhr.water.ui.history

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.lhr.water.repository.FormRepository
import com.lhr.water.repository.RegionRepository
import com.lhr.water.ui.base.APP

class HistoryViewModel(
    context: Context,
    regionRepository: RegionRepository,
    formRepository: FormRepository
) : AndroidViewModel(context.applicationContext as APP) {

    val regionRepository = regionRepository
    val formRepository = formRepository

    companion object {
    }

    init {
    }

    fun filterWaitOutputGoods(
        targetReportTitle: String,
        targetFormNumber: String
    ) = formRepository.filterWaitInputGoods(
        formRepository.waitInputGoods.value!!,
        targetReportTitle,
        targetFormNumber
    )

    fun filterTempWaitInputGoods(
        targetReportTitle: String,
        targetFormNumber: String) = formRepository.filterTempWaitInputGoods(
        formRepository.tempWaitInputGoods.value!!,
        targetReportTitle,
        targetFormNumber)
}
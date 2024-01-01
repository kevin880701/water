package com.lhr.water.ui.materialSearch

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.lhr.water.data.MapDetail
import com.lhr.water.data.RegionInformation
import com.lhr.water.data.StorageDetail
import com.lhr.water.data.WaitDealGoodsData
import com.lhr.water.repository.FormRepository
import com.lhr.water.repository.RegionRepository
import com.lhr.water.room.StorageRecordEntity
import com.lhr.water.ui.base.APP
import com.lhr.water.util.getCurrentDate
import org.json.JSONObject
import timber.log.Timber

class MaterialSearchViewModel(
    context: Context,
    val formRepository: FormRepository
) : AndroidViewModel(context.applicationContext as APP) {


    companion object {
    }

    init {
    }

}
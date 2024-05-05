package com.lhr.water.ui.cover

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.lhr.water.repository.RegionRepository
import com.lhr.water.ui.base.APP

class CoverViewModel(context: Context, regionRepository: RegionRepository): AndroidViewModel(context.applicationContext as APP) {

    var regionRepository = regionRepository

}
package com.lhr.water.ui.cover

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lhr.water.repository.RegionRepository
import com.lhr.water.ui.base.APP

class CoverViewModel(context: Context, regionRepository: RegionRepository): AndroidViewModel(context.applicationContext as APP) {

    var regionRepository = regionRepository

    fun checkTable(){
        regionRepository.checkRegionExist()
    }
}
package com.lhr.water.ui.login

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.lhr.water.data.RegionInformation
import com.lhr.water.repository.RegionRepository
import com.lhr.water.ui.base.APP

class LoginViewModel(context: Context, regionRepository: RegionRepository): AndroidViewModel(context.applicationContext as APP) {

    var regionRepository = regionRepository
    fun getRegionNameList(): ArrayList<String>{
        return regionRepository.getRegionNameList(regionRepository.regionEntities.value!!)
    }

    fun getMapNameList(regionName: String): ArrayList<String> {
        return regionRepository.getMapNameList(regionName, regionRepository.mapEntities.value!!)
    }

}
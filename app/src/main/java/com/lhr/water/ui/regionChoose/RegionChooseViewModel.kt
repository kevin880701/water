package com.lhr.water.ui.regionChoose

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lhr.water.repository.RegionRepository
import com.lhr.water.ui.base.APP
import com.lhr.water.util.MapPageStatus

class RegionChooseViewModel(context: Context, regionRepository: RegionRepository) :
    AndroidViewModel(context.applicationContext as APP) {
    var regionRepository = regionRepository

    // 頁面狀態
    var pageStatus = MutableLiveData<String>()

    // 頁面當前列表
    var currentList = MutableLiveData<ArrayList<String>>()

    init {
        pageStatus.postValue(MapPageStatus.RegionPage)
        currentList.postValue(regionRepository.getRegionNameList(regionRepository.storageInformationList))
    }


    /**
     * 切換列表
     * @param region 地區名稱。有值要要求地圖列表，如果為null代表要求區域列表
     */
    fun changeList(region: String? = null) {
        currentList.postValue(region?.let {
            regionRepository.getMapNameList(it, regionRepository.storageInformationList) }
            ?: run {
                regionRepository.getRegionNameList(regionRepository.storageInformationList) })
    }
}
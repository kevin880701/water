package com.lhr.water.ui.regionChoose

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lhr.water.data.repository.RegionRepository
import com.lhr.water.ui.base.APP
import com.lhr.water.util.MapPageStatus

class RegionChooseViewModel(context: Context, regionRepository: RegionRepository) :
    AndroidViewModel(context.applicationContext as APP) {
    var regionRepository = regionRepository

//    var regionList: MutableLiveData<ArrayList<String>> =
//        MutableLiveData<ArrayList<String>>().apply { postValue(FakerData.regionList.map { it.regionName }.distinct() as ArrayList<String>) }

    // 頁面狀態
    var pageStatus = MutableLiveData<String>()

    // 頁面當前列表
    var currentList = MutableLiveData<ArrayList<String>>()

    init {
        pageStatus.postValue(MapPageStatus.RegionPage)
        currentList.postValue(regionRepository.getRegionNameList())
    }


    /**
     * 切換列表
     * @param region 地區名稱。有值要要求地圖列表，如果為null代表要求區域列表
     */
    fun changeList(region: String? = null) {
        currentList.postValue(region?.let {
            regionRepository.getMapNameList(it) }
            ?: run {
                regionRepository.getRegionNameList() })
    }
}
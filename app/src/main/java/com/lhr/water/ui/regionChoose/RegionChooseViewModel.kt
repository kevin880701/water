package com.lhr.water.ui.regionChoose

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lhr.water.data.MapData
import com.lhr.water.repository.RegionRepository
import com.lhr.water.repository.UserRepository
import com.lhr.water.room.RegionEntity
import com.lhr.water.ui.base.APP
import com.lhr.water.util.MapDataList

enum class SelectStatus {
    RegionPage,
    DeptPage,
}

class RegionChooseViewModel(context: Context, regionRepository: RegionRepository,var userRepository: UserRepository) :
    AndroidViewModel(context.applicationContext as APP) {
    var regionRepository = regionRepository

    // 頁面狀態
    var pageStatus = MutableLiveData<SelectStatus>()
    // 頁面當前列表
    var regionList = MutableLiveData<ArrayList<RegionEntity>>()
    // 區域篩選列表，之後會根據登入的人來決定顯示哪些區域
    var regionFilterList =  ArrayList<String>()
    var selectRegion = MutableLiveData<String>()

    init {
        pageStatus.postValue(SelectStatus.RegionPage)
    }


    fun createRegionList() {
        if (userRepository.userData.deptAno.length >= 2) {
            regionFilterList.add(userRepository.userData.deptAno.substring(0, 2))
        }

        val tempList = regionRepository.regionEntities.filter { mapData ->
            regionFilterList.any { filterString ->
                mapData.regionNumber.contains(filterString)
            }
        }.distinctBy { it.regionName }.toMutableList() as ArrayList<RegionEntity>

        regionList.postValue(tempList)
    }
}
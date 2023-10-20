package com.lhr.water.data

import com.lhr.water.model.FakerData

class RegionRepository {
    fun getRegionList(): ArrayList<String> {
        return FakerData.regionList.map { it.regionName }.distinct() as ArrayList<String>
    }
}
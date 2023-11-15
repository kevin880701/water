package com.lhr.water.data.Repository

import com.lhr.water.model.FakerData
import com.lhr.water.room.TargetDao

class RegionRepository private constructor(private val targetDao: TargetDao) {

    companion object{
        private var instance: RegionRepository?=null
        fun getInstance(targetDao: TargetDao): RegionRepository {
            if (instance ==null) {
                instance = RegionRepository(targetDao)
            }
            return instance!!
        }
    }

    fun getRegionList(): ArrayList<String> {
        return FakerData.regionList.map { it.regionName }.distinct() as ArrayList<String>
    }
}
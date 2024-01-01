package com.lhr.water.data


data class RegionInformation(
    val RegionName: String,
    val MapDetail: List<MapDetail>
)

data class MapDetail(
    val MapName: String,
    val StorageDetail: List<StorageDetail>
)

data class StorageDetail(
    val StorageNum: String,
    val StorageName: String,
    val StorageX: String,
    val StorageY: String
)
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

//data class StorageItemDetail(
//    val reportId: String,
//    val materialNumber: String,
//    val materialName: String,
//    val materialSpec: String,
//    val materialUnit: String,
//    val quantity: String,
//    val price: String,
//    val inputDate: String
//)
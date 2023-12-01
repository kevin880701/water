package com.lhr.water.data

import org.json.JSONObject

data class WaitDealGoodsData(
    val reportTitle: String,
    val reportId: String,
    val itemInformation: JSONObject,
)
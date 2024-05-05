package com.lhr.water.ui.inventory

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lhr.water.data.WaitDealGoodsData
import com.lhr.water.repository.FormRepository
import com.lhr.water.repository.RegionRepository
import com.lhr.water.room.MapEntity
import com.lhr.water.room.RegionEntity
import com.lhr.water.room.StorageContentEntity
import com.lhr.water.room.StorageEntity
import com.lhr.water.room.StorageRecordEntity
import com.lhr.water.ui.base.APP
import com.lhr.water.util.getCurrentDate
import com.lhr.water.util.manager.checkInventoryJson
import com.lhr.water.util.manager.checkJson
import com.lhr.water.util.manager.jsonAddInformation
import com.lhr.water.util.manager.jsonStringToJsonArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class InventoryViewModel(
    context: Context,
    formRepository: FormRepository
) : AndroidViewModel(context.applicationContext as APP) {
    var formRepository = formRepository

    // 篩選materialName的String
    var searchMaterialName = MutableLiveData<String>()


}
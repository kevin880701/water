package com.lhr.water.ui.inventory

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
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

    /**
     * 更新表單資料
     * @param context
     * @param fileUri json檔位址
     */
    fun  updateFormData(context: Context, fileUri: Uri){
        val inputStream: InputStream? =
            context.contentResolver.openInputStream(fileUri)
        var jsonContent = readJsonFromInputStream(inputStream)
        var jsonArray = jsonStringToJsonArray(jsonContent)
        jsonArray = jsonAddInformation(jsonArray)
        if(checkInventoryJson(jsonArray, context)){
            formRepository.insertInventoryForm(jsonArray)
            formRepository.loadInventoryForm()
        }
    }

    /**
     * 讀取JSON檔案
     * @param inputStream 要被讀取的內容
     */
    fun readJsonFromInputStream(inputStream: InputStream?): String {
        val stringBuilder = StringBuilder()
        try {
            val reader = BufferedReader(InputStreamReader(inputStream))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
        } catch (e: IOException) {
            Log.e("MainActivity", "Error reading JSON file", e)
        } finally {
            try {
                inputStream?.close()
            } catch (e: IOException) {
                Log.e("MainActivity", "Error closing InputStream", e)
            }
        }
        return stringBuilder.toString()
    }
}
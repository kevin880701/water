package com.lhr.water.ui.setting

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.AndroidViewModel
import com.lhr.water.data.repository.FormRepository
import com.lhr.water.ui.base.APP
import org.json.JSONArray
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SettingViewModel(context: Context, formRepository: FormRepository): AndroidViewModel(context.applicationContext as APP) {
    var formRepository = formRepository

    /**
     * 寫入JSON檔案到指定資料夾
     * @param context 要被讀取的內容
     * @param folderUri 指定資料夾位置
     */
    fun writeJsonObjectToFolder(context: Context, folderUri: Uri) {
        try {
            val jsonObject = JSONArray(formRepository.formRecordList.value)
            val folder = DocumentFile.fromTreeUri(context, folderUri)
            // 當前日期時間
            val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
            val currentDate = dateFormat.format(Date())
            // 儲存的檔案名稱
            val file = folder?.createFile("application/json", "output_${currentDate}.json")

            file?.let {
                val outputStream = context.contentResolver.openOutputStream(it.uri)
                outputStream?.use { stream ->
                    stream.write(jsonObject.toString().toByteArray())
                }

                Log.d("MainActivity", "JSONObject written to file: ${it.uri}")
            }
        } catch (e: IOException) {
            Log.e("MainActivity", "Error writing JSONObject to file", e)
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
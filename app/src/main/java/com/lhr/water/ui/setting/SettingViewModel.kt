package com.lhr.water.ui.setting

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.AndroidViewModel
import com.google.gson.Gson
import com.lhr.water.data.form.DeliveryForm
import com.lhr.water.data.form.ReceiveForm
import com.lhr.water.data.form.ReturnForm
import com.lhr.water.data.form.TransferForm
import com.lhr.water.network.ApiManager
import com.lhr.water.network.Execute
import com.lhr.water.network.data.UpdateData
import com.lhr.water.network.data.request.DataList
import com.lhr.water.network.data.request.UpdateDataRequest
import com.lhr.water.network.data.response.UpdateDataResponse
import com.lhr.water.repository.FormRepository
import com.lhr.water.repository.RegionRepository
import com.lhr.water.repository.UserRepository
import com.lhr.water.room.FormEntity.Companion.convertFormToFormEntities
import com.lhr.water.room.SqlDatabase
import com.lhr.water.ui.base.APP
import com.lhr.water.util.manager.checkJson
import com.lhr.water.util.manager.jsonAddInformation
import com.lhr.water.util.manager.jsonStringToJsonArray
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import timber.log.Timber
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SettingViewModel(
    context: Context, var formRepository: FormRepository,
    var regionRepository: RegionRepository,
    var userRepository: UserRepository
) :
    AndroidViewModel(context.applicationContext as APP) {
    var sqlDatabase = SqlDatabase.getInstance()

    /**
     * 寫入JSON檔案到指定資料夾
     * @param context 要被讀取的內容
     * @param folderUri 指定資料夾位置
     */
//    fun writeJsonObjectToFolder(context: Context, folderUri: Uri) {
//        try {
//            val jsonObject = JSONArray(formRepository.formRecordList.value)
//            val folder = DocumentFile.fromTreeUri(context, folderUri)
//            // 當前日期時間
//            val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
//            val currentDate = dateFormat.format(Date())
//            // 儲存的檔案名稱
//            val file = folder?.createFile("application/json", "output_${currentDate}.json")
//
//            file?.let {
//                val outputStream = context.contentResolver.openOutputStream(it.uri)
//                outputStream?.use { stream ->
//                    stream.write(jsonObject.toString().toByteArray())
//                }
//
//                Log.d("MainActivity", "JSONObject written to file: ${it.uri}")
//            }
//        } catch (e: IOException) {
//            Log.e("MainActivity", "Error writing JSONObject to file", e)
//        }
//    }


    /**
     * 更新表單資料
     * @param context
     * @param fileUri json檔位址
     */
//    fun updateFormData(context: Context, fileUri: Uri){
//        val inputStream: InputStream? =
//            context.contentResolver.openInputStream(fileUri)
//        var jsonContent = readJsonFromInputStream(inputStream)
//        var jsonArray = jsonStringToJsonArray(jsonContent)
//        jsonArray = jsonAddInformation(jsonArray)
//        if(checkJson(jsonArray, context)){
//            formRepository.insertNewForm(jsonArray)
//            formRepository.loadRecord()
//        }
//    }


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


    fun updatePda() {
        println("##############################################################")
        ApiManager().getDataList(userRepository.userInfo)
            .subscribeOn(Schedulers.io())
            .map { response ->

                formRepository.updateSqlData(
                    response.data.dataList.checkoutFormList,
                    response.data.dataList.storageRecordList,
                    response.data.dataList.deliveryFormList,
                    response.data.dataList.transferFormList,
                    response.data.dataList.receiveFormList,
                    response.data.dataList.returnFormList,
                    response.data.dataList.inventoryFormList
                )
                regionRepository.updateSqlData(response.data.dataList.storageList)
            }
            .subscribe({ response ->
                println("请求成功")
            }, { error ->
                println("请求失败：${error.message}")
            })
    }

    fun updateFormData(context: Context, jsonContent: String) {
        var jsonArray = jsonStringToJsonArray(jsonContent)
        jsonArray = jsonAddInformation(jsonArray)
        if (checkJson(jsonArray, context)) {
//            formRepository.insertNewForm(jsonArray)
//            formRepository.updateData()
        }
    }

    fun uploadFromPda() {
        try {
            val gson = Gson()

            val inventoryEntities = sqlDatabase.getInventoryDao().getAll()
            val storageRecordEntities = sqlDatabase.getStorageRecordDao().getAll()
            val formEntities = sqlDatabase.getFormDao().getAll()

            // 把formEntities轉成動應Class的List
            val deliveryFormList = ArrayList<DeliveryForm>()
            val receiveFormList = ArrayList<ReceiveForm>()
            val transferFormList = ArrayList<TransferForm>()
            val returnFormList = ArrayList<ReturnForm>()

            for (formEntity in formEntities) {
                val formContent = formEntity.formContent
                val reportTitle = formEntity.reportTitle

                when (reportTitle) {
                    "交貨通知單" -> {
                        val deliveryForm = gson.fromJson(formContent, DeliveryForm::class.java)
                        deliveryFormList.add(deliveryForm)
                    }
                    "材料領料單" -> {
                        val receiveForm = gson.fromJson(formContent, ReceiveForm::class.java)
                        receiveFormList.add(receiveForm)
                    }
                    "材料調撥單" -> {
                        val transferForm = gson.fromJson(formContent, TransferForm::class.java)
                        transferFormList.add(transferForm)
                    }
                    "材料退料單" -> {
                        val returnForm = gson.fromJson(formContent, ReturnForm::class.java)
                        returnFormList.add(returnForm)
                    }
                    else -> {
                    }
                }
            }

            val updateDataRequest = UpdateDataRequest(
                dataList = DataList(
                    deliveryList = deliveryFormList,
                    transferList = transferFormList,
                    receiveList = receiveFormList,
                    returnListList = returnFormList,
                    inventoryEntities = inventoryEntities,
                    storageRecordEntities = storageRecordEntities
                ),
                userInfo = userRepository.userInfo
            )

            sendData(updateDataRequest)

            // 更新 Form 表中的 isUpdate 為 true
            formEntities.forEach { formEntity ->
                formEntity.isUpdate = true
                sqlDatabase.getFormDao().update(formEntity)
            }

            // 更新 StorageRecord 表中的 isUpdate 為 true
            storageRecordEntities.forEach { storageRecordEntity ->
                storageRecordEntity.isUpdate = true
                sqlDatabase.getStorageRecordDao().update(storageRecordEntity)
            }

            // 更新 Inventory 表中的 isUpdate 為 true
            inventoryEntities.forEach { inventoryEntity ->
                inventoryEntity.isUpdate = true
                sqlDatabase.getInventoryDao().update(inventoryEntity)
            }
        } catch (e: IOException) {
            Log.e("MainActivity", "Error writing JSONObject to file", e)
        }
    }


    fun sendData(request: UpdateDataRequest) {
        ApiManager().updateFromPDA(request)
            .subscribeOn(Schedulers.io())
            .map { response ->

            }
            .subscribe({ response ->
                println(response.toString())
            }, { error ->
                println("sendData Failed：${error.message}")
            })
    }

    /**
     * 寫入JSON檔案到指定資料夾（本地）
     * @param context 要被讀取的內容
     * @param folderUri 指定資料夾位置
     */
    fun writeJsonObjectToFolderLocal(context: Context, folderUri: Uri) {
        try {
            val gson = Gson()

            val inventoryEntities = sqlDatabase.getInventoryDao().getAll()
            val storageRecordEntities = sqlDatabase.getStorageRecordDao().getAll()
            val formEntities = sqlDatabase.getFormDao().getAll()

            // 把formEntities轉成動應Class的List
            val deliveryFormList = ArrayList<DeliveryForm>()
            val receiveFormList = ArrayList<ReceiveForm>()
            val transferFormList = ArrayList<TransferForm>()
            val returnFormList = ArrayList<ReturnForm>()

            for (formEntity in formEntities) {
                val formContent = formEntity.formContent
                val reportTitle = formEntity.reportTitle

                when (reportTitle) {
                    "交貨通知單" -> {
                        val deliveryForm = gson.fromJson(formContent, DeliveryForm::class.java)
                        deliveryFormList.add(deliveryForm)
                    }
                    "材料領料單" -> {
                        val receiveForm = gson.fromJson(formContent, ReceiveForm::class.java)
                        receiveFormList.add(receiveForm)
                    }
                    "材料調撥單" -> {
                        val transferForm = gson.fromJson(formContent, TransferForm::class.java)
                        transferFormList.add(transferForm)
                    }
                    "材料退料單" -> {
                        val returnForm = gson.fromJson(formContent, ReturnForm::class.java)
                        returnFormList.add(returnForm)
                    }
                    else -> {
                    }
                }
            }

            val updateDataRequest = UpdateDataRequest(
                dataList = DataList(
                    deliveryList = deliveryFormList,
                    transferList = transferFormList,
                    receiveList = receiveFormList,
                    returnListList = returnFormList,
                    inventoryEntities = inventoryEntities,
                    storageRecordEntities = storageRecordEntities
                ),
                userInfo = userRepository.userInfo
            )

            val jsonString = gson.toJson(updateDataRequest)

            val folder = DocumentFile.fromTreeUri(context, folderUri)
            // 當前日期時間
            val dateFormat = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault())
            val currentDate = dateFormat.format(Date())
            // 儲存的檔案名稱
            val file = folder?.createFile("application/json", "output_${currentDate}.json")

            val outputStream: OutputStream? = context.contentResolver.openOutputStream(file!!.uri)
            outputStream?.use { stream ->
                stream.write(jsonString.toByteArray())
            }

            // 更新 Form 表中的 isUpdate 為 true
            formEntities.forEach { formEntity ->
                formEntity.isUpdate = true
                sqlDatabase.getFormDao().update(formEntity)
            }

            // 更新 StorageRecord 表中的 isUpdate 為 true
            storageRecordEntities.forEach { storageRecordEntity ->
                storageRecordEntity.isUpdate = true
                sqlDatabase.getStorageRecordDao().update(storageRecordEntity)
            }

            // 更新 Inventory 表中的 isUpdate 為 true
            inventoryEntities.forEach { inventoryEntity ->
                inventoryEntity.isUpdate = true
                sqlDatabase.getInventoryDao().update(inventoryEntity)
            }
        } catch (e: IOException) {
            Log.e("MainActivity", "Error writing JSONObject to file", e)
        }
    }


    /**
     * 更新表單資料（本地）
     * @param context
     * @param fileUri json檔位址
     */
    fun updateFormDataLocal(context: Context, fileUri: Uri) {
        val inputStream: InputStream? =
            context.contentResolver.openInputStream(fileUri)
        var jsonContent = readJsonFromInputStream(inputStream)
        var jsonArray = jsonStringToJsonArray(jsonContent)
        jsonArray = jsonAddInformation(jsonArray)
        if (checkJson(jsonArray, context)) {
//            formRepository.insertNewForm(jsonArray)
        }
    }

    // 判斷儲櫃紀錄StorageRecord、表單FormEntity、盤點表單InventoryEntity是否已經備份
    fun checkIsUpdate(): Boolean {
        val formDao = sqlDatabase.getFormDao()
        val storageRecordDao = sqlDatabase.getStorageRecordDao()
        val inventoryDao = sqlDatabase.getInventoryDao()

        val hasUnUpdatedRecord = listOf(
            formDao.getAll().isNotEmpty() && formDao.getAll().any { !it.isUpdate },
            storageRecordDao.getAll().isNotEmpty() && storageRecordDao.getAll().any { !it.isUpdate },
            inventoryDao.getAll().isNotEmpty() && inventoryDao.getAll().any { !it.isUpdate }
        )

        return !hasUnUpdatedRecord.contains(true)
    }

    fun updateTest(json: String) {
        val gson = Gson()
        val updateDataResponse: UpdateDataResponse =
            gson.fromJson(json, UpdateDataResponse::class.java)

        // 先清除資料表
        sqlDatabase.getFormDao().clearTable()
        sqlDatabase.getCheckoutDao().clearTable()
        sqlDatabase.getStorageDao().clearTable()
        sqlDatabase.getStorageRecordDao().clearTable()
        sqlDatabase.getInventoryDao().clearTable()

        // 將updateDataResponse的儲櫃資訊、儲櫃紀錄、月結表插入資料表
        sqlDatabase.getCheckoutDao()
            .insertCheckoutEntities(updateDataResponse.updateData.dataList.checkoutFormList)
        sqlDatabase.getStorageDao()
            .insertStorageEntities(updateDataResponse.updateData.dataList.storageList)
        sqlDatabase.getStorageRecordDao()
            .insertStorageRecordEntities(updateDataResponse.updateData.dataList.storageRecordList)

        //將交貨、領料、退料、調撥轉成FormEntity格式
        val deliveryFormEntities =
            convertFormToFormEntities(updateDataResponse.updateData.dataList.deliveryFormList)
        val transferFormEntities =
            convertFormToFormEntities(updateDataResponse.updateData.dataList.transferFormList)
        val receiveFormEntities =
            convertFormToFormEntities(updateDataResponse.updateData.dataList.receiveFormList)
        val returnFormEntities =
            convertFormToFormEntities(updateDataResponse.updateData.dataList.returnFormList)

        // 插入到資料表中
        sqlDatabase.getFormDao().insertFormEntities(deliveryFormEntities)
        sqlDatabase.getFormDao().insertFormEntities(transferFormEntities)
        sqlDatabase.getFormDao().insertFormEntities(receiveFormEntities)
        sqlDatabase.getFormDao().insertFormEntities(returnFormEntities)


        // 插入到資料表中
        sqlDatabase.getInventoryDao()
            .insertInventoryEntities(updateDataResponse.updateData.dataList.inventoryFormList)

        // 更新資料
        formRepository.loadSqlData()
        regionRepository.loadSqlData()

    }
}
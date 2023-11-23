package com.lhr.water.data.Repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.lhr.water.R
import com.lhr.water.data.StorageDetail
import com.lhr.water.data.RegionInformation
import java.io.InputStreamReader

class RegionRepository private constructor(private val context: Context) {
// 從string.xml提取區域列表
//    var regionList: MutableLiveData<ArrayList<String>> =
//        MutableLiveData<ArrayList<String>>().apply { ArrayList(context.resources.getStringArray(R.array.region_array).toList())}

    var mapList: MutableLiveData<ArrayList<String>> =
        MutableLiveData<ArrayList<String>>().apply {
            ArrayList(
                context.resources.getStringArray(R.array.region_array).toList()
            )
        }

    var storageInformationList: ArrayList<RegionInformation>

    //    var regionList: ArrayList<String>
    companion object {
        private var instance: RegionRepository? = null
        fun getInstance(context: Context): RegionRepository {
            if (instance == null) {
                instance = RegionRepository(context)
            }
            return instance!!
        }
    }

    init {
        storageInformationList = getStorageInformation()
//        regionList = getRegionNameList()
    }

//    fun getRegionList(): ArrayList<String> {
//        return FakerData.regionList.map { it.regionName }.distinct() as ArrayList<String>
//    }

    /**
     * 從 assets 中獲取"StorageInformation.json"
     */
    private fun getStorageInformation(): ArrayList<RegionInformation> {
        return try {
//             從 assets 中獲取 InputStream
            val inputStream = context.assets.open("StorageInformation.json")

//             使用 Gson 解析 JSON 数据
            val type = object : TypeToken<List<RegionInformation>>() {}.type
            var storageInformation: List<RegionInformation> =
                Gson().fromJson(InputStreamReader(inputStream), type)

//            val inputStream = context.assets.open("StorageInformation.json")
//            val size = inputStream.available()
//            val buffer = ByteArray(size)
//            inputStream.read(buffer)
//            inputStream.close()
//            val gson = Gson()
//            val type = object : TypeToken<List<StorageInformation>>() {}.type
//            val storageInformation: List<StorageInformation> = gson.fromJson( String(buffer, Charsets.UTF_8), type)
            // 關閉 InputStream
            inputStream.close()

            storageInformation as ArrayList<RegionInformation>
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            Log.e("JsonSyntaxException", "Error parsing JSON: ${e.message}")
            ArrayList()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("JsonSyntaxException", "Error parsing JSON: ${e.message}")
            ArrayList()
        }
    }

    /**
     * 區域列表
     */
    fun getRegionNameList(): ArrayList<String> {
        return storageInformationList.map { it.RegionName } as ArrayList<String>

    }


    /**
     * 根據區域名稱列出地圖列表
     * @param regionName 區域名稱
     */
    fun getMapNameList(regionName: String): ArrayList<String> {
        // 查找 StorageInformation 中指定的 regionName
        val regionStorageInformation = storageInformationList.find { it.RegionName == regionName }
        // 提取該 regionName 下的 mapName 列表
        return regionStorageInformation?.MapDetail?.map { it.MapName } as ArrayList<String>
    }


    /**
     * 根據區域名稱和地圖名稱列出櫥櫃列表
     * @param regionName 區域名稱
     * @param mapName 地圖名稱
     */
    fun getStorageDetailList(regionName: String, mapName: String): ArrayList<StorageDetail> {
        // 查找 StorageInformation 中指定 regionName
        val regionStorageInformation =
            storageInformationList.find { it.RegionName == regionName }

        // 查找指定 mapName 的 StorageDetail
        val mapStorageDetail =
            regionStorageInformation?.MapDetail?.find { it.MapName == mapName }

        // 提取該 mapName 下的 ItemDetail 列表
        return mapStorageDetail?.StorageDetail as ArrayList<StorageDetail>
    }
}
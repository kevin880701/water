package com.lhr.water.ui.cover

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.lhr.water.room.SqlDatabase
import com.lhr.water.R
import com.lhr.water.databinding.ActivityCoverBinding
import com.lhr.water.model.Model
import com.lhr.water.ui.base.APP
import com.lhr.water.ui.base.BaseActivity
import com.lhr.water.ui.login.LoginActivity
import com.lhr.water.ui.login.LoginViewModel
import com.lhr.water.ui.main.MainActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CoverActivity : BaseActivity() {

    private val viewModel: LoginViewModel by viewModels{(applicationContext as APP).appContainer.viewModelFactory}

    private var _binding: ActivityCoverBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCoverBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ResourcesCompat.getColor(resources, R.color.seed, null)
        Log.v("AAAAAAAAAA","" + getExternalFilesDir("download"))
        Log.v("AAAAAAAAAA","" + getExternalFilesDir("upload"))

        // 測試創建文件
        val contentValues = ContentValues().apply {
            put(MediaStore.Downloads.DISPLAY_NAME, "example_file.txt")
            put(MediaStore.Downloads.MIME_TYPE, "text/plain")
            put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
        }

        val contentUri = MediaStore.Downloads.EXTERNAL_CONTENT_URI

        val resolver = contentResolver
        val fileUri = resolver.insert(contentUri, contentValues)

        // 使用 OutputStream 将数据写入文件
        resolver.openOutputStream(fileUri!!).use { outputStream ->
            // 这里可以将文件数据写入 outputStream
            outputStream?.write("Hello, World!".toByteArray())
        }

        // 創建Model
        Model
        // 讀取資料並轉成LIST
//        var sqlManager = SqlDatabase(this)
        GlobalScope.launch {
//            allTargetEntityArrayList = sqlManager.getClassDao().getAll() as ArrayList<TargetEntity>
//            if(allTargetDataArrayList.size == 0){
//                regionNameArrayList = FakerData.targetEntities.map { it.regionName }.distinct() as ArrayList<String>
//                allTargetDataArrayList = FakerData.targetEntities
//            }else{
//                // 將 TargetEntity 轉換成 TargetData 並去掉 id 欄位
//                allTargetDataArrayList = allTargetEntityArrayList.map { targetEntity ->
//                    TargetData(
//                        targetRegion = targetEntity.targetRegion,
//                        targetRegionNum = targetEntity.targetRegionNum,
//                        targetName = targetEntity.targetName,
//                        targetNum = targetEntity.targetNum,
//                        targetCoordinateX = targetEntity.targetCoordinateX,
//                        targetCoordinateY = targetEntity.targetCoordinateY,
//                        targetType = targetEntity.targetType,
//                        targetTypeNum = targetEntity.targetTypeNum
//                    )
//                } as ArrayList<TargetData>
//                Model.regionNameArrayList = Model.allTargetEntityArrayList.map { it.targetRegion }.distinct() as ArrayList<String>
//            }

            val layout = findViewById<ConstraintLayout>(R.id.constrain)
            val animation = AlphaAnimation(0.0f, 1.0f)
            animation.fillAfter = true
            animation.duration = 1000
            layout.startAnimation(animation)
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {
                    println("動畫開始")
                }

                override fun onAnimationEnd(animation: Animation) {
                    println("動畫結束")

                    val intent = Intent(this@CoverActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                override fun onAnimationRepeat(animation: Animation) {
                    println("動畫重覆執行")
                }
            })
        }
    }

    fun createFolder(){
        // 创建 water 文件夹
        val waterFolder = createFolderInDownload("water")

        // 在 water 文件夹中创建 upload 和 update 文件夹
        val uploadFolder = createFolderInFolder(waterFolder, "upload")
        val updateFolder = createFolderInFolder(waterFolder, "update")

        // 在 upload 和 update 文件夹中创建文件
        createFileInFolder(uploadFolder, "upload_file.txt", "text/plain")
        createFileInFolder(updateFolder, "update_file.txt", "text/plain")
    }

    // 创建文件夹并返回 Uri
    private fun createFolderInDownload(folderName: String): Uri {
        val contentValues = ContentValues().apply {
            put(MediaStore.Downloads.DISPLAY_NAME, folderName)
            put(MediaStore.Downloads.MIME_TYPE, "application/vnd.android.package-archive")
            put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
        }

        val contentUri = MediaStore.Downloads.EXTERNAL_CONTENT_URI
        val resolver = contentResolver
        return resolver.insert(contentUri, contentValues)!!
    }


    // 在指定文件夹中创建文件并返回 Uri
    private fun createFileInFolder(parentFolderUri: Uri, fileName: String, mimeType: String): Uri {
        val contentValues = ContentValues().apply {
            put(MediaStore.Downloads.DISPLAY_NAME, fileName)
            put(MediaStore.Downloads.MIME_TYPE, mimeType)
            put(MediaStore.Downloads.RELATIVE_PATH, getFolderPath(parentFolderUri))
        }

        val contentUri = MediaStore.Downloads.EXTERNAL_CONTENT_URI
        val resolver = contentResolver
        return resolver.insert(contentUri, contentValues)!!
    }
    private fun createFolderInFolder(parentFolderUri: Uri, folderName: String): Uri {
        val contentValues = ContentValues().apply {
            put(MediaStore.Downloads.DISPLAY_NAME, folderName)
            put(MediaStore.Downloads.MIME_TYPE, "application/vnd.android.package-archive")
            put(MediaStore.Downloads.RELATIVE_PATH, getFolderPath(parentFolderUri))
        }

        val contentUri = MediaStore.Downloads.EXTERNAL_CONTENT_URI
        val resolver = contentResolver
        return resolver.insert(contentUri, contentValues)!!
    }
    // 获取文件夹路径
    private fun getFolderPath(folderUri: Uri): String {
        val projection = arrayOf(MediaStore.Downloads.RELATIVE_PATH)
        val cursor = contentResolver.query(folderUri, projection, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                return it.getString(it.getColumnIndex(MediaStore.Downloads.RELATIVE_PATH))
            }
        }
        return ""
    }
}
package com.lhr.water.ui.deepLink

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import com.lhr.water.R
import com.lhr.water.databinding.ActivityCoverBinding
import com.lhr.water.network.data.response.UserInfo
import com.lhr.water.ui.base.APP
import com.lhr.water.ui.base.BaseActivity
import com.lhr.water.util.SharedPreferencesHelper
import com.lhr.water.util.dialog.DefaultDialog
import timber.log.Timber

class DeepLinkActivity : BaseActivity() {
    private val viewModel: DeepLinkViewModel by viewModels{ (applicationContext as APP).appContainer.viewModelFactory }
    private var _binding: ActivityCoverBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityCoverBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ResourcesCompat.getColor(resources, R.color.primaryBlue, null)

        var userInfo = SharedPreferencesHelper.getUserInfo(this)
        if (userInfo != null) {
            viewModel.userRepository.userInfo.postValue(userInfo)
        } else {
            viewModel.userRepository.userInfo.postValue(
                UserInfo(
                    deptAno = "",
                    userId = ""
                )
            )
            println("No UserInfoData found")
        }

        // 從 Intent 獲取 URL
        val url = intent?.data?.toString()

        // 根據不同的 URL 路徑處理
        url?.let {
            handleUrl(it)
        }

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent) // 設置新的Intent為當前Activity的Intent
        // 從 Intent 獲取 URL
        val url = intent?.data?.toString()
        // 根據不同的 URL 路徑處理
        url?.let {
            handleUrl(it)
        }
    }


    @SuppressLint("SuspiciousIndentation")
    private fun handleUrl(url: String) {
        Timber.d("=======================================")
        Timber.d(url)
        Timber.d("=======================================")
        try {
            when {
                url.contains("https://pda-internal.water.gov.tw/auto-download") -> {
                    if (!viewModel.checkIsUpdate()) {
                        val defaultDialog = DefaultDialog(
                            title = "尚未備份",
                            text = "尚有未同步資料，是否直接覆蓋?",
                            confirmClick = {
                                viewModel.updatePdaData(viewModel.userRepository.userInfo.value!!)
//                                finish()
                            },
                            cancelClick = {
                                finish()
                            }
                        )
                        defaultDialog.show(supportFragmentManager, "DefaultDialog")
                    } else {
                        viewModel.updatePdaData(viewModel.userRepository.userInfo.value!!)
                        finish()
                    }
                }

                url.contains("https://pda-internal.water.gov.tw/auto-upload") -> {
                    viewModel.uploadPdaData()
                    finish()
                }
            }
        } catch (e: Exception) {
            // 處理其他異常
            Timber.tag("Exception").e(e.toString())
        }
    }

}
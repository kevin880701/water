package com.lhr.water.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import com.lhr.water.R
import com.lhr.water.data.upData
import com.lhr.water.databinding.ActivityLoginBinding
import com.lhr.water.network.data.response.UserInfo
import com.lhr.water.ui.base.APP
import com.lhr.water.ui.base.BaseActivity
import com.lhr.water.ui.deepLink.DeepLinkViewModel
import com.lhr.water.ui.main.MainActivity
import com.lhr.water.util.SharedPreferencesHelper
import com.lhr.water.util.adapter.SpinnerAdapter
import com.lhr.water.util.dialog.DefaultDialog
import com.lhr.water.util.showToast


class LoginActivity : BaseActivity(), View.OnClickListener {

    private val viewModel: LoginViewModel by viewModels { (applicationContext as APP).appContainer.viewModelFactory }
    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!
    var regionName = ""
    var mapName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ResourcesCompat.getColor(resources, R.color.primaryBlue, null)

        initView()
        bindViewModel()
//        viewModel.userRepository.userInfo.postValue(UserInfo(
//            deptAno = "",
//            userId = ""
//        ))
//
//        viewModel.getUserInfo().subscribe({ _ ->
//            viewModel.getDataList()
//        }, { error ->
//            viewModel.userRepository.userInfo.postValue(UserInfo(
//                deptAno = "",
//                userId = ""
//            ))
//        })

        val receivedIntent = intent
        if (receivedIntent.action == Intent.ACTION_SEND) {
            val message = receivedIntent.getStringExtra(Intent.EXTRA_TEXT)
            println("message : $message")
        }
    }


    private fun bindViewModel() {
        viewModel.userRepository.userInfo.observe(this) { userInfo ->
            binding.textDeptNo.text = userInfo.deptAno
            binding.textUserNo.text = userInfo.userId
        }
    }

    private fun initView() {
//        binding.linearReload.setOnClickListener(this)
        binding.buttonLogin.setOnClickListener(this)
        binding.textAutoDownloadTest.setOnClickListener(this)
        binding.textAutoUploadTest.setOnClickListener(this)
        binding.textGetUserInfo.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when (v?.id) {
//            R.id.linearReload -> {
//                viewModel.getUserInfo().subscribe({ _ ->
//                    viewModel.getDataList()
//                }, { error ->
//                    viewModel.userRepository.userInfo.postValue(UserInfo(
//                        deptAno = "",
//                        userId = ""
//                    ))
//                })
//            }
            R.id.textGetUserInfo -> {
                val testViewModel: DeepLinkViewModel by viewModels{ (applicationContext as APP).appContainer.viewModelFactory }
                if(testViewModel.userRepository.userInfo.value!!.deptAno == ""){
                    testViewModel.getUserInfo().subscribe({ getUserInfoResponse ->
                        println("請求成功")
                        showToast(this,"請求成功")
//                        updatePdaData(getUserInfoResponse)
                    }, { error ->
                        showToast(this,"請求失敗")
                        println("請求失敗：${error.message}")
                    })
                }else{
                    showToast(this,"已有UserInfo：${testViewModel.userRepository.userInfo.value!!.deptAno}")
                }
            }
            R.id.textAutoUploadTest -> {
                val testViewModel: DeepLinkViewModel by viewModels{ (applicationContext as APP).appContainer.viewModelFactory }
                var userInfo = SharedPreferencesHelper.getUserInfo(this)
                if (userInfo != null) {
                    testViewModel.userRepository.userInfo.postValue(userInfo)
                } else {
                    userInfo = UserInfo(
                        deptAno = "",
                        userId = ""
                    )

                    println("No UserInfoData found")
                }
                testViewModel.uploadPdaData(userInfo, viewModel.formRepository.isInventoryCompleted.value!!)
            }
            R.id.textAutoDownloadTest -> {
                val testViewModel: DeepLinkViewModel by viewModels{ (applicationContext as APP).appContainer.viewModelFactory }
                if (!testViewModel.checkIsUpdate()) {
                    val defaultDialog = DefaultDialog(
                        title = "尚未備份",
                        text = "尚有未同步資料，是否直接覆蓋?",
                        confirmClick = {
                            testViewModel.updatePdaData(testViewModel.userRepository.userInfo.value!!)
                            finish()
                        },
                        cancelClick = {
                            finish()
                        }
                    )
                    defaultDialog.show(supportFragmentManager, "DefaultDialog")
                } else {
                    testViewModel.updatePdaData(testViewModel.userRepository.userInfo.value!!)
                    finish()
                }
            }
            R.id.buttonLogin -> {
                if(viewModel.userRepository.userInfo.value!!.userId != "") {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }else{
                    val defaultDialog = DefaultDialog(
                        title = "未取得登入資訊",
                        text = "請重新取得用戶資訊",
                    )
                    defaultDialog.show(supportFragmentManager, "DefaultDialog")
                }
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                finish()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}
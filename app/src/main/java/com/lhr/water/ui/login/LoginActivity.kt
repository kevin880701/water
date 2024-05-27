package com.lhr.water.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import com.lhr.water.R
import com.lhr.water.databinding.ActivityLoginBinding
import com.lhr.water.ui.base.APP
import com.lhr.water.ui.base.BaseActivity
import com.lhr.water.ui.main.MainActivity
import com.lhr.water.util.adapter.SpinnerAdapter


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



        // 检查是否接收到 Intent
        println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
        val receivedIntent = intent
        if (receivedIntent.action == Intent.ACTION_SEND) {
            println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
            // 如果收到的 Intent 是 SEND 动作，则从 Intent 中提取字符串消息
            val message = receivedIntent.getStringExtra(Intent.EXTRA_TEXT)
            // 将消息显示在 TextView 中
            println("message : $message")
        }
        initView()
    }

    private fun initView() {
//        initSpinner(binding.spinnerRegion, viewModel.getRegionNameList())
//        initSpinner(binding.spinnerMap, viewModel.getMapNameList(binding.spinnerRegion.selectedItem.toString()))
//
//        // 设置 Spinner 的选择监听器
//        binding.spinnerRegion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                // 通过 position 获取当前选定项的文字
//                mapName = parent?.getItemAtPosition(position).toString()
//                initSpinner(binding.spinnerMap, viewModel.getMapNameList(mapName))
//
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//                // 在没有选中项的情况下触发
//            }
//        }
//
//        // 设置 Spinner 的选择监听器
//        binding.spinnerMap.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                // 通过 position 获取当前选定项的文字
//                regionName = parent?.getItemAtPosition(position).toString()
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//                // 在没有选中项的情况下触发
//            }
//        }
        binding.buttonLogin.setOnClickListener(this)
    }

    private fun initSpinner(spinner: Spinner, spinnerData: ArrayList<String>){
        val adapter = SpinnerAdapter(this, android.R.layout.simple_spinner_item, spinnerData)
        spinner.adapter = adapter
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonLogin -> {
//                LoginData.region = binding.spinnerRegion.selectedItem.toString()
//                LoginData.map = binding.spinnerMap.selectedItem.toString()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
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
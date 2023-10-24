package com.lhr.water.ui.formContent

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.transition.Slide
import android.transition.Transition
import android.transition.TransitionManager
import android.view.Gravity
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.lhr.water.R
import com.lhr.water.databinding.ActivityFormContentBinding
import com.lhr.water.manager.DialogManager.showBottomSheet
import com.lhr.water.model.FormData
import com.lhr.water.ui.base.APP
import com.lhr.water.ui.base.BaseActivity
import com.lhr.water.util.widget.FormGoodsAdd
import com.lhr.water.util.widget.FormInputData
import timber.log.Timber

class FormContentActivity : BaseActivity(), View.OnClickListener, FormGoodsAdd.Listener, FormInputData.Listener {
    private val viewModel: FormContentViewModel by viewModels{(applicationContext as APP).appContainer.viewModelFactory}
    private var _binding: ActivityFormContentBinding? = null
    private val binding get() = _binding!!
    lateinit var formName: String
    lateinit var dataNameList: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFormContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ResourcesCompat.getColor(resources, R.color.seed, null)

        // 檢查版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            formName = intent.getParcelableExtra("formName", String::class.java) as String
        } else {
            formName = intent.getSerializableExtra("formName") as String
        }
        bindViewModel()
        initView()
    }

    private fun bindViewModel() {

    }

    private fun initView() {
        binding.widgetTitleBar.textTitle.text = formName
        binding.widgetTitleBar.imageBack.visibility = View.VISIBLE
        binding.widgetTitleBar.imageScanner.visibility = View.VISIBLE
        initFormInputData()
    }
    fun initFormInputData() {
        var list = resources.getStringArray(R.array.delivery_form).toList() as ArrayList<String>

        list.forEach { dataName ->
            val formInputDataView = FormInputData(this, dataName, false)

            binding.linearData.addView(formInputDataView)
        }

        val formGoodsAddView = FormGoodsAdd(this, this)
        binding.linearData.addView(formGoodsAddView)

    }

    fun setDataNameList(formName: String): List<String> {
        return when(formName){
            getString(R.string.delivery) -> {resources.getStringArray(R.array.delivery_form).toList() as ArrayList<String>}
            else -> {ArrayList<String>()}
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.imageBack -> {
                onBackPressedCallback.handleOnBackPressed()
            }
        }
    }

    override fun onAddGoodsClick() {
        val formInputDataView = FormInputData(this, "dataName", true, this)
        // 创建一个点击事件监听器
        binding.linearData.addView(formInputDataView)
        //新增後能下移顯示新增的widget
        binding.scrollViewData.post {
            binding.scrollViewData.fullScroll(View.FOCUS_DOWN)
        }
        Timber.d("" + binding.linearData.childCount)
    }

    override fun onDeleteGoodsClick(view: View) {
        Timber.d("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW")
        if (view.id == R.id.imageDelete) {
            Timber.d("SSSSSSSSSSSSSSS")
            // 抓imageDelete的父層，這邊需要跨兩層
            val parentItem = view.parent.parent as View
            // 刪除指定列
            binding.linearData.removeView(parentItem)
        }
    }
}
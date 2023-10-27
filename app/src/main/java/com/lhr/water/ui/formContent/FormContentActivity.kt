package com.lhr.water.ui.formContent

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import com.lhr.water.R
import com.lhr.water.data.FormGoodInfo
import com.lhr.water.databinding.ActivityFormContentBinding
import com.lhr.water.ui.base.APP
import com.lhr.water.ui.base.BaseActivity
import com.lhr.water.ui.formContent.GoodsDialog.GoodsDialog
import com.lhr.water.util.widget.FormGoodsAdd
import com.lhr.water.util.widget.FormGoodsDataWidget
import com.lhr.water.util.widget.FormInputDataWidget
import timber.log.Timber

class FormContentActivity : BaseActivity(), View.OnClickListener, FormGoodsAdd.Listener, FormInputDataWidget.Listener, FormGoodsDataWidget.Listener,GoodsDialog.Listener {
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
        setupBackButton(binding.widgetTitleBar.imageBack)
        initFormInputData()
    }
    fun initFormInputData() {
        var list = resources.getStringArray(R.array.delivery_form).toList() as ArrayList<String>

        list.forEach { dataName ->
            val formInputDataWidgetView = FormInputDataWidget(this, dataName, false)

            binding.linearData.addView(formInputDataWidgetView)
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
        val goodsDialog = GoodsDialog(true, this)
        goodsDialog.show(supportFragmentManager, "GoodsDialog")
//        DialogManager.showEditDialog(
//            this,
//            resources.getString(R.string.delivery),
//            object : DialogManager.OnEditListener {
//                override fun onEdit(edit: String) {
//                    title = edit
//
//                    val formInputDataView = FormInputData(this@FormContentActivity, title, true, this@FormContentActivity)
//                    // 創建一個點擊事件
//                    binding.linearData.addView(formInputDataView)
//                    //新增後能下移顯示新增的widget
//                    binding.scrollViewData.post {
//                        binding.scrollViewData.fullScroll(View.FOCUS_DOWN)
//                    }
//                    if (edit.replace(" 1212", "11111111111").isEmpty()) {
//
//                        return
//                    }
//                }
//            }
//        )
    }

    override fun onDeleteGoodsClick(view: View) {
        if (view.id == R.id.imageDelete) {
            // 抓imageDelete的父層，這邊需要跨三層
            val parentItem = view.parent.parent.parent as View
            // 刪除指定列
            binding.linearData.removeView(parentItem)
        }
    }

    override fun onGoodsColClick(formGoodInfo: FormGoodInfo, formGoodsDataWidget: FormGoodsDataWidget) {
        val goodsDialog = GoodsDialog(false, this, formGoodInfo, formGoodsDataWidget)
        goodsDialog.show(supportFragmentManager, "GoodsDialog")
    }

    override fun onAddGoods(formGoodInfo: FormGoodInfo) {
        val formGoodsDataWidget = FormGoodsDataWidget(this@FormContentActivity, formGoodInfo, this@FormContentActivity)
      // 創建一個點擊事件
      binding.linearData.addView(formGoodsDataWidget)
      //新增後能下移顯示新增的widget
      binding.scrollViewData.post {
          binding.scrollViewData.fullScroll(View.FOCUS_DOWN)
      }
    }

    override fun onChangeGoodsInfo(formGoodInfo: FormGoodInfo, formGoodsDataWidget: FormGoodsDataWidget) {
        formGoodsDataWidget.binding.textGoodsName.text = formGoodInfo.goodsName
        formGoodsDataWidget.binding.textGoodsNumber.text = formGoodInfo.goodsNumber
        formGoodsDataWidget.formGoodInfo = formGoodInfo
        Timber.d(formGoodInfo.goodsName)
        binding.scrollViewData.smoothScrollTo(0, formGoodsDataWidget.top)
    }
}
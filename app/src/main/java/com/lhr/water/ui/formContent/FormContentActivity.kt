package com.lhr.water.ui.formContent

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.get
import androidx.core.view.iterator
import com.lhr.water.R
import com.lhr.water.data.Repository.FormRepository
import com.lhr.water.databinding.ActivityFormContentBinding
import com.lhr.water.room.FormEntity
import com.lhr.water.room.SqlDatabase
import com.lhr.water.ui.base.APP
import com.lhr.water.ui.base.BaseActivity
import com.lhr.water.ui.formContent.GoodsDialog.GoodsDialog
import com.lhr.water.ui.qrCode.QrcodeActivity
import com.lhr.water.util.jsonObjectToJsonString
import com.lhr.water.util.listToJsonObject
import com.lhr.water.util.widget.FormContentDataSpinnerWidget
import com.lhr.water.util.widget.FormGoodsAdd
import com.lhr.water.util.widget.FormGoodsDataWidget
import com.lhr.water.util.widget.FormContentDataWidget
import org.json.JSONArray
import org.json.JSONObject

class FormContentActivity : BaseActivity(), View.OnClickListener, FormGoodsAdd.Listener,
    FormContentDataWidget.Listener, FormGoodsDataWidget.Listener, GoodsDialog.Listener {
    private val viewModel: FormContentViewModel by viewModels { (applicationContext as APP).appContainer.viewModelFactory }
    private var _binding: ActivityFormContentBinding? = null
    private val binding get() = _binding!!
    lateinit var formName: String
    var jsonString: String? = null
    var formFieldNameList = ArrayList<String>() //表單欄位
    var formFieldNameEngList = ArrayList<String>() //表單欄位英文名
    var formFieldContentList = ArrayList<String>() //表單欄位內容
    var formItemFieldNameList = ArrayList<String>() //貨物欄位
    var formItemFieldNameEngList = ArrayList<String>() //貨物欄位英文名
    var formItemFieldContentList: JSONArray? = null //貨物欄位內容
    var formStatus = "0"
    lateinit var jsonObject: JSONObject
    private val qrcodeActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            if (data != null) {
                val resultData = data.getStringExtra("keyName")
            }
        }
    }

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
        if (intent.hasExtra("jsonString")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                jsonString = intent.getParcelableExtra("jsonString", String::class.java)
            } else {
                jsonString = intent.getStringExtra("jsonString")
            }
        }

        binding.widgetTitleBar.imageScanner.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, QrcodeActivity::class.java)
            qrcodeActivityResultLauncher.launch(intent)
        })
        bindViewModel()
        initView()
    }

    private fun bindViewModel() {

    }

    private fun initView() {
        binding.widgetTitleBar.textTitle.text = formName
        binding.widgetTitleBar.imageBack.visibility = View.VISIBLE
        binding.widgetTitleBar.imageScanner.visibility = View.VISIBLE
        when (formName) {
            getString(R.string.delivery) -> {
                formFieldNameList = resources.getStringArray(R.array.delivery_form_field_name)
                    .toList() as ArrayList<String>
                formFieldNameEngList =
                    resources.getStringArray(R.array.delivery_form_field_name_eng)
                        .toList() as ArrayList<String>
                formItemFieldNameList = resources.getStringArray(R.array.delivery_goods_field_name)
                    .toList() as ArrayList<String>
                formItemFieldNameEngList =
                    resources.getStringArray(R.array.delivery_goods_field_name_eng)
                        .toList() as ArrayList<String>
            }

            getString(R.string.check) -> {

            }

            getString(R.string.take_goods) -> {

            }

            getString(R.string.allocate) -> {

            }

            getString(R.string.return_goods) -> {

            }

            getString(R.string.inventory) -> {

            }
        }
        // 如果是開啟已有紀錄
        jsonString?.let {
            // 將jsonString轉成jsonObject
            jsonObject = JSONObject(jsonString)
            // 使用 map 函數根據key列表提取值並創建新的列表
            formFieldContentList = formFieldNameEngList.map { key ->
                jsonObject.getString(key)
            } as ArrayList<String>
            formItemFieldContentList = jsonObject.getJSONArray("ItemDetail")
        }
        addFormData()
        setupBackButton(binding.widgetTitleBar.imageBack)
        binding.buttonSend.setOnClickListener(this)
        binding.widgetFormGoodsAdd.imageAdd.setOnClickListener(this)
    }


    /**
     * 根據string.xml來增加要輸入的欄位
     */
    private fun addFormData() {
        formFieldNameList.forEachIndexed { index, fieldName ->
            val fieldContent =
                if (index < formFieldContentList.size) formFieldContentList[index] else null
            // 創建FormContentDataWidget
            if (fieldName == getString(R.string.status)) {
                val formContentDataSpinnerWidget = if (fieldContent != null) {
                    FormContentDataSpinnerWidget(
                        activity = this,
                        spinnerList = resources.getStringArray(R.array.deal_status).toList() as ArrayList<String>,
                        fieldName = fieldName,
                        fieldContent = fieldContent
                    )
                } else {
                    FormContentDataSpinnerWidget(
                        activity = this,
                        spinnerList = resources.getStringArray(R.array.deal_status).toList() as ArrayList<String>,
                        fieldName = fieldName
                    )
                }
                binding.linearFormData.addView(formContentDataSpinnerWidget)
            } else if (fieldName == getString(R.string.form)) {
                val formContentDataSpinnerWidget = if (fieldContent != null) {
                    FormContentDataSpinnerWidget(
                        activity = this,
                        spinnerList = resources.getStringArray(R.array.form_array)
                            .toList() as ArrayList<String>,
                        fieldName = fieldName,
                        fieldContent = fieldContent
                    )
                } else {
                    FormContentDataSpinnerWidget(
                        activity = this,
                        spinnerList = resources.getStringArray(R.array.form_array)
                            .toList() as ArrayList<String>,
                        fieldName = fieldName
                    )
                }
                binding.linearFormData.addView(formContentDataSpinnerWidget)
            } else {
                val formContentDataWidget = if (fieldContent != null) {
                    FormContentDataWidget(
                        activity = this,
                        fieldName = fieldName,
                        fieldContent = fieldContent
                    )
                } else {
                    FormContentDataWidget(activity = this, fieldName = fieldName)
                }
                binding.linearFormData.addView(formContentDataWidget)
            }
        }
        formItemFieldContentList?.let{formItemFieldContentList ->
            // 遍历 JSONArray 中的每个对象
            for (i in 0 until formItemFieldContentList.length()) {
                val itemObject = formItemFieldContentList.getJSONObject(i)

                val formGoodsDataWidget =
                    FormGoodsDataWidget(activity = this@FormContentActivity, formItemFieldJson = itemObject, listener = this@FormContentActivity)
                binding.linearItemData.addView(formGoodsDataWidget)
            }
        }

    }

    fun saveRecord() {
//        var formGoodInfoList = ArrayList<String>()
        val itemDetailArray = JSONArray()
        for (formGoodsDataWidget in binding.linearItemData) {
            itemDetailArray.put((formGoodsDataWidget as FormGoodsDataWidget).formItemJson)
//            formGoodInfoList.add(jsonObjectToJsonString((formGoodsDataWidget as FormGoodsDataWidget).formItemJson))
        }
        var formContentList = ArrayList<String>()
        formFieldNameList.forEachIndexed { index, fieldName ->
            when(fieldName){
                getString(R.string.form) -> formContentList.add((binding.linearFormData[index] as FormContentDataSpinnerWidget).content)
                getString(R.string.status) -> formContentList.add((binding.linearFormData[index] as FormContentDataSpinnerWidget).content)
                else -> formContentList.add((binding.linearFormData[index] as FormContentDataWidget).content)
            }
        }

        val formContentJsonObject = listToJsonObject(
            formFieldNameEngList,
            formContentList
        )
        formContentJsonObject.put("ItemDetail", itemDetailArray)
        var formEntity = FormEntity()
        formEntity.reportId = formContentJsonObject.getString("ReportId")
        formEntity.formContent = jsonObjectToJsonString(formContentJsonObject)
        SqlDatabase.getInstance().getDeliveryDao().insertOrUpdate(formEntity)
        FormRepository.getInstance(SqlDatabase.getInstance().getDeliveryDao()).loadRecord()
        finish()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.imageBack -> {
                onBackPressedCallback.handleOnBackPressed()
            }

            R.id.buttonSend -> {
                saveRecord()
            }

            R.id.imageAdd -> {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
                onAddGoodsClick()
            }
        }
    }


    /**
     * 點擊新增貨物的按鈕後跳出Dialog輸入新增的貨物資訊
     */
    override fun onAddGoodsClick() {
        val goodsDialog = GoodsDialog(true, formItemFieldNameList, formItemFieldNameEngList, this)
        goodsDialog.show(supportFragmentManager, "GoodsDialog")
    }

    override fun onDeleteGoodsClick(view: View) {
        if (view.id == R.id.imageDelete) {
            // 抓imageDelete的父層，這邊需要跨三層
            val parentItem = view.parent.parent.parent as View
            // 刪除指定列
            binding.linearItemData.removeView(parentItem)
        }
    }

    /**
     * 點擊已有的貨物，在Dialog中顯示貨物資訊
     */
    override fun onGoodsColClick(
        formItemFieldContentList: ArrayList<String>,
        formGoodsDataWidget: FormGoodsDataWidget
    ) {
        val goodsDialog = GoodsDialog(
            false,
            formItemFieldNameList,
            formItemFieldNameEngList,
            this,
            formItemFieldContentList,
            formGoodsDataWidget
        )
        goodsDialog.show(supportFragmentManager, "GoodsDialog")
    }


    /**
     * 在Dialog中輸入完新增的貨物資訊並送出後，新增一列
     */
    override fun onGoodsDialogConfirm(formItemJson: JSONObject) {
        val formGoodsDataWidget =
            FormGoodsDataWidget(this@FormContentActivity, formItemJson, this@FormContentActivity)
        // 創建一個點擊事件
        binding.linearItemData.addView(formGoodsDataWidget)
        //新增後能下移顯示新增的widget
        binding.scrollViewData.post {
            binding.scrollViewData.fullScroll(View.FOCUS_DOWN)
        }
    }

    override fun onChangeGoodsInfo(
        formItemJson: JSONObject,
        formGoodsDataWidget: FormGoodsDataWidget
    ) {
        formGoodsDataWidget.binding.textGoodsName.text = formItemJson.getString("MaterialName")
        formGoodsDataWidget.binding.textGoodsNumber.text = formItemJson.getString("MaterialNumber")
        formGoodsDataWidget.formItemJson = formItemJson
        binding.scrollViewData.smoothScrollTo(0, formGoodsDataWidget.top)
    }
}
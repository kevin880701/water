package com.lhr.water.ui.formContent

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.get
import androidx.core.view.iterator
import com.lhr.water.R
import com.lhr.water.repository.FormRepository
import com.lhr.water.databinding.ActivityFormContentBinding
import com.lhr.water.room.FormEntity
import com.lhr.water.room.SqlDatabase
import com.lhr.water.room.StorageRecordEntity
import com.lhr.water.ui.base.APP
import com.lhr.water.ui.base.BaseActivity
import com.lhr.water.util.dialog.GoodsDialog
import com.lhr.water.ui.qrCode.QrcodeActivity
import com.lhr.water.util.FormName.pickingFormName
import com.lhr.water.util.TransferStatus.transferInput
import com.lhr.water.util.TransferStatus.transferOutput
import com.lhr.water.util.isInput
import com.lhr.water.util.manager.jsonObjectToJsonString
import com.lhr.water.util.manager.jsonStringToJson
import com.lhr.water.util.manager.listToJsonObject
import com.lhr.water.util.showToast
import com.lhr.water.util.transferStatus
import com.lhr.water.util.widget.FormContentDataSpinnerWidget
import com.lhr.water.util.widget.FormGoodsAdd
import com.lhr.water.util.widget.FormGoodsDataWidget
import com.lhr.water.util.widget.FormContentDataWidget
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber

class FormContentActivity : BaseActivity(), View.OnClickListener, FormGoodsAdd.Listener,
    FormContentDataWidget.Listener, FormGoodsDataWidget.Listener, GoodsDialog.Listener {
    private val viewModel: FormContentViewModel by viewModels { (applicationContext as APP).appContainer.viewModelFactory }
    private var _binding: ActivityFormContentBinding? = null
    private val binding get() = _binding!!
    private lateinit var formName: String
    private var jsonString: String? = null
    private var formFieldNameList = ArrayList<String>() //表單欄位
    private var formFieldNameEngList = ArrayList<String>() //表單欄位英文名
    private var formFieldContentList = ArrayList<String>() //表單欄位內容
    private var formItemFieldNameList = ArrayList<String>() //貨物欄位
    private var formItemFieldNameEngList = ArrayList<String>() //貨物欄位英文名
    private var formItemFieldContentList: JSONArray? = null //貨物欄位內容
    private lateinit var jsonObject: JSONObject
    private var isInput = true
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
            formName = intent.getParcelableExtra("reportTitle", String::class.java) as String
        } else {
            formName = intent.getSerializableExtra("reportTitle") as String
        }
        if (intent.hasExtra("jsonString")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                jsonString = intent.getParcelableExtra("jsonString", String::class.java)
            } else {
                jsonString = intent.getStringExtra("jsonString")
            }
        }

        isInput = isInput(JSONObject(jsonString))

        when (formName) {
            getString(R.string.delivery) -> {
                formName = getString(R.string.delivery_form)
            }

            getString(R.string.check) -> {
                formName = getString(R.string.check_form)
            }

            getString(R.string.picking) -> {
                formName = getString(R.string.picking_form)
            }

            getString(R.string.transfer) -> {
                formName = getString(R.string.transfer_form)
            }

            getString(R.string.returning) -> {
                formName = getString(R.string.returning_form)
            }

            getString(R.string.inventory) -> {
                formName = getString(R.string.inventory_form)
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
        when (formName) {
            getString(R.string.delivery_form) -> {
                formFieldNameList = resources.getStringArray(R.array.delivery_form_field_name)
                    .toList() as ArrayList<String>
                formFieldNameEngList =
                    resources.getStringArray(R.array.delivery_form_field_name_eng)
                        .toList() as ArrayList<String>
                formItemFieldNameList = resources.getStringArray(R.array.delivery_Item_field_name)
                    .toList() as ArrayList<String>
                formItemFieldNameEngList =
                    resources.getStringArray(R.array.delivery_item_field_name_eng)
                        .toList() as ArrayList<String>
            }

            getString(R.string.check_form) -> {

            }

            getString(R.string.picking_form) -> {
                formFieldNameList = resources.getStringArray(R.array.picking_form_field_name)
                    .toList() as ArrayList<String>
                formFieldNameEngList =
                    resources.getStringArray(R.array.picking_form_field_name_eng)
                        .toList() as ArrayList<String>
                formItemFieldNameList = resources.getStringArray(R.array.picking_item_field_name)
                    .toList() as ArrayList<String>
                formItemFieldNameEngList =
                    resources.getStringArray(R.array.picking_item_field_name_eng)
                        .toList() as ArrayList<String>
            }

            getString(R.string.transfer_form) -> {
                formFieldNameList = resources.getStringArray(R.array.transfer_form_field_name)
                    .toList() as ArrayList<String>
                formFieldNameEngList =
                    resources.getStringArray(R.array.transfer_form_field_name_eng)
                        .toList() as ArrayList<String>
                formItemFieldNameList = resources.getStringArray(R.array.transfer_item_field_name)
                    .toList() as ArrayList<String>
                formItemFieldNameEngList =
                    resources.getStringArray(R.array.transfer_item_field_name_eng)
                        .toList() as ArrayList<String>
            }

            getString(R.string.returning_form) -> {
                formFieldNameList = resources.getStringArray(R.array.returning_form_field_name)
                    .toList() as ArrayList<String>
                formFieldNameEngList =
                    resources.getStringArray(R.array.returning_form_field_name_eng)
                        .toList() as ArrayList<String>
                formItemFieldNameList = resources.getStringArray(R.array.returning_item_field_name)
                    .toList() as ArrayList<String>
                formItemFieldNameEngList =
                    resources.getStringArray(R.array.returning_item_field_name_eng)
                        .toList() as ArrayList<String>
            }

            getString(R.string.inventory_form) -> {
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
            formItemFieldContentList = jsonObject.getJSONArray("itemDetail")
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
            if (fieldName == getString(R.string.deal_status)) {
                val formContentDataSpinnerWidget = if (fieldContent != null) {
                    FormContentDataSpinnerWidget(
                        activity = this,
                        spinnerList = resources.getStringArray(R.array.deal_status)
                            .toList() as ArrayList<String>,
                        fieldName = fieldName,
                        fieldContent = fieldContent
                    )
                } else {
                    FormContentDataSpinnerWidget(
                        activity = this,
                        spinnerList = resources.getStringArray(R.array.deal_status)
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
        formItemFieldContentList?.let { formItemFieldContentList ->
            // 遍历 JSONArray 中的每个对象
            for (i in 0 until formItemFieldContentList.length()) {
                val itemObject = formItemFieldContentList.getJSONObject(i)

                val formGoodsDataWidget =
                    FormGoodsDataWidget(
                        activity = this@FormContentActivity,
                        formItemFieldJson = itemObject,
                        listener = this@FormContentActivity
                    )
                binding.linearItemData.addView(formGoodsDataWidget)
            }
        }

    }

    /**
     * 點擊確認
     */
    private fun onClickSend() {
        val itemDetailArray = JSONArray()
        for (formGoodsDataWidget in binding.linearItemData) {
            itemDetailArray.put((formGoodsDataWidget as FormGoodsDataWidget).formItemJson)
        }
        var formContentList = ArrayList<String>()
        formFieldNameList.forEachIndexed { index, fieldName ->
            when (fieldName) {
                getString(R.string.deal_status) -> formContentList.add((binding.linearFormData[index] as FormContentDataSpinnerWidget).content)
                else -> formContentList.add((binding.linearFormData[index] as FormContentDataWidget).content)
            }
        }

        val formContentJsonObject = listToJsonObject(
            formFieldNameEngList,
            formContentList
        )

        formContentJsonObject.put("itemDetail", itemDetailArray)
        var reportTitle = formContentJsonObject.getString("reportTitle")
        var dealStatus = formContentJsonObject.getString("dealStatus")
        // 調撥需根據receivingDept(收方單位)和receivingLocation(收料地點)來判斷是進貨還是出貨
        var transferStatus = transferStatus(
            reportTitle == getString(R.string.transfer_form),
            formContentJsonObject
        )

        var formEntity = FormEntity()
        formEntity.formNumber = formContentJsonObject.getString("formNumber")
        formEntity.formContent = jsonObjectToJsonString(formContentJsonObject)

        // 如果表單是交貨、退料、進貨調撥並且處理狀態是處理完成的話要判斷表單中的貨物是否已經全部入庫
        if ((reportTitle == getString(R.string.delivery_form) ||
                    reportTitle == getString(R.string.returning_form) ||
                    transferStatus == transferInput) && dealStatus == getString(R.string.complete_deal)
        ) {
            if (isMaterialAlreadyInput(
                    viewModel.formRepository.tempWaitInputGoods.value!!,
                    itemDetailArray,
                    formContentJsonObject.getString("formNumber"),
                    reportTitle
                )
            ) {
                SqlDatabase.getInstance().getStorageRecordDao().insertStorageRecordList(
                    viewModel.getInsertGoodsFromTempWaitDealGoods(
                        itemDetailArray,
                        formContentJsonObject.getString("formNumber"),
                        reportTitle
                    )
                )
                viewModel.inputStorageContent(
                    formContentJsonObject.getString("reportTitle"),
                    formContentJsonObject.getString("formNumber")
                )
                updateForm(formEntity)
                updateTempWaitInputGoods(
                    itemDetailArray,
                    formContentJsonObject.getString("formNumber"),
                    reportTitle
                )
                finish()
            } else {
                showToast(this, "貨物未處理完成!")
            }
            // 如果表單是領料、出貨調撥並且處理狀態是處理完成的話要判斷表單中的貨物是否已經全部出庫
        } else if ((reportTitle == pickingFormName ||
                    transferStatus == transferOutput) && dealStatus == getString(R.string.complete_deal)
        ) {
            if (isMaterialAlreadyInput(
                    viewModel.formRepository.tempWaitOutputGoods.value!!,
                    itemDetailArray,
                    formContentJsonObject.getString("formNumber"),
                    reportTitle
                )
            ) {
                SqlDatabase.getInstance().getStorageRecordDao().insertStorageRecordList(
                    viewModel.getInsertGoodsFromTempWaitDealGoods(
                        itemDetailArray,
                        formContentJsonObject.getString("formNumber"),
                        reportTitle
                    )
                )
                viewModel.outputStorageContent(
                    formContentJsonObject.getString("reportTitle"),
                    formContentJsonObject.getString("formNumber")
                )
                updateForm(formEntity)
                updateTempWaitOutputGoods(
                    itemDetailArray,
                    formContentJsonObject.getString("formNumber"),
                    reportTitle
                )
                finish()
            } else {
                showToast(this, "貨物未處理完成!")
            }
        } else {
            updateForm(formEntity)
            finish()
        }
    }


    /**
     * 更新表單和room
     * @param formEntity 更新的表單資訊
     */
    fun updateForm(formEntity: FormEntity) {
        SqlDatabase.getInstance().getDeliveryDao().insertOrUpdate(formEntity)
        FormRepository.getInstance(this).loadRecord()
    }


    /**
     * 判斷進貨類表單(交貨、退料、調撥)表單中的貨物是否已經放入暫存入庫清單(tempWaitInputGoods)
     * @param itemDetailArray 要確認的貨物陣列
     * @param targetFormNumber 表單代號
     * @param targetReportTitle 表單名稱
     * @return 回傳Boolean
     */
    fun isMaterialAlreadyInput(
        tempWaitGoods: ArrayList<StorageRecordEntity>,
        itemDetailArray: JSONArray,
        targetFormNumber: String,
        targetReportTitle: String
    ): Boolean {
        for (i in 0 until itemDetailArray.length()) {
            val itemDetail = itemDetailArray.getJSONObject(i)
            var totalQuantity = 0
            for (storageContentEntity in tempWaitGoods) {
                // 檢查條件
                if (
                    storageContentEntity.formNumber == targetFormNumber &&
                    storageContentEntity.reportTitle == targetReportTitle &&
                    JSONObject(storageContentEntity.itemInformation).getString("number") == itemDetail.getString(
                        "number"
                    )
                ) {
                    totalQuantity += JSONObject(storageContentEntity.itemInformation).getInt("quantity")
                }
            }
            if (isInput) {
                if (totalQuantity < itemDetail.getInt("receivedQuantity")) {
                    return false
                }
            } else {
                if (totalQuantity < itemDetail.getInt("actualQuantity")) {
                    return false
                }
            }
        }
        return true
    }

    /**
     * 確定入庫後要把暫存入庫清單(tempWaitInputGoods)中關於表單的貨物刪除
     * @param itemDetailArray 要刪除的貨物陣列
     * @param formNumber 表單代號
     * @param reportTitle 表單名稱
     */
    fun updateTempWaitInputGoods(
        itemDetailArray: JSONArray,
        formNumber: String,
        reportTitle: String
    ) {
        val currentList = viewModel.formRepository.tempWaitInputGoods.value ?: ArrayList()
        for (i in 0 until itemDetailArray.length()) {
            val itemDetail = itemDetailArray.getJSONObject(i)
            val targetNumber = itemDetail.getString("number")

            // 移除 tempWaitInputGoods 中符合条件的项
            currentList.removeIf { entity ->
                entity.formNumber == formNumber &&
                        entity.reportTitle == reportTitle &&
                        jsonStringToJson(entity.itemInformation)["number"].toString() == targetNumber
            }
        }
        // 更新暫存進貨列表
        viewModel.formRepository.tempWaitInputGoods.postValue(currentList)
    }


    /**
     * 確定出庫後要把暫存出庫清單(tempWaitInputGoods)中關於表單的貨物刪除
     * @param itemDetailArray 要刪除的貨物陣列
     * @param formNumber 表單代號
     * @param reportTitle 表單名稱
     */
    fun updateTempWaitOutputGoods(
        itemDetailArray: JSONArray,
        formNumber: String,
        reportTitle: String
    ) {
        val currentList = viewModel.formRepository.tempWaitOutputGoods.value ?: ArrayList()
        for (i in 0 until itemDetailArray.length()) {
            val itemDetail = itemDetailArray.getJSONObject(i)
            val targetNumber = itemDetail.getString("number")

            // 移除 tempWaitInputGoods 中符合条件的项
            currentList.removeIf { entity ->
                entity.formNumber == formNumber &&
                        entity.reportTitle == reportTitle &&
                        jsonStringToJson(entity.itemInformation)["number"].toString() == targetNumber
            }
        }
        // 更新暫存出貨列表
        viewModel.formRepository.tempWaitOutputGoods.postValue(currentList)
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.imageBack -> {
                onBackPressedCallback.handleOnBackPressed()
            }

            R.id.buttonSend -> {
                onClickSend()
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
        formGoodsDataWidget.binding.textMaterialName.text = formItemJson.getString("materialName")
        formGoodsDataWidget.binding.textMaterialNumber.text = formItemJson.getString("materialNumber")
        formGoodsDataWidget.binding.textMaterialSpec.text = formItemJson.getString("materialSpec")
        formGoodsDataWidget.binding.textMaterialUnit.text = formItemJson.getString("materialUnit")
        formGoodsDataWidget.formItemJson = formItemJson
        binding.scrollViewData.smoothScrollTo(0, formGoodsDataWidget.top)
    }
}
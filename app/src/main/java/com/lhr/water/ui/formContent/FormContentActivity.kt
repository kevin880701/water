package com.lhr.water.ui.formContent

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import com.google.gson.Gson
import com.lhr.water.R
import com.lhr.water.data.deliveryFieldMap
import com.lhr.water.data.deliveryItemFieldMap
import com.lhr.water.data.form.BaseForm
import com.lhr.water.data.form.BaseItem
import com.lhr.water.data.form.DeliveryForm
import com.lhr.water.data.form.ReceiveForm
import com.lhr.water.data.form.ReturnForm
import com.lhr.water.data.form.TransferForm
import com.lhr.water.data.receiveFieldMap
import com.lhr.water.data.receiveItemFieldMap
import com.lhr.water.data.returningFieldMap
import com.lhr.water.data.returningItemFieldMap
import com.lhr.water.data.transferFieldMap
import com.lhr.water.data.transferItemFieldMap
import com.lhr.water.repository.FormRepository
import com.lhr.water.databinding.ActivityFormContentBinding
import com.lhr.water.room.FormEntity
import com.lhr.water.room.SqlDatabase
import com.lhr.water.ui.base.APP
import com.lhr.water.ui.base.BaseActivity
import com.lhr.water.util.adapter.SpinnerAdapter
import com.lhr.water.util.dealStatusList
import com.lhr.water.util.isInput
import com.lhr.water.util.showToast
import com.lhr.water.util.widget.FormGoodsDataWidget
import com.lhr.water.util.widget.FormContentDataWidget

class FormContentActivity : BaseActivity(), View.OnClickListener,
    FormContentDataWidget.Listener, FormGoodsDataWidget.Listener {
    private val viewModel: FormContentViewModel by viewModels { (applicationContext as APP).appContainer.viewModelFactory }
    private var _binding: ActivityFormContentBinding? = null
    private val binding get() = _binding!!
    private var formFieldNameMap: MutableMap<String, String> = linkedMapOf() //表單欄位
    private var formItemFieldNameMap: MutableMap<String, String> = linkedMapOf() //貨物欄位
    private var isInput = true
    private lateinit var formEntity: FormEntity
    private lateinit var baseForm: BaseForm
    var currentDealStatus = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFormContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ResourcesCompat.getColor(resources, R.color.primaryBlue, null)

        formEntity = intent.getSerializableExtra("formEntity") as FormEntity
        baseForm = formEntity.parseBaseForm()

        currentDealStatus = formEntity.dealStatus

        isInput = isInput(formEntity)

        bindViewModel()
        initView()
    }

    private fun bindViewModel() {

    }

    private fun initView() {
        binding.widgetTitleBar.textTitle.text = formEntity.reportTitle
        binding.widgetTitleBar.imageBack.visibility = View.VISIBLE
        when (formEntity.reportTitle) {
            getString(R.string.delivery_form) -> {
                formFieldNameMap = deliveryFieldMap.toMutableMap()
                formItemFieldNameMap = deliveryItemFieldMap.toMutableMap()
            }
            getString(R.string.receive_form) -> {
                formFieldNameMap = receiveFieldMap.toMutableMap()
                formItemFieldNameMap = receiveItemFieldMap.toMutableMap()
            }
            getString(R.string.transfer_form) -> {
                formFieldNameMap = transferFieldMap.toMutableMap()
                formItemFieldNameMap = transferItemFieldMap.toMutableMap()
            }

            getString(R.string.returning_form) -> {
                formFieldNameMap = returningFieldMap.toMutableMap()
                formItemFieldNameMap = returningItemFieldMap.toMutableMap()
            }
        }


        // 設定Spinner的選擇項監聽器
        val adapter = SpinnerAdapter(this, android.R.layout.simple_spinner_item, dealStatusList)
        binding.spinnerDealStatus.adapter = adapter
        binding.spinnerDealStatus.setSelection(dealStatusList.indexOf(currentDealStatus))
        binding.spinnerDealStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // 當選項被選擇時，將選項的值存儲到content
                currentDealStatus = dealStatusList[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // 如果沒有選項被選擇，你可以在這里處理邏輯
            }
        }

        addFormData()
        setupBackButton(binding.widgetTitleBar.imageBack)
        binding.buttonSend.setOnClickListener(this)
    }


    /**
     * 根據string.xml來增加要輸入的欄位
     */
    private fun addFormData() {
        baseForm.jsonConvertMap().forEach { key, value ->
            if(key != "itemDetail" && key != "dealStatus") {
                val formContentDataWidget = FormContentDataWidget(
                    activity = this,
                    fieldName = formFieldNameMap[key]!!,
                    fieldContent = value.toString()
                )
                binding.linearFormData.addView(formContentDataWidget)
            }
        }

        // --------------材料--------------------
        // 根据 reportTitle 的值确定应该转换为哪个子类
        val baseForm: BaseForm? = when (formEntity.reportTitle) {
            "交貨通知單" -> Gson().fromJson(formEntity.formContent, DeliveryForm::class.java)
            "材料領料單" -> Gson().fromJson(formEntity.formContent, ReceiveForm::class.java)
            "材料調撥單" -> Gson().fromJson(formEntity.formContent, TransferForm::class.java)
            "材料退料單" -> Gson().fromJson(formEntity.formContent, ReturnForm::class.java)
            else -> null // 未知的 reportTitle，或者其他处理方式
        }
        baseForm!!.itemDetails.forEachIndexed { index, itemDetail ->
                val formGoodsDataWidget =
                    FormGoodsDataWidget(
                        activity = this@FormContentActivity,
                        itemDetail = itemDetail,
                        listener = this@FormContentActivity
                    )
                binding.linearItemData.addView(formGoodsDataWidget)
        }
    }

    /**
     * 點擊確認
     */
    private fun onClickSend() {

        var formEntity = FormEntity(
            formNumber = formEntity.formNumber,
            dealStatus = currentDealStatus,
            reportId = formEntity.reportId,
            reportTitle = formEntity.reportTitle,
            date = formEntity.date,
            formContent = formEntity.formContent,
        )
        var dealStatus = currentDealStatus

        // 如果表單是交貨、退料、進貨調撥並且處理狀態是處理完成的話要判斷表單中的貨物是否已經全部入庫
        if (dealStatus == getString(R.string.complete_deal)
        ) {
            if (isMaterialAlreadyInput(
                    baseForm.itemDetails,
                    baseForm.formNumber,
                )
            ) {

                // 如果是退料單取得目前日期並轉換為民國年份
//                if(reportTitle == getString(R.string.returning_form) && form.receivedDate != ""){
////                    val currentDate = LocalDate.now()
////                    val rocYear = currentDate.year - 1911
////                    val formattedDate = String.format("%03d/%02d/%02d", rocYear, currentDate.monthValue, currentDate.dayOfMonth)
//                    form.receivedDate = getCurrentDate()
//                    formEntity.formContent = form.toJsonString()
//                }

                // 將暫存紀錄轉入SQL
                SqlDatabase.getInstance().getStorageRecordDao().insertStorageRecordEntities(
                    viewModel.getInsertStorageRecord(
                        baseForm.itemDetails,
                        baseForm.formNumber
                    )
                )
                updateForm(formEntity)
                viewModel.formRepository.loadSqlData()
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
        SqlDatabase.getInstance().getFormDao().insertOrUpdate(formEntity)
        FormRepository.getInstance(this).loadSqlData()
    }


    /**
     * 判斷進貨類表單(交貨、退料、調撥)表單中的貨物是否已經放入暫存入庫清單(tempWaitInputGoods)
     * @param itemDetailList 要確認的貨物列表
     * @param targetFormNumber 表單代號
     * @return 回傳Boolean
     */
    fun isMaterialAlreadyInput(
        itemDetailList: List<BaseItem>,
        targetFormNumber: String,
    ): Boolean {
        for (itemDetail in itemDetailList) {
            var totalQuantity = 0
            val materialNumber = itemDetail.materialNumber

            // 在暫存紀錄中查找與當前itemDetail的materialNumber和指定的FormNumber相匹配的記錄
            val matchingRecords = viewModel.formRepository.tempStorageRecordEntities.value!!.filter {
                it.materialNumber == materialNumber && it.formNumber == targetFormNumber
            }

            // 將匹配記錄的quantity加總到totalQuantity
            for (record in matchingRecords) {
                totalQuantity += record.quantity
            }

            if (totalQuantity<itemDetail.getRequestQuantity()) {
                return false
            }
        }
        return true
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.imageBack -> {
                onBackPressedCallback.handleOnBackPressed()
            }

            R.id.buttonSend -> {
                onClickSend()
            }
        }
    }

    override fun onDeleteGoodsClick(view: View) {
    }

    /**
     * 點擊已有的貨物，在Dialog中顯示貨物資訊
     */
    override fun onGoodsColClick(
        itemDetail: BaseItem,
        formGoodsDataWidget: FormGoodsDataWidget
    ) {
//        val goodsDialog = MaterialDialog(
//            false,
//            formItemFieldNameMap,
//            this,
//            itemDetail
//        )
//        goodsDialog.show(supportFragmentManager, "GoodsDialog")
    }
}
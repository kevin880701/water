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
import com.lhr.water.util.isCreateRNumberList
import com.lhr.water.util.showToast
import com.lhr.water.util.widget.MaterialWidget
import com.lhr.water.util.widget.FormContentDataWidget

class FormContentActivity : BaseActivity(), View.OnClickListener {
    val viewModel: FormContentViewModel by viewModels { (applicationContext as APP).appContainer.viewModelFactory }
    private var _binding: ActivityFormContentBinding? = null
    private val binding get() = _binding!!
    private var formFieldNameMap: MutableMap<String, String> = linkedMapOf() //表單欄位
    private var formItemFieldNameMap: MutableMap<String, String> = linkedMapOf() //貨物欄位
    private lateinit var formEntity: FormEntity
    private lateinit var baseForm: BaseForm
    var currentDealStatus = ""
    var currentIsCreateRNumber = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFormContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ResourcesCompat.getColor(resources, R.color.primaryBlue, null)

        formEntity = intent.getSerializableExtra("formEntity") as FormEntity
        baseForm = formEntity.parseBaseForm()

        currentDealStatus = formEntity.dealStatus
        currentIsCreateRNumber = formEntity.isCreateRNumber


        // 如果表單是處理中則提前確認是否有入庫完成，若有則自動把狀態改為處理完成
        if (currentDealStatus == getString(R.string.now_deal)
        ) {
            if (isMaterialAlreadyInput(
                    baseForm.itemDetails,
                    baseForm.formNumber,
                )
            ) {
                currentDealStatus = getString(R.string.complete_deal)
            }
        }

        currentIsCreateRNumber = baseForm.isCreateRNumber

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


        // 設定處理狀態Spinner的選擇項監聽器
        val adapter = SpinnerAdapter(this, android.R.layout.simple_spinner_item, dealStatusList)
        binding.spinnerDealStatus.adapter = adapter
        binding.spinnerDealStatus.setSelection(dealStatusList.indexOf(currentDealStatus))
        binding.spinnerDealStatus.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
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

        // 只有交貨單才要顯示分段交貨
        if (formEntity.reportTitle != "交貨通知單") {
            binding.textDeliveryStatus.visibility = View.GONE
        }

        binding.buttonSend.setOnClickListener(this)
    }


    /**
     * 根據string.xml來增加要輸入的欄位
     */
    private fun addFormData() {
        baseForm.jsonConvertMap().forEach { key, value ->
            if (key != "itemDetail" && key != "dealStatus" && key != "isCreateRNumber") {
                val formContentDataWidget = FormContentDataWidget(
                    activity = this,
                    fieldName = formFieldNameMap[key]!!,
                    fieldContent = value.toString()
                )
                binding.linearFormData.addView(formContentDataWidget)
            }else if(formEntity.reportTitle == "材料調撥單"){
                if((baseForm as TransferForm).transferStatus == "撥方已送出") {
                    // 當單據為"調撥單"並且transferStatus為"撥方已送出"時才有
                    // 設定產生領料單Spinner的選擇項監聽器
                    binding.constraintIsCreateRNumber.visibility = View.VISIBLE
                    val adapter = SpinnerAdapter(
                        this,
                        android.R.layout.simple_spinner_item,
                        isCreateRNumberList
                    )
                    binding.spinnerIsCreateRNumber.adapter = adapter
                    binding.spinnerIsCreateRNumber.setSelection(currentIsCreateRNumber.toInt())
                    binding.spinnerIsCreateRNumber.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                // 當選項被選擇時，將選項的值存儲到content
                                currentIsCreateRNumber = position.toString()
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                // 如果沒有選項被選擇，你可以在這里處理邏輯
                            }
                        }
                }
            }
        }

        // --------------材料--------------------

        baseForm.itemDetails.forEachIndexed { index, itemDetail ->
            val formMaterialDataWidget =
                MaterialWidget(
                    activity = this@FormContentActivity,
                    baseForm = baseForm,
                    itemDetail = itemDetail,
                    deliveryStatus = if (formEntity.reportTitle == "交貨通知單") {
                        (baseForm as DeliveryForm).itemDetails[index].deliveryStatus
                    } else {
                        null
                    },
                    dealStatus = formEntity.dealStatus
                )
            binding.linearItemData.addView(formMaterialDataWidget)
        }
    }

    /**
     * 點擊確認
     */
    private fun onClickSend() {

        var dealStatus = currentDealStatus

        for (i in 0 until binding.linearItemData.childCount) {
            val childView = binding.linearItemData.getChildAt(i) as MaterialWidget
            val gson = Gson()
            var jsonString = ""

            // 更新核定數量
            baseForm.itemDetails[i].approvedQuantity = childView.textApprovedQuantity.text.toString()
            jsonString = gson.toJson(baseForm)

            formEntity.formContent = jsonString

            // 交貨單要更新每個材料的分段交貨欄位
            if (formEntity.reportTitle == "交貨通知單") {
                (baseForm as DeliveryForm).itemDetails[i].deliveryStatus = childView.binding.switchDeliveryStatus.isChecked.toString()
            }
            jsonString = gson.toJson(baseForm)
            formEntity.formContent = jsonString
        }

        var formEntity = FormEntity(
            formNumber = formEntity.formNumber,
            dealStatus = currentDealStatus,
            reportId = formEntity.reportId,
            reportTitle = formEntity.reportTitle,
            date = formEntity.date,
            formContent = formEntity.formContent,
            isCreateRNumber = currentIsCreateRNumber,
        )

        // 如果表單是處理完成的話要判斷表單中的貨物是否已經全部入庫
        if (dealStatus == getString(R.string.complete_deal)
        ) {
            if (isMaterialAlreadyInput(
                    baseForm.itemDetails,
                    baseForm.formNumber,
                )
            ) {
                // 將暫存紀錄轉入SQL
                SqlDatabase.getInstance().getStorageRecordDao().insertStorageRecordEntities(
                    viewModel.getInsertStorageRecord(
                        baseForm.itemDetails,
                        baseForm.formNumber
                    )
                )
                formEntity.isUpdate = false
                updateForm(formEntity)
                viewModel.formRepository.loadSqlData()
                finish()
            } else {
                showToast(this, "貨物未處理完成!")
            }
        } else {
            formEntity.isUpdate = true
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
            val matchingRecords =
                viewModel.formRepository.tempStorageRecordEntities.value!!.filter {
                    it.materialNumber == materialNumber && it.formNumber == targetFormNumber
                }

            // 將匹配記錄的quantity加總到totalQuantity
            for (record in matchingRecords) {
                totalQuantity += record.quantity.toInt()
            }

            if (totalQuantity < itemDetail.requestQuantity.toInt()) {
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
}
package com.lhr.water.ui.storageGoodInput

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.lhr.water.R
import com.lhr.water.data.WaitDealGoodsData
import com.lhr.water.databinding.ActivityCoverBinding
import com.lhr.water.databinding.ActivityStorageContentBinding
import com.lhr.water.databinding.ActivityStorageInputBinding
import com.lhr.water.model.Model
import com.lhr.water.room.SqlDatabase
import com.lhr.water.room.StorageContentEntity
import com.lhr.water.ui.base.APP
import com.lhr.water.ui.base.BaseActivity
import com.lhr.water.ui.login.LoginActivity
import com.lhr.water.ui.login.LoginViewModel
import com.lhr.water.ui.map.MapActivity
import com.lhr.water.util.adapter.MapChooseAdapter
import com.lhr.water.util.adapter.RegionChooseAdapter
import com.lhr.water.util.adapter.StorageInputAdapter
import com.lhr.water.util.dialog.GoodsDialog
import com.lhr.water.util.manager.jsonStringToJson
import com.lhr.water.util.widget.FormGoodsDataWidget
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber


class StorageGoodInputActivity : BaseActivity(), StorageInputAdapter.Listener, View.OnClickListener, GoodsDialog.Listener {

    private val viewModel: StorageGoodInputViewModel by viewModels{(applicationContext as APP).appContainer.viewModelFactory}

    private var _binding: ActivityStorageInputBinding? = null
    private val binding get() = _binding!!

    lateinit var storageInputAdapter: StorageInputAdapter
    lateinit var region: String
    lateinit var map: String
    lateinit var storageNum: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityStorageInputBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ResourcesCompat.getColor(resources, R.color.seed, null)

        // 檢查版本判斷接收資料方式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            region = intent.getParcelableExtra("region", String::class.java) as String
            map = intent.getParcelableExtra("map", String::class.java) as String
            storageNum = intent.getParcelableExtra("storageNum", String::class.java) as String
        } else {
            region = intent.getSerializableExtra("region") as String
            map = intent.getSerializableExtra("map") as String
            storageNum = intent.getSerializableExtra("storageNum") as String
        }

        initView()
        bindViewModel()
    }


    private fun bindViewModel() {
        viewModel.inputFormNumber.observe(this) { input ->
            val resultList = viewModel.waitInputGoodList.filter { waitDealGoodsData ->
                waitDealGoodsData.formNumber.contains(input)
            }
            storageInputAdapter.submitList(resultList)
//            for (i in resultList){
//                Timber.d(i.formNumber)
//                Timber.d(i.reportTitle)
//                Timber.d(i.itemInformation.toString())
//            }
        }
    }

    private fun initView() {
        binding.widgetTitleBar.textTitle.text = getString(R.string.wait_input_good)
        binding.widgetTitleBar.imageBack.visibility = View.VISIBLE
        initRecyclerView()

        setupBackButton(binding.widgetTitleBar.imageBack)


        binding.editSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 在文本改變之前執行的操作
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 在文本改變過程中執行的操作
            }

            override fun afterTextChanged(s: Editable?) {
                // 在文本改變之後執行的操作
                viewModel.inputFormNumber.postValue(s.toString())
            }
        })
    }

    private fun initRecyclerView() {
        storageInputAdapter = StorageInputAdapter(this)

        storageInputAdapter.submitList(viewModel.getWaitInputGoods())
        binding.recyclerGoods.adapter = storageInputAdapter
        binding.recyclerGoods.layoutManager = LinearLayoutManager(this)

        binding.widgetTitleBar.imageBack.setOnClickListener(this)
        binding.buttonConfirm.setOnClickListener(this)
    }



    /**
     * 點擊列顯示對應貨物資訊的Dialog
     * @param waitDealGoodsData 貨物資訊
     */
    override fun onItemClick(waitDealGoodsData: WaitDealGoodsData) {
        val goodFieldNameList = resources.getStringArray(R.array.storage_Good_field_name)
            .toList() as ArrayList<String>
        val goodFieldNameEngList =
            resources.getStringArray(R.array.storage_Good_field_name_eng)
                .toList() as ArrayList<String>
        val goodContent = ArrayList<String>()
        val goodJsonObject = waitDealGoodsData.itemInformation
        goodFieldNameEngList.forEach { key ->
            if (goodJsonObject.has(key)) {
                val value = goodJsonObject.getString(key)
                goodContent.add(value)
            } else {
                // Handle the case where the key is not present in the JSON object
                // You can choose to add a default value or take any other action
                goodContent.add("Key $key not found")
            }
        }
        val goodsDialog = GoodsDialog(
            isAdd = true,
            formItemFieldNameList = goodFieldNameList,
            formItemFieldNameEngList = goodFieldNameEngList,
            listener = this,
            formItemFieldContentList = goodContent
        )
        goodsDialog.show(supportFragmentManager, "GoodsDialog")
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.buttonConfirm -> {
                viewModel.inputGoods(storageInputAdapter, region, map, storageNum)
                finish()
            }
        }
    }

    override fun onGoodsDialogConfirm(formItemJson: JSONObject) {
    }

    override fun onChangeGoodsInfo(
        formItemJson: JSONObject,
        formGoodsDataWidget: FormGoodsDataWidget
    ) {
    }
}
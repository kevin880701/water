package com.lhr.water.ui.inventory

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lhr.water.R
import com.lhr.water.data.returningFieldMap
import com.lhr.water.databinding.FragmentInventoryBinding
import com.lhr.water.repository.FormRepository
import com.lhr.water.room.InventoryEntity
import com.lhr.water.ui.base.BaseFragment
import com.lhr.water.util.SharedPreferencesHelper
import com.lhr.water.util.adapter.InventoryAdapter
import org.json.JSONException
import timber.log.Timber


class InventoryFragment : BaseFragment(), View.OnClickListener, InventoryAdapter.Listener {

    private var _binding: FragmentInventoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: InventoryViewModel by viewModels { viewModelFactory }
    private lateinit var inventoryAdapter: InventoryAdapter
    val formRepository: FormRepository by lazy { FormRepository.getInstance(requireContext()) }

    private var formFieldNameList = ArrayList<String>() //表單欄位
    private var formFieldNameEngList = ArrayList<String>() //表單欄位英文名
    private var formFieldNameMap: MutableMap<String, String> = linkedMapOf() //表單欄位

    private val callback = object : OnBackPressedCallback(true /* enabled by default */) {
        override fun handleOnBackPressed() {
                requireActivity().finish()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInventoryBinding.inflate(layoutInflater)

        initView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
    }

    private fun bindViewModel() {
        formRepository.inventoryEntities.observe(viewLifecycleOwner) { inventoryEntities ->
            inventoryAdapter.submitList(inventoryEntities)
        }

        // 盤點表單代號輸入後篩選更新
        viewModel.searchMaterialName.observe(viewLifecycleOwner) {searchMaterialName ->
            inventoryAdapter.submitList(viewModel.filterRecord(formRepository.inventoryEntities.value!!, searchMaterialName))
        }

        // 盤點表單代號輸入後篩選更新
        viewModel.formRepository.isInventoryCompleted.observe(viewLifecycleOwner) {isInventoryCompleted ->
            binding.checkbox.isChecked = isInventoryCompleted
        }
    }

    private fun initView() {

        formFieldNameMap = returningFieldMap.toMutableMap()

        binding.widgetTitleBar.textTitle.text = requireActivity().getString(R.string.search_inventory)
        binding.widgetTitleBar.imageBackup.visibility = View.VISIBLE
        initRecyclerView()
        binding.editSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 在文本改變之前執行的操作
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 在文本改變過程中執行的操作
            }

            override fun afterTextChanged(s: Editable?) {
                // 在文本改變之後執行的操作
                viewModel.searchMaterialName.postValue(s.toString())
            }
        })

        binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
            viewModel.formRepository.isInventoryCompleted.postValue(isChecked)
            SharedPreferencesHelper.saveInventoryCompleted(requireContext(), isChecked)
        }

        binding.widgetTitleBar.imageBackup.setOnClickListener(this)
    }

    private fun initRecyclerView() {
        inventoryAdapter = InventoryAdapter(this, requireContext())
        binding.recyclerInventory.adapter = inventoryAdapter
        binding.recyclerInventory.layoutManager = LinearLayoutManager(activity)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
        }
    }

    /**
     * 表單列表點擊
     * @param json 被點擊的列資料
     */
    override fun onItemClick(inventoryEntity: InventoryEntity) {
        val extractedValues = ArrayList<String>()
        for (fieldName in formFieldNameEngList) {
            try {
//                val value: String = json.getString(fieldName)
//                extractedValues.add(value)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Timber.d("onPause")
        callback.remove()
    }

    override fun onResume() {
        super.onResume()
        Timber.d("onResume")
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

}
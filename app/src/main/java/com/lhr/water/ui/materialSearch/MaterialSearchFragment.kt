package com.lhr.water.ui.materialSearch

import android.content.Intent
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
import com.lhr.water.data.Form
import com.lhr.water.data.Form.Companion.toJsonString
import com.lhr.water.databinding.FragmentMaterialSearchBinding
import com.lhr.water.ui.base.BaseFragment
import com.lhr.water.ui.formContent.FormContentActivity
import com.lhr.water.ui.history.dealMaterial.DealMaterialActivity
import com.lhr.water.util.adapter.HistoryAdapter
import com.lhr.water.util.adapter.MaterialSearchAdapter
import org.json.JSONObject
import timber.log.Timber

class MaterialSearchFragment : BaseFragment(), View.OnClickListener, HistoryAdapter.Listener {

    private var _binding: FragmentMaterialSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MaterialSearchViewModel by viewModels { viewModelFactory }
    private lateinit var materialSearchAdapter: MaterialSearchAdapter

    private val callback = object : OnBackPressedCallback(true /* enabled by default */) {
        override fun handleOnBackPressed() {
                requireActivity().finish()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMaterialSearchBinding.inflate(layoutInflater)

        initView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
    }

    private fun bindViewModel() {
        viewModel.formRepository.storageGoods.observe(viewLifecycleOwner) { newStorageGoods ->
            materialSearchAdapter.submitList(newStorageGoods)
        }
    }

    private fun initView() {
        binding.widgetTitleBar.textTitle.text = requireActivity().getString(R.string.search_material)
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
                materialSearchAdapter.submitList(viewModel.formRepository.searchStorageContentByMaterialName(s.toString()))
            }
        })

        binding.widgetTitleBar.imageFilter.setOnClickListener(this)
    }

    private fun initRecyclerView() {
        materialSearchAdapter = MaterialSearchAdapter(viewModel)
        materialSearchAdapter.submitList(viewModel.formRepository.storageGoods.value)
        binding.recyclerHistory.adapter = materialSearchAdapter
        binding.recyclerHistory.layoutManager = LinearLayoutManager(activity)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imageFilter -> {
            }
        }
    }

    /**
     * 表單列表點擊
     * @param form 被點擊的列資料
     */
    override fun onItemClick(form: Form) {
        val intent = Intent(requireActivity(), FormContentActivity::class.java)
        intent.putExtra("reportTitle", form.reportTitle)
        intent.putExtra("jsonString", form.toJsonString())
        requireActivity().startActivity(intent)
    }

    override fun onDealGoodsClick(json: JSONObject) {
        val intent = Intent(requireActivity(), DealMaterialActivity::class.java)
        intent.putExtra("jsonString", json.toString())
        requireActivity().startActivity(intent)
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
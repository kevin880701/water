package com.lhr.water.ui.history

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
import com.lhr.water.repository.FormRepository
import com.lhr.water.databinding.FragmentHistoryBinding
import com.lhr.water.ui.base.BaseFragment
import com.lhr.water.ui.formContent.FormContentActivity
import com.lhr.water.ui.history.dealMaterial.DealMaterialActivity
import com.lhr.water.util.popupWindow.FilterFormPopupWindow
import com.lhr.water.util.adapter.HistoryAdapter
import org.json.JSONObject
import timber.log.Timber

class HistoryFragment : BaseFragment(), View.OnClickListener, HistoryAdapter.Listener {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HistoryViewModel by viewModels { viewModelFactory }
    private lateinit var historyAdapter: HistoryAdapter
    val formRepository: FormRepository by lazy { FormRepository.getInstance(requireContext()) }

    private val callback = object : OnBackPressedCallback(true /* enabled by default */) {
        override fun handleOnBackPressed() {
                requireActivity().finish()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(layoutInflater)

        initView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
    }

    private fun bindViewModel() {
        formRepository.formRecordList.observe(viewLifecycleOwner) { newFormRecordList ->
            historyAdapter.submitList(newFormRecordList)
        }
        // 根據篩選的表單類別和表單代號更新列表
        formRepository.formFilterRecordList.observe(viewLifecycleOwner) { newFormRecordList ->
            historyAdapter.submitList(newFormRecordList)
        }
        // 表單類別篩選更新
        formRepository.filterList.observe(viewLifecycleOwner) {
            formRepository.formFilterRecordList.postValue(formRepository.filterRecord())
        }
        // 表單代號輸入後篩選更新
        formRepository.searchFormNumber.observe(viewLifecycleOwner) {
            formRepository.formFilterRecordList.postValue(formRepository.filterRecord())
        }
    }

    private fun initView() {
        binding.widgetTitleBar.textTitle.text = requireActivity().getString(R.string.form_history)
        binding.widgetTitleBar.imageFilter.visibility = View.VISIBLE
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
                formRepository.searchFormNumber.postValue(s.toString())
            }
        })

        binding.widgetTitleBar.imageFilter.setOnClickListener(this)
    }

    private fun initRecyclerView() {
        historyAdapter = HistoryAdapter(this, requireContext())
        binding.recyclerHistory.adapter = historyAdapter
        binding.recyclerHistory.layoutManager = LinearLayoutManager(activity)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imageFilter -> {
                showPopupWindow(binding.widgetTitleBar.constraintTitleBar)
            }
        }
    }

    /**
     * 表單列表點擊
     * @param json 被點擊的列資料
     */
    override fun onItemClick(json: JSONObject) {
        val intent = Intent(requireActivity(), FormContentActivity::class.java)
        intent.putExtra("reportTitle", json.getString("reportTitle"))
        intent.putExtra("jsonString", json.toString())
        requireActivity().startActivity(intent)
    }

    override fun onDealGoodsClick(json: JSONObject) {
        val intent = Intent(requireActivity(), DealMaterialActivity::class.java)
        intent.putExtra("jsonString", json.toString())
        requireActivity().startActivity(intent)
    }

    /**
     * 顯示篩選清單
     * @param anchorView 要在哪個元件的下方
     */
    private fun showPopupWindow(anchorView: View) {
        // 創建PopupWindow
        val popupWindow = FilterFormPopupWindow(requireActivity(), viewModel)

        // 顯示PopupWindow 在 TitleBar 的下方
        popupWindow.showAsDropDown(anchorView)
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
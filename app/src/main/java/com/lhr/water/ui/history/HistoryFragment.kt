package com.lhr.water.ui.history

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.recyclerview.widget.LinearLayoutManager
import com.lhr.water.R
import com.lhr.water.data.DeliveryData
import com.lhr.water.data.Repository.FormRepository
import com.lhr.water.databinding.FragmentHistoryBinding
import com.lhr.water.room.SqlDatabase
import com.lhr.water.ui.base.BaseFragment
import com.lhr.water.ui.formContent.FormContentActivity
import com.lhr.water.util.popupWindow.FilterFormPopupWindow
import com.lhr.water.util.recyclerViewAdapter.HistoryAdapter
import org.json.JSONObject

class HistoryFragment : BaseFragment(), View.OnClickListener, HistoryAdapter.Listener {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HistoryViewModel by viewModels { viewModelFactory }
    lateinit var historyAdapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(layoutInflater)
//        requireActivity().window.statusBarColor = ResourcesCompat.getColor(resources, R.color.white, null)

        initView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
    }

    private fun bindViewModel() {
        FormRepository.getInstance(SqlDatabase.getInstance().getDeliveryDao()).formRecordList.observe(viewLifecycleOwner){ newFormRecordList ->
            historyAdapter.submitList(newFormRecordList)
        }
        viewModel.filterList.observe(viewLifecycleOwner){ newFilterList ->
            val filteredFormRecordList = FormRepository.getInstance(SqlDatabase.getInstance().getDeliveryDao()).formRecordList.value?.filter { jsonObject ->
                // 根据 "FormClass" 判断是否在 filterList 中
                val formClass = jsonObject.optString("FormClass")
                formClass in newFilterList
            }?.toMutableList()
            historyAdapter.submitList(filteredFormRecordList)
        }
    }

    private fun initView() {
        binding.widgetTitleBar.textTitle.text = requireActivity().getString(R.string.form_history)
        binding.widgetTitleBar.imageFilter.visibility = View.VISIBLE
        binding.widgetTitleBar.imageScanner.visibility = View.VISIBLE
        initRecyclerView()

        binding.widgetTitleBar.imageFilter.setOnClickListener(this)
    }

    private fun initRecyclerView() {
        historyAdapter = HistoryAdapter(this, requireContext())
//        historyAdapter.submitList(FormRepository.getInstance(SqlDatabase.getInstance().getDeliveryDao()).loadRecord())
        binding.recyclerHistory.adapter = historyAdapter
        binding.recyclerHistory.layoutManager = LinearLayoutManager(activity)
        FormRepository.getInstance(SqlDatabase.getInstance().getDeliveryDao()).loadRecord()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imageFilter -> {
                showPopupWindow(binding.widgetTitleBar.constraintTitleBar)
            }
        }
    }

    override fun onItemClick(json: JSONObject) {
        val intent = Intent(requireActivity(), FormContentActivity::class.java)
        intent.putExtra("formName", json.getString("FormClass"))
        intent.putExtra("jsonString", json.toString())
        requireActivity().startActivity(intent)
    }

    /**
     * 顯示篩選清單
     * @param anchorView 要在哪個元件的下方
     */
    private fun showPopupWindow(anchorView: View) {
        val inflater = requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        // 創建PopupWindow
        val popupWindow = FilterFormPopupWindow(requireActivity(), viewModel)

        // 顯示PopupWindow 在 TitleBar 的下方
        popupWindow.showAsDropDown(anchorView)
    }
}
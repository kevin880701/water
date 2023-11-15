package com.lhr.water.ui.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lhr.water.R
import com.lhr.water.data.DeliveryData
import com.lhr.water.data.Repository.FormRepository
import com.lhr.water.databinding.FragmentHistoryBinding
import com.lhr.water.room.SqlDatabase
import com.lhr.water.ui.base.BaseFragment
import com.lhr.water.ui.formContent.FormContentActivity
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

    }

    private fun initView() {
        binding.widgetTitleBar.textTitle.text = requireActivity().getString(R.string.form_history)
        initRecyclerView()
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

        }
    }

    override fun onItemClick(json: JSONObject) {
        val intent = Intent(requireActivity(), FormContentActivity::class.java)
        intent.putExtra("formName", resources.getStringArray(R.array.form_array)[json.getString("FormClass").toInt()])
        intent.putExtra("jsonString", json.toString())
        requireActivity().startActivity(intent)
    }
}
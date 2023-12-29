package com.lhr.water.ui.history.inputActivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lhr.water.R
import com.lhr.water.data.WaitDealGoodsData
import com.lhr.water.databinding.FragmentWaitInputGoodsBinding
import com.lhr.water.room.StorageContentEntity
import com.lhr.water.ui.base.BaseFragment
import com.lhr.water.ui.history.HistoryViewModel
import com.lhr.water.util.adapter.AlreadyChooseGoodsAdapter
import org.json.JSONObject
import timber.log.Timber


class AlreadyChooseGoodsFragment(jsonString: JSONObject) : BaseFragment(), View.OnClickListener,
    AlreadyChooseGoodsAdapter.Listener {

    private val viewModel: HistoryViewModel by viewModels { viewModelFactory }
    private var _binding: FragmentWaitInputGoodsBinding? = null
    private val binding get() = _binding!!
    private lateinit var alreadyChooseGoodsAdapter: AlreadyChooseGoodsAdapter
    private var jsonString = jsonString


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWaitInputGoodsBinding.inflate(layoutInflater)
        initView()
        bindViewModel()
        return binding.root
    }

    private fun bindViewModel() {
        viewModel.formRepository.tempWaitInputGoods.observe(viewLifecycleOwner) { _ ->
            alreadyChooseGoodsAdapter.submitList(
                viewModel.filterTempWaitInputGoods(
                    jsonString["reportTitle"].toString(),
                    jsonString["formNumber"].toString()
                )
            )
        }
    }

    private fun initView() {
        initRecyclerView()
    }


    private fun initRecyclerView() {
        alreadyChooseGoodsAdapter = AlreadyChooseGoodsAdapter(this, viewModel)
        alreadyChooseGoodsAdapter.submitList(
            viewModel.filterTempWaitInputGoods(
                jsonString["reportTitle"].toString(),
                jsonString["formNumber"].toString()
            )
        )
        binding.recyclerGoods.adapter = alreadyChooseGoodsAdapter
        binding.recyclerGoods.layoutManager = LinearLayoutManager(requireActivity())
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.linearLayoutGoodInput -> {

            }
        }
    }

    /**
     * 移除暫存列表的指定內容
     */
    override fun onRemoveClick(storageContentEntity: StorageContentEntity) {
        var tempArrayList = viewModel.formRepository.tempWaitInputGoods.value!!
        tempArrayList.removeIf { it == storageContentEntity }
        viewModel.formRepository.tempWaitInputGoods.value = tempArrayList
    }
}
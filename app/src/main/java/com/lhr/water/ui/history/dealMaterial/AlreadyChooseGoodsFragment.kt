package com.lhr.water.ui.history.dealMaterial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lhr.water.R
import com.lhr.water.databinding.FragmentAlreadyChooseMaterialBinding
import com.lhr.water.databinding.FragmentWaitDealMaterialBinding
import com.lhr.water.room.StorageRecordEntity
import com.lhr.water.ui.base.BaseFragment
import com.lhr.water.ui.history.HistoryViewModel
import com.lhr.water.util.adapter.AlreadyChooseGoodsAdapter
import com.lhr.water.util.isInput
import org.json.JSONObject


class AlreadyChooseGoodsFragment(jsonString: JSONObject) : BaseFragment(), View.OnClickListener,
    AlreadyChooseGoodsAdapter.Listener {

    private val viewModel: HistoryViewModel by viewModels { viewModelFactory }
    private var _binding: FragmentAlreadyChooseMaterialBinding? = null
    private val binding get() = _binding!!
    private lateinit var alreadyChooseGoodsAdapter: AlreadyChooseGoodsAdapter
    private var jsonString = jsonString
    private var isInput = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlreadyChooseMaterialBinding.inflate(layoutInflater)

        isInput = isInput(jsonString)
        initView()
        bindViewModel()
        return binding.root
    }

    private fun bindViewModel() {
        if (isInput){
            viewModel.formRepository.tempWaitInputGoods.observe(viewLifecycleOwner) { _ ->
                alreadyChooseGoodsAdapter.submitList(
                    viewModel.filterTempWaitInputGoods(
                        jsonString["reportTitle"].toString(),
                        jsonString["formNumber"].toString()
                    )
                )
            }
        }else{
            viewModel.formRepository.tempWaitOutputGoods.observe(viewLifecycleOwner) { _ ->
                alreadyChooseGoodsAdapter.submitList(
                    viewModel.filterTempWaitOutputGoods(
                        jsonString["reportTitle"].toString(),
                        jsonString["formNumber"].toString()
                    )
                )
            }
        }
    }

    private fun initView() {
        binding.widgetField.imageRemove.visibility = View.INVISIBLE
        initRecyclerView()
    }


    private fun initRecyclerView() {
        alreadyChooseGoodsAdapter = AlreadyChooseGoodsAdapter(this, viewModel)
        if(isInput){
            alreadyChooseGoodsAdapter.submitList(
                viewModel.filterTempWaitInputGoods(
                    jsonString["reportTitle"].toString(),
                    jsonString["formNumber"].toString()
                )
            )
        }else{
            alreadyChooseGoodsAdapter.submitList(
                viewModel.filterTempWaitOutputGoods(
                    jsonString["reportTitle"].toString(),
                    jsonString["formNumber"].toString()
                )
            )
        }
        binding.recyclerGoods.adapter = alreadyChooseGoodsAdapter
        binding.recyclerGoods.layoutManager = LinearLayoutManager(requireActivity())
    }


    override fun onClick(v: View?) {
        when (v?.id) {
        }
    }

    /**
     * 移除暫存列表的指定內容
     */
    override fun onRemoveClick(storageContentEntity: StorageRecordEntity) {
        if (isInput){
            var tempArrayList = viewModel.formRepository.tempWaitInputGoods.value!!
            tempArrayList.removeIf { it == storageContentEntity }
            viewModel.formRepository.tempWaitInputGoods.value = tempArrayList
        }else{
            var tempArrayList = viewModel.formRepository.tempWaitOutputGoods.value!!
            tempArrayList.removeIf { it == storageContentEntity }
            viewModel.formRepository.tempWaitOutputGoods.value = tempArrayList
        }
    }
}
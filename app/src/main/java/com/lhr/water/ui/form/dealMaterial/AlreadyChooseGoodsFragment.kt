package com.lhr.water.ui.form.dealMaterial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lhr.water.data.Form
import com.lhr.water.data.TempDealGoodsData
import com.lhr.water.databinding.FragmentAlreadyChooseMaterialBinding
import com.lhr.water.ui.base.BaseFragment
import com.lhr.water.ui.form.FormViewModel
import com.lhr.water.util.adapter.AlreadyChooseGoodsAdapter
import com.lhr.water.util.isInput


class AlreadyChooseGoodsFragment(form: Form) : BaseFragment(), View.OnClickListener,
    AlreadyChooseGoodsAdapter.Listener {

    private val viewModel: FormViewModel by viewModels { viewModelFactory }
    private var _binding: FragmentAlreadyChooseMaterialBinding? = null
    private val binding get() = _binding!!
    private lateinit var alreadyChooseGoodsAdapter: AlreadyChooseGoodsAdapter
    private var form = form
    private var isInput = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlreadyChooseMaterialBinding.inflate(layoutInflater)

        isInput = isInput(form)
        initView()
        bindViewModel()
        return binding.root
    }

    private fun bindViewModel() {
        if (isInput){
            viewModel.formRepository.tempWaitInputGoods.observe(viewLifecycleOwner) { _ ->
                alreadyChooseGoodsAdapter.submitList(
                    viewModel.filterTempWaitInputGoods(
                        form.reportTitle.toString(),
                        form.formNumber.toString()
                    )
                )
            }
        }else{
            viewModel.formRepository.tempWaitOutputGoods.observe(viewLifecycleOwner) { _ ->
                alreadyChooseGoodsAdapter.submitList(
                    viewModel.filterTempWaitOutputGoods(
                        form.reportTitle.toString(),
                        form.formNumber.toString()
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
        alreadyChooseGoodsAdapter = AlreadyChooseGoodsAdapter(this)
        if(isInput){
            alreadyChooseGoodsAdapter.submitList(
                viewModel.filterTempWaitInputGoods(
                    form.reportTitle.toString(),
                    form.formNumber.toString()
                )
            )
        }else{
            alreadyChooseGoodsAdapter.submitList(
                viewModel.filterTempWaitOutputGoods(
                    form.reportTitle.toString(),
                    form.formNumber.toString()
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
    override fun onRemoveClick(tempDealGoodsData: TempDealGoodsData) {
        if (isInput){
            var tempArrayList = viewModel.formRepository.tempWaitInputGoods.value!!
            tempArrayList.removeIf { it == tempDealGoodsData }
            viewModel.formRepository.tempWaitInputGoods.value = tempArrayList
        }else{
            var tempArrayList = viewModel.formRepository.tempWaitOutputGoods.value!!
            tempArrayList.removeIf { it == tempDealGoodsData }
            viewModel.formRepository.tempWaitOutputGoods.value = tempArrayList
        }
    }
}
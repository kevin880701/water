package com.lhr.water.ui.form.dealMaterial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lhr.water.data.Form
import com.lhr.water.databinding.FragmentAlreadyChooseMaterialBinding
import com.lhr.water.room.StorageRecordEntity
import com.lhr.water.ui.base.BaseFragment
import com.lhr.water.util.adapter.AlreadyChooseMaterialAdapter
import com.lhr.water.util.isInput


class AlreadyChooseGoodsFragment(form: Form) : BaseFragment(), View.OnClickListener,
    AlreadyChooseMaterialAdapter.Listener {

    private val viewModel: DealMaterialViewModel by viewModels { viewModelFactory }
    private var _binding: FragmentAlreadyChooseMaterialBinding? = null
    private val binding get() = _binding!!
    private lateinit var alreadyChooseMaterialAdapter: AlreadyChooseMaterialAdapter
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

        viewModel.formRepository.tempWaitInputGoods.observe(viewLifecycleOwner) { _ ->
            alreadyChooseMaterialAdapter.submitList(
                viewModel.filterTempWaitInputGoods(
                    form.reportTitle.toString(),
                    form.formNumber.toString()
                )
            )
        }
    }

    private fun initView() {
        binding.widgetField.imageRemove.visibility = View.INVISIBLE
        initRecyclerView()
    }


    private fun initRecyclerView() {
        alreadyChooseMaterialAdapter = AlreadyChooseMaterialAdapter(this, requireContext())

        alreadyChooseMaterialAdapter.submitList(
            viewModel.filterTempWaitInputGoods(
                form.reportTitle.toString(),
                form.formNumber.toString()
            )
        )

        binding.recyclerGoods.adapter = alreadyChooseMaterialAdapter
        binding.recyclerGoods.layoutManager = LinearLayoutManager(requireActivity())
    }


    override fun onClick(v: View?) {
        when (v?.id) {
        }
    }

    /**
     * 移除暫存列表的指定內容
     */
    override fun onRemoveClick(storageRecordEntity: StorageRecordEntity) {

        var tempArrayList = viewModel.formRepository.tempWaitInputGoods.value!!
        tempArrayList.removeIf { it == storageRecordEntity }
        viewModel.formRepository.tempWaitInputGoods.value = tempArrayList
    }
}
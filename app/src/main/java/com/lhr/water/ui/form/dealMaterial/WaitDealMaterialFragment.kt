package com.lhr.water.ui.form.dealMaterial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lhr.water.data.form.BaseForm
import com.lhr.water.data.form.BaseItem
import com.lhr.water.databinding.FragmentWaitDealMaterialBinding
import com.lhr.water.room.FormEntity
import com.lhr.water.ui.base.BaseFragment
import com.lhr.water.util.adapter.WaitDealMaterialAdapter
import com.lhr.water.util.dialog.DealInputMaterialDialog
import com.lhr.water.util.dialog.DealOutputMaterialDialog
import com.lhr.water.util.isInput


class WaitDealMaterialFragment(var formEntity: FormEntity) : BaseFragment(), View.OnClickListener,
    WaitDealMaterialAdapter.Listener {

    private val viewModel: DealMaterialViewModel by viewModels { viewModelFactory }
    private var _binding: FragmentWaitDealMaterialBinding? = null
    private val binding get() = _binding!!
    private lateinit var waitDealMaterialAdapter: WaitDealMaterialAdapter
    private var isInput = true
    private lateinit var baseForm: BaseForm


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWaitDealMaterialBinding.inflate(layoutInflater)
        baseForm = formEntity.parseBaseForm()
        isInput = isInput(formEntity)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        bindViewModel()
    }

    private fun bindViewModel() {
        viewModel.formRepository.tempStorageRecordEntities.observe(viewLifecycleOwner) { newList ->
            waitDealMaterialAdapter.notifyDataSetChanged()
        }
    }

    private fun initView() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        waitDealMaterialAdapter = WaitDealMaterialAdapter(
            requireContext(), formEntity, this, viewModel, isInput
        )
        if (isInput) {
            waitDealMaterialAdapter.submitList(
                baseForm.itemDetails
            )
        } else {
            waitDealMaterialAdapter.submitList(
                baseForm.itemDetails
            )
        }
        binding.recyclerGoods.adapter = waitDealMaterialAdapter
        binding.recyclerGoods.layoutManager =
            LinearLayoutManager(this@WaitDealMaterialFragment.requireActivity())
    }


    override fun onClick(v: View?) {
        when (v?.id) {
        }
    }

    override fun onItemClick(itemDetail: BaseItem, maxQuantity: String) {

            if (isInput) {
                var goodsDialog = DealInputMaterialDialog(
                    formEntity,
                    itemDetail,
                    maxQuantity
                )
                goodsDialog.show(requireActivity().supportFragmentManager, "DealInputMaterialDialog")
            } else {
                var goodsDialog = DealOutputMaterialDialog(
                    formEntity,
                    itemDetail,
                    maxQuantity
                )
                goodsDialog.show(requireActivity().supportFragmentManager, "DealOutputMaterialDialog")
            }
    }
}
package com.lhr.water.ui.form.dealMaterial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lhr.water.data.Form
import com.lhr.water.data.ItemDetail
import com.lhr.water.databinding.FragmentWaitDealMaterialBinding
import com.lhr.water.ui.base.BaseFragment
import com.lhr.water.ui.form.FormViewModel
import com.lhr.water.util.adapter.WaitDealMaterialAdapter
import com.lhr.water.util.dialog.WaitDealMaterialDialog
import com.lhr.water.util.isInput


class WaitDealMaterialFragment(form: Form) : BaseFragment(), View.OnClickListener,
    WaitDealMaterialAdapter.Listener {

    private val viewModel: FormViewModel by viewModels { viewModelFactory }
    private var _binding: FragmentWaitDealMaterialBinding? = null
    private val binding get() = _binding!!
    private lateinit var waitDealMaterialAdapter: WaitDealMaterialAdapter
    private var form = form
    private var isInput = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWaitDealMaterialBinding.inflate(layoutInflater)
        isInput = isInput(form)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        bindViewModel()
    }

    private fun bindViewModel() {
        if (isInput) {
            viewModel.formRepository.tempWaitInputGoods.observe(viewLifecycleOwner) { newList ->
                waitDealMaterialAdapter.notifyDataSetChanged()
            }
        } else {
            viewModel.formRepository.tempWaitOutputGoods.observe(viewLifecycleOwner) { newList ->
                waitDealMaterialAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun initView() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        waitDealMaterialAdapter = WaitDealMaterialAdapter(
            requireContext(), form, this, viewModel, isInput
        )
        if (isInput) {
            waitDealMaterialAdapter.submitList(
                form.itemDetails
            )
        } else {
            waitDealMaterialAdapter.submitList(
                form.itemDetails
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

    override fun onItemClick(itemDetail: ItemDetail, maxQuantity: String) {
        val goodsDialog = WaitDealMaterialDialog(
            form,
            itemDetail,
            maxQuantity,
            isInput
        )
        goodsDialog.show(requireActivity().supportFragmentManager, "InputGoodsDialog")
    }
}
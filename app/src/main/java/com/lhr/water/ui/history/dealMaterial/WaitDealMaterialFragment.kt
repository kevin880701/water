package com.lhr.water.ui.history.dealMaterial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lhr.water.data.WaitDealGoodsData
import com.lhr.water.databinding.FragmentWaitDealMaterialBinding
import com.lhr.water.ui.base.BaseFragment
import com.lhr.water.ui.history.HistoryViewModel
import com.lhr.water.util.adapter.WaitDealMaterialAdapter
import com.lhr.water.util.dialog.WaitDealMaterialDialog
import com.lhr.water.util.isInput
import org.json.JSONObject


class WaitDealMaterialFragment(jsonString: JSONObject) : BaseFragment(), View.OnClickListener,
    WaitDealMaterialAdapter.Listener {

    private val viewModel: HistoryViewModel by viewModels { viewModelFactory }
    private var _binding: FragmentWaitDealMaterialBinding? = null
    private val binding get() = _binding!!
    private lateinit var waitDealMaterialAdapter: WaitDealMaterialAdapter
    private var jsonString = jsonString
    private var isInput = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWaitDealMaterialBinding.inflate(layoutInflater)
        isInput = isInput(jsonString)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        bindViewModel()
    }

    private fun bindViewModel() {
        if(isInput){
            viewModel.formRepository.tempWaitInputGoods.observe(viewLifecycleOwner) { newList ->
                waitDealMaterialAdapter.notifyDataSetChanged()
            }
        }else{
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
            requireContext(), jsonString["reportTitle"].toString(),
            jsonString["formNumber"].toString(), this, viewModel, isInput
        )
        if (isInput) {
            waitDealMaterialAdapter.submitList(
                viewModel.filterWaitInputGoods(
                    jsonString["reportTitle"].toString(),
                    jsonString["formNumber"].toString()
                )
            )
        } else {
            waitDealMaterialAdapter.submitList(
                viewModel.filterWaitOutputGoods(
                    jsonString["reportTitle"].toString(),
                    jsonString["formNumber"].toString()
                )
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

    override fun onItemClick(waitDealGoodsData: WaitDealGoodsData, maxQuantity: String) {
        val goodsDialog = WaitDealMaterialDialog(
            waitDealGoodsData,
            maxQuantity,
            isInput
        )
        goodsDialog.show(requireActivity().supportFragmentManager, "InputGoodsDialog")
    }
}
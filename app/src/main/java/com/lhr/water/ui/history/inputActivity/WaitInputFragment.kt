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
import com.lhr.water.ui.base.BaseFragment
import com.lhr.water.ui.history.HistoryViewModel
import com.lhr.water.util.adapter.InputAdapter
import com.lhr.water.util.dialog.InputDialog
import com.lhr.water.util.isInput
import org.json.JSONObject
import timber.log.Timber


class WaitInputFragment(jsonString: JSONObject) : BaseFragment(), View.OnClickListener,
    InputAdapter.Listener {

    private val viewModel: HistoryViewModel by viewModels { viewModelFactory }
    private var _binding: FragmentWaitInputGoodsBinding? = null
    private val binding get() = _binding!!
    private lateinit var inputAdapter: InputAdapter
    private var jsonString = jsonString
    private var isInput = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWaitInputGoodsBinding.inflate(layoutInflater)
        isInput = isInput(jsonString)
        initView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        bindViewModel()
    }

    private fun bindViewModel() {
        viewModel.formRepository.tempWaitInputGoods.observe(viewLifecycleOwner) { newList ->
            inputAdapter.notifyDataSetChanged()
        }
    }

    private fun initView() {
        initRecyclerView()
    }


    private fun initRecyclerView() {
        inputAdapter = InputAdapter(
            requireContext(), jsonString["reportTitle"].toString(),
            jsonString["formNumber"].toString(), this, viewModel, isInput
        )
        if (isInput) {
            inputAdapter.submitList(
                viewModel.filterWaitInputGoods(
                    jsonString["reportTitle"].toString(),
                    jsonString["formNumber"].toString()
                )
            )
        } else {
            inputAdapter.submitList(
                viewModel.filterWaitOutputGoods(
                    jsonString["reportTitle"].toString(),
                    jsonString["formNumber"].toString()
                )
            )
        }
        binding.recyclerGoods.adapter = inputAdapter
        binding.recyclerGoods.layoutManager =
            LinearLayoutManager(this@WaitInputFragment.requireActivity())
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.linearLayoutGoodInput -> {

            }
        }
    }

    override fun onItemClick(waitDealGoodsData: WaitDealGoodsData, maxQuantity: String) {
        val goodsDialog = InputDialog(
            waitDealGoodsData,
            maxQuantity,
            isInput
        )
        goodsDialog.show(requireActivity().supportFragmentManager, "InputGoodsDialog")
    }
}
package com.lhr.water.ui.goods.waitOutputGoods

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lhr.water.data.WaitDealGoodsData
import com.lhr.water.databinding.FragmentWaitOutputGoodsBinding
import com.lhr.water.ui.base.BaseFragment
import com.lhr.water.ui.goods.GoodsViewModel
import com.lhr.water.util.adapter.WaitOutputGoodsAdapter
import com.lhr.water.util.dialog.OutputGoodsDialog
import org.json.JSONObject

class WaitOutputGoodsFragment : BaseFragment(), View.OnClickListener, WaitOutputGoodsAdapter.Listener, OutputGoodsDialog.Listener {

    private var _binding: FragmentWaitOutputGoodsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: GoodsViewModel by viewModels { viewModelFactory }
    private lateinit var waitOutputGoodsAdapter: WaitOutputGoodsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWaitOutputGoodsBinding.inflate(layoutInflater)

        initView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        bindViewModel()
    }

    private fun bindViewModel() {
        viewModel.formRepository.waitOutputGoods.observe(viewLifecycleOwner) { newList ->
            waitOutputGoodsAdapter.submitList(newList)
        }
    }

    private fun initView() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        waitOutputGoodsAdapter = WaitOutputGoodsAdapter(this)
        waitOutputGoodsAdapter.submitList(viewModel.getWaitOutputGoods())
        binding.recyclerGoods.adapter = waitOutputGoodsAdapter
        binding.recyclerGoods.layoutManager = LinearLayoutManager(activity)
    }
    override fun onClick(v: View?) {
        when (v?.id) {
        }
    }

    override fun onItemClick(waitDealGoodsData: WaitDealGoodsData) {

        val outputGoodsDialog = OutputGoodsDialog(
            waitDealGoodsData,
            listener = this
        )
        outputGoodsDialog.show(requireActivity().supportFragmentManager, "OutputGoodsDialog")
    }

    override fun onGoodsDialogConfirm(formItemJson: JSONObject) {

    }
}
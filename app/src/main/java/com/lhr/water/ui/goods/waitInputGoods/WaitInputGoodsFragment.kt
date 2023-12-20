package com.lhr.water.ui.goods.waitInputGoods

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.lhr.water.data.WaitDealGoodsData
import com.lhr.water.databinding.FragmentWaitInputGoodsBinding
import com.lhr.water.ui.base.BaseFragment
import com.lhr.water.ui.goods.GoodsViewModel
import com.lhr.water.util.adapter.WaitInputGoodsAdapter
import com.lhr.water.util.dialog.InputGoodsDialog
import com.lhr.water.util.showToast
import org.json.JSONObject
import timber.log.Timber

class WaitInputGoodsFragment : BaseFragment(), View.OnClickListener, WaitInputGoodsAdapter.Listener, InputGoodsDialog.Listener {

    private var _binding: FragmentWaitInputGoodsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: GoodsViewModel by viewModels { viewModelFactory }
    private lateinit var waitInputGoodsAdapter: WaitInputGoodsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWaitInputGoodsBinding.inflate(layoutInflater)

        initView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        bindViewModel()
    }

    private fun bindViewModel() {
        viewModel.formRepository.waitInputGoods.observe(viewLifecycleOwner) { newList ->
            waitInputGoodsAdapter.submitList(newList)
        }
    }

    private fun initView() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        waitInputGoodsAdapter = WaitInputGoodsAdapter(this)
        waitInputGoodsAdapter.submitList(viewModel.getWaitInputGoods())
        binding.recyclerGoods.adapter = waitInputGoodsAdapter
        binding.recyclerGoods.layoutManager = LinearLayoutManager(activity)
    }
    override fun onClick(v: View?) {
        when (v?.id) {
        }
    }

    override fun onItemClick(waitDealGoodsData: WaitDealGoodsData) {
        val goodsDialog = InputGoodsDialog(
            waitDealGoodsData,
            listener = this
        )
        goodsDialog.show(requireActivity().supportFragmentManager, "InputGoodsDialog")
    }


    override fun onGoodsDialogConfirm(formItemJson: JSONObject) {
    }
}
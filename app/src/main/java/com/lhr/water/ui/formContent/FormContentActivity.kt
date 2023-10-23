package com.lhr.water.ui.formContent

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.lhr.water.R
import com.lhr.water.databinding.ActivityFormContentBinding
import com.lhr.water.ui.base.APP
import com.lhr.water.ui.base.BaseActivity
import com.lhr.water.util.recyclerViewAdapter.MapChooseAdapter

class FormContentActivity : BaseActivity(), View.OnClickListener {
    private val viewModel: FormContentViewModel by viewModels{(applicationContext as APP).appContainer.viewModelFactory}
    private var _binding: ActivityFormContentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFormContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ResourcesCompat.getColor(resources, R.color.seed, null)
    }

    private fun initView() {
        binding.widgetTitleBar.textTitle.text = getString(R.string.map_choose)
        binding.widgetTitleBar.imageBack.visibility = View.VISIBLE
        initRecyclerView()
    }
    fun initRecyclerView() {
//        mapChooseAdapter = MapChooseAdapter(this)
//        binding.recyclerMap.adapter = mapChooseAdapter
//        binding.recyclerMap.layoutManager = LinearLayoutManager(this)
//        viewModel.setMapList(region)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.imageBack -> {
                onBackPressedCallback.handleOnBackPressed()
            }
        }
    }
}
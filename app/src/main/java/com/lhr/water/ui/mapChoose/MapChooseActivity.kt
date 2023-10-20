package com.lhr.water.ui.mapChoose

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.lhr.water.R
import com.lhr.water.databinding.ActivityMapChooseBinding
import com.lhr.water.model.FakerData
import com.lhr.water.model.FormData
import com.lhr.water.ui.base.APP
import com.lhr.water.ui.base.BaseActivity
import com.lhr.water.ui.map.MapActivity
import com.lhr.water.util.recyclerViewAdapter.MapChooseAdapter
import com.lhr.water.util.recyclerViewAdapter.RegionChooseAdapter
import timber.log.Timber

class MapChooseActivity : BaseActivity(), MapChooseAdapter.Listener {

    private val viewModel: MapChooseViewModel by viewModels{(applicationContext as APP).appContainer.viewModelFactory}
    private var _binding: ActivityMapChooseBinding? = null
    private val binding get() = _binding!!
    lateinit var region: String
    lateinit var mapChooseAdapter: MapChooseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMapChooseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ResourcesCompat.getColor(resources, R.color.seed, null)


        region = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("region", String::class.java) as String
        } else {
            intent.getSerializableExtra("region") as String
        }
        bindViewModel()
        initView()
    }


    private fun bindViewModel() {
        viewModel.mapList.observe(this){ list ->
            mapChooseAdapter.submitList(list)
        }
    }

    private fun initView() {
        initRecyclerView()
    }
    fun initRecyclerView() {
        mapChooseAdapter = MapChooseAdapter(this)
        binding.recyclerMap.adapter = mapChooseAdapter
        binding.recyclerMap.layoutManager = LinearLayoutManager(this)
        viewModel.setMapList(region)
    }

    override fun onItemClick(item: String) {
        println("點擊")
        val intent = Intent(this, MapActivity::class.java)
        intent.putExtra("region", region)
        intent.putExtra("map", item)
        startActivity(intent)
    }

    override fun onItemLongClick(item: String) {
        println("長按")
    }

}
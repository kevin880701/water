package com.lhr.water.ui.storageContent

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.lhr.water.R
import com.lhr.water.databinding.ActivityCoverBinding
import com.lhr.water.databinding.ActivityStorageContentBinding
import com.lhr.water.model.Model
import com.lhr.water.ui.base.APP
import com.lhr.water.ui.base.BaseActivity
import com.lhr.water.ui.login.LoginActivity
import com.lhr.water.ui.login.LoginViewModel
import com.lhr.water.ui.map.MapActivity
import com.lhr.water.ui.storageGoodInput.StorageGoodInputActivity
import com.lhr.water.util.adapter.RegionChooseAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class StorageContentActivity : BaseActivity(), View.OnClickListener {

    private val viewModel: StorageContentViewModel by viewModels{(applicationContext as APP).appContainer.viewModelFactory}

    private var _binding: ActivityStorageContentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityStorageContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ResourcesCompat.getColor(resources, R.color.seed, null)

        initView()
    }

    private fun initView() {
        binding.widgetTitleBar.textTitle.text = getString(R.string.storage_content)
        binding.widgetTitleBar.imageBack.visibility = View.VISIBLE
        setupBackButton(binding.widgetTitleBar.imageBack)
        binding.linearLayoutGoodInput.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.linearLayoutGoodInput ->{
                val intent = Intent(this, StorageGoodInputActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
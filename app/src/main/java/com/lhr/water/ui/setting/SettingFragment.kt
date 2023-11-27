package com.lhr.water.ui.setting

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.lhr.water.R
import com.lhr.water.data.repository.FormRepository
import com.lhr.water.databinding.FragmentSettingBinding
import com.lhr.water.ui.base.BaseFragment
import java.io.InputStream

class SettingFragment : BaseFragment(), View.OnClickListener {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingViewModel by viewModels { viewModelFactory }
    val formRepository: FormRepository by lazy { FormRepository.getInstance(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(layoutInflater)

        initView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
    }

    private fun bindViewModel() {
    }

    private fun initView() {
        binding.widgetTitleBar.textTitle.text = requireActivity().getString(R.string.setting)

        binding.constraintUpdate.setOnClickListener(this)
        binding.constraintUpload.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.constraintUpload -> {
                saveFile.launch(null)
            }

            R.id.constraintUpdate -> {
                pickFile.launch("application/json")
            }
        }
    }

    /**
     * 選擇資料夾並儲存JSON檔
     */
    private val saveFile =
        registerForActivityResult(ActivityResultContracts.OpenDocumentTree()) { uri ->
            uri?.let {
                // 將 JSONObject 寫入到資料夾
                viewModel.writeJsonObjectToFolder(requireContext(), it)
            }
        }

    /**
     * 選擇JSON檔案並讀取
     */
    private val pickFile =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                val inputStream: InputStream? =
                    requireActivity().contentResolver.openInputStream(it)
                val jsonContent = viewModel.readJsonFromInputStream(inputStream)
                Log.d("MainActivity", "Selected JSON content:\n$jsonContent")
            }
        }


}
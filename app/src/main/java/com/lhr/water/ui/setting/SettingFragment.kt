package com.lhr.water.ui.setting

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.lhr.water.R
import com.lhr.water.databinding.FragmentSettingBinding
import com.lhr.water.ui.base.BaseFragment

class SettingFragment : BaseFragment(), View.OnClickListener {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingViewModel by viewModels { viewModelFactory }
    /**
     * 選擇JSON檔案並讀取
     */
    private lateinit var pickFile: ActivityResultLauncher<String>


    /**
     * 選擇資料夾並儲存JSON檔
     */
    private lateinit var saveFile: ActivityResultLauncher<Uri?>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(layoutInflater)

        pickFile =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                uri?.let {
                    viewModel.updateFormData2(requireContext(), it)
                }
            }

        saveFile =
            registerForActivityResult(ActivityResultContracts.OpenDocumentTree()) { uri ->
                uri?.let {
                    // 將 JSONObject 寫入到資料夾
                    viewModel.writeJsonObjectToFolderLocal(requireContext(), it)
                }
            }

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
        binding.constraintBackup.setOnClickListener(this)
        binding.constraintUpdateLocal.setOnClickListener(this)
        binding.constraintBackupLocal.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.constraintBackup -> {
//                saveFile.launch(null)

                viewModel.writeJsonObjectToFolder(requireActivity())
            }
            R.id.constraintUpdate -> {
//                pickFile.launch("application/json")
                viewModel.uploadFiles(this.requireActivity())
            }
            R.id.constraintBackupLocal -> {
                saveFile.launch(null)
            }
            R.id.constraintUpdateLocal -> {
                pickFile.launch("application/json")
            }
        }
    }
}
package com.lhr.water.util.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.lhr.water.R
import com.lhr.water.databinding.DialogEditStorageNameBinding
import com.lhr.water.room.SqlDatabase
import com.lhr.water.room.StorageEntity
import com.lhr.water.ui.base.APP
import com.lhr.water.ui.base.AppViewModelFactory
import com.lhr.water.ui.map.MapViewModel
import com.lhr.water.util.showToast

class EditStorageNameDialog(
    var region: String,
    var map: String,
    private var oldStorageEntity: StorageEntity
) : DialogFragment(), View.OnClickListener {

    private var dialog: AlertDialog? = null
    private var _binding: DialogEditStorageNameBinding? = null
    private val binding get() = _binding!!

    private val viewModelFactory: AppViewModelFactory
        get() = (requireContext().applicationContext as APP).appContainer.viewModelFactory
    private val viewModel: MapViewModel by viewModels { viewModelFactory }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogEditStorageNameBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(activity)
        builder.setCancelable(false)

        initView()
        builder.setView(binding.root)
        dialog = builder.create()

        return builder.create()
    }

    fun initView() {
        binding.widgetTitleBar.textTitle.text =
            activity?.resources?.getString(R.string.edit_storage_name)
        binding.widgetTitleBar.imageCancel.visibility = View.VISIBLE

        binding.buttonConfirm.setOnClickListener(this)
        binding.widgetTitleBar.imageCancel.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonConfirm -> {
//                if (SqlDatabase.getInstance().getStorageDao().isStorageNameExist(binding.editStorageName.text.toString())){
//                    showToast(requireContext(), "儲櫃名稱已存在")
//                }else{
////                    SqlDatabase.getInstance().getStorageDao().updateStorage(
////                        regionName = oldStorageEntity.regionName,
////                        mapName = oldStorageEntity.mapName,
////                        oldStorageName = oldStorageEntity.storageName,
////                        newStorageName = binding.editStorageName.text.toString()
////                    )
//                    viewModel.regionRepository.loadStorageInformation()
//                    this.dismiss()
//                }
            }

            R.id.buttonCancel -> {
                this.dismiss()
            }
        }
    }
}
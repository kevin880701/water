package com.lhr.water.util.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.lhr.water.R
import com.lhr.water.databinding.DialogConfirmCancelBinding
import com.lhr.water.room.SqlDatabase
import com.lhr.water.ui.base.APP
import com.lhr.water.ui.base.AppViewModelFactory
import com.lhr.water.ui.history.HistoryViewModel

class ConfirmCancelDialog(
    val region: String,
    val map: String,
    val storageName: String
) : DialogFragment(), View.OnClickListener {

    private var dialog: AlertDialog? = null
    private var _binding: DialogConfirmCancelBinding? = null
    private val binding get() = _binding!!

    private val viewModelFactory: AppViewModelFactory
        get() = (requireContext().applicationContext as APP).appContainer.viewModelFactory
    private val viewModel: HistoryViewModel by viewModels { viewModelFactory }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogConfirmCancelBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(activity)
        builder.setCancelable(false)

        initView()
        builder.setView(binding.root)
        dialog = builder.create()
        return builder.create()
    }

    fun initView() {
        binding.textMessage.text = requireActivity().getString(R.string.check_delete)

        binding.buttonConfirm.setOnClickListener(this)
        binding.buttonCancel.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonConfirm -> {
                SqlDatabase.getInstance().getStorageDao().deleteByRegionMapAndStorage(region, map, storageName)
                viewModel.regionRepository.loadStorageInformation2()
                this.dismiss()
            }

            R.id.buttonCancel -> {
                this.dismiss()
            }
        }
    }
}
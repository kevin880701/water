package com.lhr.water.util.dialog

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.lhr.water.R
import com.lhr.water.databinding.DialogAddStorageDataBinding
import com.lhr.water.room.SqlDatabase
import com.lhr.water.room.StorageEntity
import com.lhr.water.ui.base.APP
import com.lhr.water.ui.base.AppViewModelFactory
import com.lhr.water.ui.map.AddPointActivity
import com.lhr.water.ui.map.MapViewModel
import com.lhr.water.util.showToast
import timber.log.Timber

class AddStorageDataDialog(
    val region: String,
    val map: String
) : DialogFragment(), View.OnClickListener {

    private var dialog: AlertDialog? = null
    private var _binding: DialogAddStorageDataBinding? = null
    private val binding get() = _binding!!

    private val viewModelFactory: AppViewModelFactory
        get() = (requireContext().applicationContext as APP).appContainer.viewModelFactory
    private val viewModel: MapViewModel by viewModels { viewModelFactory }
    var pointX: Float? = 0f
    var pointY: Float? = 0f

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogAddStorageDataBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(activity)
        builder.setCancelable(false)

        initView()
        builder.setView(binding.root)
        dialog = builder.create()
        return builder.create()
    }

    fun initView() {
        binding.widgetTitleBar.textTitle.text =
            activity?.resources?.getString(R.string.add_storage_data)
        binding.widgetTitleBar.imageCancel.visibility = View.VISIBLE

        binding.imageAddPoint.setOnClickListener(this)
        binding.buttonConfirm.setOnClickListener(this)
        binding.widgetTitleBar.imageCancel.setOnClickListener(this)
    }

    private val addPointLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            pointX = data?.getFloatExtra("pointX", 0.0f)
            pointY = data?.getFloatExtra("pointY", 0.0f)
            binding.textPoint.text = "$pointX, $pointY"
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonConfirm -> {
                if(binding.editStorageName.text.toString() == ""){
                    showToast(this.requireContext(), "儲櫃代碼已存在")
                }else if(isStorageNumExists(viewModel.regionRepository.storageEntities.value!!, binding.editStorageName.text.toString())){
                    showToast(this.requireContext(), "儲櫃代碼已存在")
                }else{
                    SqlDatabase.getInstance().getStorageDao().insertStorageEntity(
                        StorageEntity(
                            regionName = region,
                            mapName = map,
                            storageName = binding.editStorageName.text.toString(),
                            storageX = pointX.toString(),
                            storageY = pointY.toString()
                        )
                    )
                    viewModel.regionRepository.loadStorageInformation2()
                    this.dismiss()
                }
            }

            R.id.buttonCancel -> {
                this.dismiss()
            }

            R.id.imageAddPoint -> {
                Timber.d("imageAddPoint CLICK!!!")
                val intent = Intent(context, AddPointActivity::class.java)
                intent.putExtra("region", region)
                intent.putExtra("map", map)
                addPointLauncher?.launch(intent)
            }
        }
    }

    fun isStorageNumExists(storageEntities: ArrayList<StorageEntity>, targetStorageName: String): Boolean {
        return storageEntities.any { storageEntity ->
            storageEntity.storageName == targetStorageName
        }
    }
}
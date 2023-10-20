package com.lhr.water.ui.qrCode

import android.Manifest
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import com.google.zxing.BarcodeFormat
import com.google.zxing.ResultPoint
import com.google.zxing.client.android.BeepManager
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import com.lhr.water.R
import com.lhr.water.databinding.ActivityQrcodeBinding
import com.lhr.water.listener.OnPermissionListener
import com.lhr.water.manager.PermissionManager
import com.lhr.water.ui.base.APP
import com.lhr.water.ui.base.BaseActivity
import timber.log.Timber
import java.lang.reflect.Parameter
import java.util.Arrays

class QrcodeActivity : BaseActivity(), OnPermissionListener {

    private val viewModel: QrCodeViewModel by viewModels{(applicationContext as APP).appContainer.viewModelFactory}
    private var _binding: ActivityQrcodeBinding? = null
    private val binding get() = _binding!!
    private var beepManager: BeepManager? = null

    val PERMISSION_REQUEST_START = 10001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityQrcodeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ResourcesCompat.getColor(resources, R.color.black, null)
        initView()

        PermissionManager.instance!!.setOnPermissionListener(this)

        PermissionManager.instance!!.checkPermission(
            this, arrayOf<String>(
                Manifest.permission.CAMERA
            ) as Array<String?>?, PERMISSION_REQUEST_START
        )
    }


    private fun initView() {
        beepManager = BeepManager(this)
        val formats: Collection<BarcodeFormat> = Arrays.asList(BarcodeFormat.QR_CODE)
        binding.scanner.barcodeView.decoderFactory = DefaultDecoderFactory(formats)
        binding.scanner.decodeContinuous(callback)
        binding.scanner.statusView.text = ""
        binding.scanner.resume()
    }

    private val callback: BarcodeCallback = object : BarcodeCallback {
        override fun barcodeResult(result: BarcodeResult) {
            val codeString = result.text

            Timber.e("getString : $codeString")
        }

        override fun possibleResultPoints(resultPoints: List<ResultPoint>) {}
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionManager.instance!!
            .onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
    override fun onPermissionRequest(isSuccess: Boolean, requestCode: Int) {
        if (requestCode == PERMISSION_REQUEST_START) {
            if (isSuccess) {
                initView()
            } else {
                onBackPressed()
            }
        }
    }
}
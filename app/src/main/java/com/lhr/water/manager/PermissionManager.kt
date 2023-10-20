package com.lhr.water.manager

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.lhr.water.R
import com.lhr.water.listener.OnPermissionListener

class PermissionManager {
    private var per = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    private var onPermissionListener: OnPermissionListener? = null
    fun setOnPermissionListener(onPermissionListener: OnPermissionListener?) {
        this.onPermissionListener = onPermissionListener
    }

    fun setPermissionManager(per: Array<String>) {
        this.per = per
    }

    /**
     * 權限要求
     * @param permission  需要的權限
     * @param requestCode 事件代碼
     */
    fun checkPermission(
        activity: AppCompatActivity?,
        permission: Array<String?>?,
        requestCode: Int
    ) {
        ActivityCompat.requestPermissions(
            activity!!,
            permission!!, requestCode
        )
    }

    /**
     * 權限回應處理
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray,
        activity: AppCompatActivity
    ) {
        var hasAllGranted = true
        for (i in grantResults.indices) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                hasAllGranted = false
                //請求失敗，並且勾選不再詢問時
                if (!ActivityCompat.shouldShowRequestPermissionRationale(
                        activity,
                        permissions[i]!!
                    )
                ) {
                    DialogManager.showBaseDialog(
                        activity,
                        activity.getString(R.string.permission_error),
                        activity.getString(R.string.confirm),
                        DialogInterface.OnClickListener { dialog, which ->
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            val uri = Uri.fromParts("package", activity.packageName, null)
                            intent.data = uri
                            activity.startActivity(intent)
                        },
                        activity.getString(R.string.cancel),
                        DialogInterface.OnClickListener { dialog, which ->
                            requestPermissionsGo(
                                false,
                                requestCode
                            )
                        })
                    //請求失敗，無勾選不再詢問時
                } else {
                    checkPermission(activity, permissions, requestCode)
                }
                break
            }
        }
        //請求成功
        if (hasAllGranted) {
            requestPermissionsGo(true, requestCode)
        }
    }

    /**
     * 權限要求狀況
     * @param isSuccess  是否同意
     * @param requestCode  事件代碼
     */
    fun requestPermissionsGo(isSuccess: Boolean, requestCode: Int) {
        if (onPermissionListener != null) {
            onPermissionListener?.onPermissionRequest(isSuccess, requestCode)
        }
    }

    companion object {
        private var permissionManager: PermissionManager? = null
        val instance: PermissionManager?
            get() {
                if (permissionManager == null) {
                    permissionManager = PermissionManager()
                }
                return permissionManager
            }
    }
}
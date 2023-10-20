package com.lhr.water.listener

interface OnPermissionListener {
    fun onPermissionRequest(isSuccess: Boolean, requestCode: Int)
}
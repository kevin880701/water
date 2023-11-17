package com.lhr.water.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log


class YourBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val receivedData = intent.getStringExtra("key_name")

        Log.d("YourBroadcastReceiver", "Received broadcast")

        if (receivedData != null) {
            Log.d("YourBroadcastReceiver", "Received data: $receivedData")
        } else {
            Log.d("YourBroadcastReceiver", "No data received")
        }
        // 处理接收到的数据
    }
}
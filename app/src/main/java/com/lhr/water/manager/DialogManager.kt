package com.lhr.water.manager

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.lhr.water.R
import com.lhr.water.databinding.DialogConfirmBinding
import com.lhr.water.ui.base.BaseActivity
import java.util.Objects


object DialogManager {
    private var dialog: AlertDialog? = null
    private var loadDialog: AlertDialog? = null

    /**
     * 提示對話框
     *
     * @param message     訊息
     * @param posBtn      右邊按鍵文字
     * @param posListener 右邊按鍵事件
     * @param negBtn      中間按鍵文字
     * @param negListener 中間按鍵事件
     * @param neuBtn      左邊按鍵文字
     * @param neuListener 左邊按鍵事件
     */
    fun showBaseDialog(
        activity: AppCompatActivity,
        message: String?,
        posBtn: String?,
        posListener: DialogInterface.OnClickListener?,
        negBtn: String?,
        negListener: DialogInterface.OnClickListener?,
        neuBtn: String?,
        neuListener: DialogInterface.OnClickListener?
    ) {
        activity.runOnUiThread {
            cancelDialog(activity)
            val builder = AlertDialog.Builder(activity)
            builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton(posBtn, posListener)
                .setNegativeButton(negBtn, negListener)
                .setNeutralButton(neuBtn, neuListener)
            dialog = builder.create()
            dialog?.show()
        }
    }

    fun showBaseDialog(
        activity: AppCompatActivity,
        message: String?,
        posBtn: String?,
        posListener: DialogInterface.OnClickListener?,
        negBtn: String?,
        negListener: DialogInterface.OnClickListener?
    ) {
        activity.runOnUiThread {
            cancelDialog(activity)
            val builder = AlertDialog.Builder(activity)
            builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton(posBtn, posListener)
                .setNegativeButton(negBtn, negListener)
            dialog = builder.create()
            dialog?.show()
        }
    }

    fun showBaseDialog(
        activity: Activity,
        message: String?,
        posBtn: String?,
        posListener: DialogInterface.OnClickListener?
    ) {
        activity.runOnUiThread {
            cancelDialog(activity)
            val builder = AlertDialog.Builder(activity)
            builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton(posBtn, posListener)
            dialog = builder.create()
            dialog?.show()
        }
    }

    fun showConfirmDialog(
        activity: AppCompatActivity,
        title: String?,
        content: String?,
        onClickListener: View.OnClickListener?
    ) {
        activity.runOnUiThread {
//            cancelLoadDialog(activity)
            val builder = AlertDialog.Builder(activity)
            builder.setCancelable(false)
            val binding: DialogConfirmBinding = DataBindingUtil.inflate(
                LayoutInflater.from(activity), R.layout.dialog_confirm, null, false
            )
            binding.title.setText(title)
            binding.content.setText(content)
            binding.confirm.setOnClickListener(View.OnClickListener { v ->
                dialog!!.dismiss()
                onClickListener?.onClick(v)
            })
            binding.close.setOnClickListener(View.OnClickListener { v ->
                dialog!!.dismiss()
                onClickListener?.onClick(v)
            })
            builder.setView(binding.getRoot())
            dialog = builder.create()
            dialog?.setCancelable(false)
            Objects.requireNonNull(dialog?.window)
                ?.setBackgroundDrawableResource(R.color.transparent)
            dialog?.show()
            val lp = WindowManager.LayoutParams()
            val dm = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(dm)
            lp.copyFrom(dialog?.getWindow()!!.attributes)
            lp.width = (dm.widthPixels * 0.7).toInt()
            dialog?.getWindow()!!.attributes = lp
        }
    }

//    fun showTicketDialog(
//        activity: AppCompatActivity,
//        title: String?,
//        list: List<CouponData?>,
//        onAdapterItemListener: OnAdapterItemListener?
//    ) {
//        activity.runOnUiThread {
//            cancelLoadDialog(activity)
//            val builder = AlertDialog.Builder(activity)
//            builder.setCancelable(false)
//            val binding: DialogSelectTicketBinding = DataBindingUtil.inflate(
//                LayoutInflater.from(activity), R.layout.dialog_select_ticket, null, false
//            )
//            binding.title.setText(title)
//            binding.content.setText(
//                list.size.toString() + binding.getRoot().getResources().getString(R.string.s_ticket)
//            )
//            binding.list.setLayoutManager(LinearLayoutManager(activity))
//            val adapter = TicketAdapter(list)
//            binding.list.setAdapter(adapter)
//            adapter.setOnAdapterItemListener(object : OnAdapterItemListener() {
//                fun onItemClick(pos: Int) {
//                    dialog!!.dismiss()
//                    if (onAdapterItemListener != null) {
//                        onAdapterItemListener.onItemClick(pos)
//                    }
//                }
//            })
//            binding.close.setOnClickListener(View.OnClickListener { dialog!!.dismiss() })
//            builder.setView(binding.getRoot())
//            dialog = builder.create()
//            dialog?.setCancelable(false)
//            Objects.requireNonNull(dialog?.getWindow())
//                .setBackgroundDrawableResource(R.color.transparent)
//            dialog?.show()
//            val lp = WindowManager.LayoutParams()
//            val dm = DisplayMetrics()
//            activity.windowManager.defaultDisplay.getMetrics(dm)
//            lp.copyFrom(dialog?.getWindow()!!.attributes)
//            lp.width = (dm.widthPixels * 0.7).toInt()
//            dialog?.getWindow()!!.attributes = lp
//        }
//    }
//
//    fun showSelectDialog(
//        activity: AppCompatActivity,
//        title: String?,
//        content: String?,
//        onClickListener: View.OnClickListener?,
//        onCancelListener: View.OnClickListener?
//    ) {
//        activity.runOnUiThread {
//            cancelLoadDialog(activity)
//            val builder = AlertDialog.Builder(activity)
//            builder.setCancelable(false)
//            val binding: DialogSelectBinding = DataBindingUtil.inflate(
//                LayoutInflater.from(activity), R.layout.dialog_select, null, false
//            )
//            binding.title.setText(title)
//            binding.content.setText(content)
//            binding.yes.setOnClickListener(View.OnClickListener { v ->
//                dialog!!.dismiss()
//                onClickListener?.onClick(v)
//            })
//            binding.no.setOnClickListener(View.OnClickListener { v ->
//                dialog!!.dismiss()
//                onCancelListener?.onClick(v)
//            })
//            binding.close.setOnClickListener(View.OnClickListener { dialog!!.dismiss() })
//            builder.setView(binding.getRoot())
//            dialog = builder.create()
//            dialog?.setCancelable(false)
//            Objects.requireNonNull(dialog?.getWindow())
//                .setBackgroundDrawableResource(R.color.transparent)
//            dialog?.show()
//            val lp = WindowManager.LayoutParams()
//            val dm = DisplayMetrics()
//            activity.windowManager.defaultDisplay.getMetrics(dm)
//            lp.copyFrom(dialog?.getWindow()!!.attributes)
//            lp.width = (dm.widthPixels * 0.7).toInt()
//            dialog?.getWindow()!!.attributes = lp
//        }
//    }

//    fun showQrcodeDialog(
//        activity: AppCompatActivity, code: String
//    ) {
//        activity.runOnUiThread {
//            cancelDialog(activity)
//            val builder = AlertDialog.Builder(activity)
//            builder.setCancelable(false)
//            val binding: DialogShowQrcodeBinding = DataBindingUtil.inflate(
//                LayoutInflater.from(activity), R.layout.dialog_show_qrcode, null, false
//            )
//            try {
//                val encoder = BarcodeEncoder()
//                val bm = encoder.encodeBitmap(
//                    code + Parameter.QRCODE_SPLITE + UserManager.getInstance().getTokenData()
//                        .getToken(),
//                    BarcodeFormat.QR_CODE,
//                    Calculation.convertDpToPixel(180, activity) as Int,
//                    Calculation.convertDpToPixel(180, activity) as Int
//                )
//                if (bm != null) {
//                    binding.img.setImageBitmap(bm)
//                }
//            } catch (e: Exception) {
//                Log.e("showQrcodeDialog", e.toString())
//            }
//            binding.close.setOnClickListener(View.OnClickListener { dialog!!.dismiss() })
//            builder.setView(binding.getRoot())
//            dialog = builder.create()
//            dialog?.setCancelable(false)
//            Objects.requireNonNull(dialog?.getWindow())
//                .setBackgroundDrawableResource(R.color.transparent)
//            dialog?.show()
//        }
//    }

    /**
     * 關閉對話框
     */
    fun cancelDialog(activity: Activity?) {
        if (dialog != null) {
            dialog!!.dismiss()
        }
    }

    /**
     * 顯示LOADING畫面
     */
//    fun showLoadDialog(activity: AppCompatActivity) {
//        activity.runOnUiThread {
//            cancelLoadDialog(activity)
//            val builder = AlertDialog.Builder(activity)
//            builder.setCancelable(false)
//            val bar = ProgressBar(activity)
//            bar.indeterminateDrawable.setColorFilter(
//                Color.BLACK,
//                PorterDuff.Mode.MULTIPLY
//            )
//            builder.setView(bar)
//            loadDialog = builder.create()
//            Objects.requireNonNull(loaddialog?.getWindow())
//                .setBackgroundDrawableResource(R.color.transparent)
//            Objects.requireNonNull(loaddialog?.getWindow())
//                .clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
//            loaddialog?.show()
//        }
//    }
//
//    /**
//     * 關閉LOADING畫面
//     */
//    fun cancelLoadDialog(activity: AppCompatActivity) {
//        activity.runOnUiThread { if (loadDialog != null) loadDialog!!.dismiss() }
//    }

    private var bottomSheetDialog: BottomSheetDialog? = null
    fun showBottomSheet(
        activity: BaseActivity, v: View?
    ) {
        cancelBottomSheet(activity)
        bottomSheetDialog = BottomSheetDialog(activity)
        val displayMetrics = DisplayMetrics()
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        bottomSheetDialog!!.behavior.peekHeight = height
        bottomSheetDialog!!.setContentView(v!!)
        bottomSheetDialog!!.show()
    }

    fun cancelBottomSheet(
        activity: BaseActivity?
    ) {
        if (bottomSheetDialog != null && bottomSheetDialog!!.isShowing) {
            bottomSheetDialog!!.dismiss()
        }
    }
}
package com.lhr.water.util.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lhr.water.R
import com.lhr.water.data.Form
import com.lhr.water.databinding.ItemHistoryBinding
import org.json.JSONObject

class HistoryAdapter(val listener: Listener, context: Context) :
    ListAdapter<Form, HistoryAdapter.ViewHolder>(LOCK_DIFF_UTIL) {
    var context = context

    companion object {
        val LOCK_DIFF_UTIL = object : DiffUtil.ItemCallback<Form>() {
            override fun areItemsTheSame(oldItem: Form, newItem: Form): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Form,
                newItem: Form
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(form: Form) {
            binding.textReportTitle.text = form.reportId
            binding.textFormNumber.text = form.formNumber
            binding.textDate.text = form.date
            when (form.dealStatus) {
                context.getString(R.string.wait_deal) -> {
                    binding.imageStatus.setImageDrawable(context.getDrawable(R.drawable.red_light))
                    binding.imageDealGoods.visibility = View.INVISIBLE
                }

                context.getString(R.string.now_deal) -> {
                    binding.imageStatus.setImageDrawable(context.getDrawable(R.drawable.yellow_light))
                    binding.imageDealGoods.visibility = View.VISIBLE
                }

                context.getString(R.string.complete_deal) -> {
                    binding.imageStatus.setImageDrawable(context.getDrawable(R.drawable.green_light))
                    binding.imageDealGoods.visibility = View.INVISIBLE
                }
            }

//            binding.imageDealGoods.setOnClickListener {
//                listener.onDealGoodsClick(getItem(adapterPosition))
//            }
            binding.root.setOnClickListener {
                listener.onItemClick(getItem(adapterPosition))
            }
        }
    }

    interface Listener {
        fun onItemClick(item: Form)
        fun onDealGoodsClick(item: JSONObject)
    }
}
package com.lhr.water.util.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lhr.water.R
import com.lhr.water.databinding.ItemHistoryBinding
import org.json.JSONObject

class HistoryAdapter(val listener: Listener, context: Context): ListAdapter<JSONObject, HistoryAdapter.ViewHolder>(LOCK_DIFF_UTIL) {
    var context = context
    companion object{
        val LOCK_DIFF_UTIL = object : DiffUtil.ItemCallback<JSONObject>() {
            override fun areItemsTheSame(oldItem: JSONObject, newItem: JSONObject): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: JSONObject,
                newItem: JSONObject
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }


    interface Listener{
        fun onItemClick(item: JSONObject)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemHistoryBinding): RecyclerView.ViewHolder(binding.root){
        init {
            // bindingAdapterPosition無法使用，所以用adapterPosition替代
            binding.root.setOnClickListener {
                listener.onItemClick(getItem(adapterPosition))
            }
        }

        fun bind(json: JSONObject){
            binding.textReportTitle.text = json.getString("reportTitle")
            binding.textFormNumber.text = json.getString("deliveryNumber")
            binding.textDate.text = json.getString("date")
            when(json.getString("dealStatus")){
                context.getString(R.string.wait_deal) -> {binding.imageStatus.setImageDrawable(context.getDrawable(R.drawable.red_light))}
                context.getString(R.string.now_deal) -> {binding.imageStatus.setImageDrawable(context.getDrawable(R.drawable.yellow_light))}
                context.getString(R.string.complete_deal) -> {binding.imageStatus.setImageDrawable(context.getDrawable(R.drawable.green_light))}
            }
        }
    }
}
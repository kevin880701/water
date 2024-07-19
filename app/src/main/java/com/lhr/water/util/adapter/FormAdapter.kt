package com.lhr.water.util.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lhr.water.R
import com.lhr.water.databinding.ItemFormBinding
import com.lhr.water.room.FormEntity

class FormAdapter(val listener: Listener, context: Context) :
    ListAdapter<FormEntity, FormAdapter.ViewHolder>(LOCK_DIFF_UTIL) {
    var context = context

    companion object {
        val LOCK_DIFF_UTIL = object : DiffUtil.ItemCallback<FormEntity>() {
            override fun areItemsTheSame(oldItem: FormEntity, newItem: FormEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: FormEntity,
                newItem: FormEntity
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFormBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemFormBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(formEntity: FormEntity) {
            binding.textReportTitle.text = formEntity.reportTitle
            binding.textFormNumber.text = formEntity.formNumber
            binding.textDate.text = formEntity.date?.let {
                if (it.length >= 10) {
                    it.substring(0, 10)
                } else {
                    "date error"
                }
            }
            when (formEntity.dealStatus) {
                context.getString(R.string.wait_deal) -> {
                    binding.viewStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.red))
                    binding.imageDealGoods.visibility = View.INVISIBLE
                }

                context.getString(R.string.now_deal) -> {
                    binding.viewStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow))
                    binding.imageDealGoods.visibility = View.VISIBLE
                }

                context.getString(R.string.complete_deal) -> {
                    binding.viewStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.green))
                    binding.imageDealGoods.visibility = View.INVISIBLE
                }
            }

            binding.imageDealGoods.setOnClickListener {
                listener.onDealMaterialClick(getItem(adapterPosition))
            }
            binding.root.setOnClickListener {
                if(adapterPosition != -1){
                    listener.onItemClick(getItem(adapterPosition))
                }
            }
        }
    }

    interface Listener {
        fun onItemClick(formEntity: FormEntity)
        fun onDealMaterialClick(formEntity: FormEntity)
    }
}
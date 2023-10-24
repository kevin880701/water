package com.lhr.water.util.recyclerViewAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lhr.water.databinding.ItemFormBinding
import com.lhr.water.model.FormData

class FormAdapter(val listener: Listener): ListAdapter<FormData, FormAdapter.ViewHolder>(LOCK_DIFF_UTIL) {

    companion object{
        val LOCK_DIFF_UTIL = object : DiffUtil.ItemCallback<FormData>() {
            override fun areItemsTheSame(oldItem: FormData, newItem: FormData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: FormData,
                newItem: FormData
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }

    interface Listener{
        fun onItemClick(item: FormData)
        fun onItemLongClick(item: FormData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFormBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemFormBinding): RecyclerView.ViewHolder(binding.root){

        init {
            // bindingAdapterPosition無法使用，所以用adapterPosition替代
            binding.root.setOnClickListener {
                listener.onItemClick(getItem(adapterPosition))
            }

            binding.root.setOnLongClickListener {
                listener.onItemLongClick(getItem(adapterPosition))
                return@setOnLongClickListener true
            }
        }

        fun bind(formData: FormData){
            binding.textFormName.text = formData.formName
            binding.imageForm.setImageResource(formData.formImage)
        }
    }
}
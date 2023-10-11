package com.lhr.water.util.recyclerViewAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lhr.water.R

class FormAdapter(formList: ArrayList<String>) :
    RecyclerView.Adapter<FormAdapter.ViewHolder>() {
    var formList = formList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_form, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textFormName.text = formList[position]
        holder.itemView.setOnClickListener {
        }
    }

    override fun getItemCount(): Int {
        return formList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val textFormName: TextView = v.findViewById(R.id.textFormName)
    }

}
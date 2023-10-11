package com.lhr.water.util.recyclerViewAdapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.lhr.water.R
import com.lhr.water.ui.map.MapActivity
import com.lhr.water.ui.mapChoose.MapChooseFragment

class MapChooseAdapter(arrayList: ArrayList<String>, fragment: Fragment) : RecyclerView.Adapter<MapChooseAdapter.ViewHolder>() {
    var arrayList = arrayList
    var fragment = fragment

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_choose_map, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textMapName.text = arrayList[position]
        holder.itemView.setOnClickListener {
            val intent = Intent(fragment.requireActivity(), MapActivity::class.java)
            intent.putExtra("region", arrayList[position])
            fragment.requireActivity().startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val textMapName: TextView = v.findViewById(R.id.textMapName)
    }
}
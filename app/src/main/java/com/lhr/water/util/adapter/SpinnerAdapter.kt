package com.lhr.water.util.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.lhr.water.R

class SpinnerAdapter(
    context: Context,
    resource: Int,
    objects: List<String>
) : ArrayAdapter<String>(context, resource, objects) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.setTextColor(ResourcesCompat.getColor(context.resources, R.color.seed, null))
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = inflater.inflate(R.layout.item_form_spinner, parent, false)
        val textItemName = view.findViewById<TextView>(R.id.textItemName)
        view.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        textItemName.text = getItem(position)
        textItemName.setTextColor(ResourcesCompat.getColor(context.resources, R.color.seed, null))

        return view
    }
}
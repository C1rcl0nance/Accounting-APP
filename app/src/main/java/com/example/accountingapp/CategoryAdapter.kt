package com.example.accountingapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.accountingapp.data.model.Category

class CategoryAdapter(private val context: Context, private val categoryList: List<Category>) : BaseAdapter() {

    override fun getCount(): Int {
        return categoryList.size
    }

    override fun getItem(position: Int): Any {
        return categoryList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.category_item, parent, false)

        val currentCategory = categoryList[position]

        val categoryIcon = view.findViewById<ImageView>(R.id.category_icon)
        val categoryName = view.findViewById<TextView>(R.id.category_name)

        categoryIcon.setImageResource(currentCategory.iconResource)
        categoryName.text = currentCategory.name

        return view
    }
}


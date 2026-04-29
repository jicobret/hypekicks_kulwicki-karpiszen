package com.example.hypekicks

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class SneakerAdapter(
    private val context: Context,
    private var list: List<Sneaker>
) : BaseAdapter() {

    fun updateList(newList: List<Sneaker>) {
        list = newList
        notifyDataSetChanged()
    }

    override fun getCount() = list.size
    override fun getItem(position: Int) = list[position]
    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_sneaker, parent, false)

        val sneaker = list[position]

        val image = view.findViewById<ImageView>(R.id.imageView)
        val name = view.findViewById<TextView>(R.id.textView)

        name.text = "${sneaker.brand} ${sneaker.modelName}"

        Glide.with(context)
            .load(sneaker.imageUrl)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_delete)
            .into(image)

        return view
    }
}
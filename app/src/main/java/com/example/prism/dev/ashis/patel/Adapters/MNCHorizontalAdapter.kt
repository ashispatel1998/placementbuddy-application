package com.example.prism.dev.ashis.patel.Adapters

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.example.prism.dev.ashis.patel.R
import com.squareup.picasso.Picasso

class MNCHorizontalAdapter(val list: ArrayList<String>) :
        RecyclerView.Adapter<MNCHorizontalAdapter.MyView>() {

    class MyView(view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView
        init {
            imageView = view
                    .findViewById<ImageView>(R.id.imageMNC)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        val itemView: View = LayoutInflater
                .from(parent.context)
                .inflate(
                        R.layout.horizontal_list_item,
                        parent,
                        false
                )
        return MyView(itemView)
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: MyView, position: Int) {
        val listData = list[position]

        //Loading Image into view
        Picasso.get().load(listData).placeholder(R.drawable.loa).into(holder.imageView)

    }

    override fun getItemCount(): Int {
        return list.size
    }

}
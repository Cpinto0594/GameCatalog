package com.cpinto.gamecatalog.extras.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView


object RecyclerViewBindingAdapters {

    @JvmStatic
    @BindingAdapter("app:adapter")
    fun adapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>) {
        recyclerView.adapter = adapter
    }

}
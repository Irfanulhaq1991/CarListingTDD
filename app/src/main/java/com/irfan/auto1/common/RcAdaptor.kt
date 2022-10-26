package com.irfan.auto1.common

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RcAdaptor<T>(private val itemLayoutManger: ItemLayoutManger<T>) :
    RecyclerView.Adapter<RcAdaptor.AppViewHolder<T>>() {
    private var itemList = ArrayList<T>()

    fun bindRecyclerView(
        recyclerview: RecyclerView,
        rcScrollListener: RecyclerView.OnScrollListener? = null
    ) {
        recyclerview.adapter = this
        itemLayoutManger.onRcAdapterReady()
        rcScrollListener?.let { recyclerview.addOnScrollListener(it) }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(itemList: List<T>) {
        if (this.itemList.containsAll(itemList)) return
        this.itemList.addAll(itemList)
        notifyDataSetChanged()
    }

    fun getItems(): List<T> {
        return this.itemList
    }

    fun getItems(position: Int): T {
        if (position > itemList.size || position < 0) throw IllegalArgumentException()
        return this.itemList[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder<T> {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(viewType, parent, false)
        return AppViewHolder(view, itemLayoutManger)
    }

    override fun onBindViewHolder(holder: AppViewHolder<T>, position: Int) {
        holder.bind(position, getItems(position))
    }

    override fun getItemViewType(position: Int): Int {
        return itemLayoutManger.getLayoutId(position)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class AppViewHolder<in T>(
        private val view: View,
        private val itemLayoutManger: ItemLayoutManger<T>
    ) :
        RecyclerView.ViewHolder(view) {
        fun bind(position: Int, item: T) {
            itemLayoutManger.bindView(view, position, item)
        }
    }
}

interface ItemLayoutManger<T> {
    fun onRcAdapterReady()
    fun getLayoutId(position: Int): Int
    fun bindView(view: View, position: Int, item: T)
}
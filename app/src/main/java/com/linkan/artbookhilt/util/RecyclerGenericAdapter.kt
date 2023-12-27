package com.linkan.artbookhilt.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.linkan.artbookhilt.adapter.BaseRecyclerAdapter

fun <T> RecyclerView.bindData(
    data: List<T>,
    layoutRes: Int,
    bindFunc: (View, T) -> Unit,
    clickListener: ((T) -> Unit)? = null) {

    adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val item = data[position]
            bindFunc(holder.itemView, item)
            clickListener?.let { listener ->
                holder.itemView.setOnClickListener { listener(item) }
            }
        }

        override fun getItemCount() = data.size
        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    }
}

fun <T, M : ViewBinding> bindAdapterWithBinding(
    layoutRes: Int,
    viewBinder : ((View) -> M),
    bindFunc: (M, T) -> Unit,
    clickListener: ((T) -> Unit)? = null) : BaseRecyclerAdapter<T, M> {

    return BaseRecyclerAdapter(
                layoutRes = layoutRes,
                binder = viewBinder,
                bindFunc = bindFunc,
                clickListener = clickListener)

}
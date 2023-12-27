package com.linkan.artbookhilt.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class BaseRecyclerAdapter<T, M : ViewBinding>(
    private val layoutRes: Int,
    private val binder : ((View) -> M),
    private val bindFunc: (M, T) -> Unit,
    private val clickListener: ((T) -> Unit)? = null
) : RecyclerView.Adapter<BaseRecyclerAdapter<T, M>.ViewHolder>() {

    var itemList: List<T>
        set(value) = recyclerListDiffer.submitList(value)
        get() = recyclerListDiffer.currentList

    private val diffCallback = object : DiffUtil.ItemCallback<T>() {

        override fun areItemsTheSame(oldItem: T & Any, newItem: T & Any): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T & Any, newItem: T & Any): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
        return ViewHolder(binder(view))
    }

    override fun onBindViewHolder(holder: BaseRecyclerAdapter<T, M>.ViewHolder, position: Int) {
        val item = itemList[position]
        bindFunc(holder.mBinding, item)
        clickListener?.let { listener ->
            holder.itemView.setOnClickListener { listener(item) }
        }
    }

    override fun getItemCount() = itemList.size
    inner class ViewHolder(val mBinding: M) : RecyclerView.ViewHolder(mBinding.root)
}
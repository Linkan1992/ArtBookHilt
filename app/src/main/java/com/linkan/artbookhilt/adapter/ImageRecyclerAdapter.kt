package com.linkan.artbookhilt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.linkan.artbookhilt.databinding.ImageRowBinding
import javax.inject.Inject

class ImageRecyclerAdapter @Inject constructor(
    private val glide : RequestManager) : RecyclerView.Adapter<ImageRecyclerAdapter.ImageViewHolder>() {

    private var onItemClickListener : ((String) -> Unit)? = null

    fun setOnItemClickListener(listener : (String) -> Unit){
        onItemClickListener = listener
    }

    private val diffCallback = object : DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerDiffList = AsyncListDiffer(this, diffCallback)

    var imageList : List<String>
        get() = recyclerDiffList.currentList
        set(value) = recyclerDiffList.submitList(value)

    class ImageViewHolder(val mBinding: ImageRowBinding) : RecyclerView.ViewHolder(mBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageRecyclerAdapter.ImageViewHolder {
        return ImageViewHolder(ImageRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ImageRecyclerAdapter.ImageViewHolder, position: Int) {
        val imageUrl = imageList[position]
        glide.load(imageUrl).into(holder.mBinding.singleArtImageView)
        holder.itemView.apply {
            setOnClickListener {
                onItemClickListener?.let { listener ->
                    listener(imageUrl)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}
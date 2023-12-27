package com.linkan.artbookhilt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.linkan.artbookhilt.databinding.ArtRowBinding
import com.linkan.artbookhilt.roomdb.Art
import javax.inject.Inject

class ArtRecyclerAdapter @Inject constructor(
    private val glide : RequestManager) : RecyclerView.Adapter<ArtRecyclerAdapter.ArtViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Art>(){
        override fun areItemsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffCallback)

    var artList : List<Art>
        set(value) = recyclerListDiffer.submitList(value)
        get() = recyclerListDiffer.currentList

    class ArtViewHolder(val mBinding : ArtRowBinding) : RecyclerView.ViewHolder(mBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtViewHolder {
        val binding = ArtRowBinding.inflate( LayoutInflater.from(parent.context), parent, false)
        return ArtViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtViewHolder, position: Int) {
        artList[position].let { item ->

            glide.load(item.imageUrl).into(holder.mBinding.imgArt)

            holder.mBinding.apply {
                tvArtName.text = "Art Name : ${item.artName}"
                tvArtistName.text = "Artist Name : ${item.artistName}"
                tvArtYear.text = "Year of Art : ${item.artYear}"
            }
        }
    }

    override fun getItemCount(): Int {
        return artList.size
    }
}
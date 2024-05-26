package com.linkan.artbookhilt.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.linkan.artbookhilt.R
import com.linkan.artbookhilt.adapter.ArtRecyclerAdapter
import com.linkan.artbookhilt.adapter.BaseRecyclerAdapter
import com.linkan.artbookhilt.databinding.ArtRowBinding
import com.linkan.artbookhilt.databinding.FragmentArtBinding
import com.linkan.artbookhilt.roomdb.Art
import com.linkan.artbookhilt.util.bindAdapterWithBinding
import com.linkan.artbookhilt.viewmodel.ArtViewModel
import javax.inject.Inject

class ArtFragment @Inject constructor(
    private val artRecyclerAdapter1: ArtRecyclerAdapter, private val glide : RequestManager) : Fragment(R.layout.fragment_art) {

    private var mBinding : FragmentArtBinding? = null
    private val mViewModel : ArtViewModel by activityViewModels()
    lateinit var artRecyclerAdapter : BaseRecyclerAdapter<Art, ArtRowBinding>

    private val swipeCallback  = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPosition = viewHolder.layoutPosition
            val selectedArt = artRecyclerAdapter.itemList[layoutPosition]
            mViewModel.deleteArt(selectedArt)
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mBinding = FragmentArtBinding.bind(view)
        Toast.makeText(context, "ArtFragment >> onViewCreated", Toast.LENGTH_SHORT).show()

        mBinding?.fbButton?.setOnClickListener {
            findNavController().navigate(ArtFragmentDirections.actionArtFragmentToArtFragmentDetails())
        }

        subscribeToObserver()
        initRecyclerView()
        bindSwipeCallToRecycler()
    }

    private fun initRecyclerView() {
        mBinding?.rvList?.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            artRecyclerAdapter = bindAdapterWithBinding(
                layoutRes = R.layout.art_row,
                viewBinder = { view -> return@bindAdapterWithBinding ArtRowBinding.bind(view) },
                bindFunc = { binding : ArtRowBinding, item : Art ->
                    glide.load(item.imageUrl).into(binding.imgArt)
                    binding.apply {
                        tvArtName.text = "Art Name : ${item.artName}"
                        tvArtistName.text = "Artist Name : ${item.artistName}"
                        tvArtYear.text = "Year of Art : ${item.artYear}"
                    }
                },
                clickListener = {

                })
            adapter = artRecyclerAdapter
        }
    }

    private fun bindSwipeCallToRecycler() {
        ItemTouchHelper(swipeCallback).attachToRecyclerView(mBinding?.rvList)
    }

    private fun subscribeToObserver() {
        mViewModel.artList.observe(viewLifecycleOwner){
            artRecyclerAdapter.itemList = it
        }
    }


    override fun onDestroyView() {
        mBinding = null
        Toast.makeText(context, "ArtFragment >> onDestroyView", Toast.LENGTH_SHORT).show()
        super.onDestroyView()
    }

}
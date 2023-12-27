package com.linkan.artbookhilt.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.linkan.artbookhilt.adapter.ArtRecyclerAdapter
import com.linkan.artbookhilt.adapter.ImageRecyclerAdapter
import javax.inject.Inject

class ArtFragmentFactory @Inject constructor(
    private val glide : RequestManager,
    private val artRecyclerAdapter: ArtRecyclerAdapter,
    private val imageRecyclerAdapter: ImageRecyclerAdapter) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            ArtFragment::class.java.name -> ArtFragment(artRecyclerAdapter, glide)
            ArtFragmentDetails::class.java.name -> ArtFragmentDetails(glide)
            ImageApiFragment::class.java.name -> ImageApiFragment(imageRecyclerAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }

}
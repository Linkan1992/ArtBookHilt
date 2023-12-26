package com.linkan.artbookhilt.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.RequestManager
import com.linkan.artbookhilt.R
import com.linkan.artbookhilt.adapter.ImageRecyclerAdapter
import com.linkan.artbookhilt.databinding.FragmentImageApiBinding
import com.linkan.artbookhilt.util.Status
import com.linkan.artbookhilt.viewmodel.ArtViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ImageApiFragment @Inject constructor(
    private val imageRecyclerAdapter: ImageRecyclerAdapter
) : Fragment(R.layout.fragment_image_api) {

    private var mBinding: FragmentImageApiBinding? = null

    private val mViewModel: ArtViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Toast.makeText(context, "ImageApiFragment >> onViewCreated", Toast.LENGTH_SHORT).show()
        mBinding = FragmentImageApiBinding.bind(view)

        initRecyclerView()
        subscribeToObserver()
        initSearch()
    }

    private var job: Job? = null
    private fun initSearch() {
        mBinding?.searchText?.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                delay(1000)
                Log.d(
                    "ImageApiFragment",
                    "I'm Searching in Thread = ${Thread.currentThread().name}"
                )
                it?.toString()
                    ?.takeIf { it.isNotEmpty() }
                    ?.let {
                        mViewModel.searchForImage(it)
                    }
            }
        }
    }


    private fun initRecyclerView() {
        mBinding?.apply {
            imageRecyclerView.layoutManager = GridLayoutManager(context, 3)
            imageRecyclerView.adapter = imageRecyclerAdapter

            imageRecyclerAdapter.setOnItemClickListener {
                findNavController().popBackStack()
                mViewModel.setSelectedImage(it)
            }
        }
    }

    private fun subscribeToObserver() {
        mViewModel.imageList.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    val urls = it.data?.hits?.map { imageResult ->
                        imageResult.previewURL
                    }

                    imageRecyclerAdapter.imageList = urls ?: listOf()
                    mBinding?.progressBar?.visibility = View.GONE
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message ?: "Error", Toast.LENGTH_SHORT)
                        .show()
                    mBinding?.progressBar?.visibility = View.GONE
                }

                Status.LOADING -> {
                    mBinding?.progressBar?.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroyView() {
        mBinding = null
        Toast.makeText(context, "ImageApiFragment >> onDestroyView", Toast.LENGTH_SHORT).show()
        super.onDestroyView()
    }


}
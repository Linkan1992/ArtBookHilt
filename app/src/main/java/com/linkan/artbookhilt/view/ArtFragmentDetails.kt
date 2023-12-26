package com.linkan.artbookhilt.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.linkan.artbookhilt.R
import com.linkan.artbookhilt.adapter.ArtRecyclerAdapter
import com.linkan.artbookhilt.databinding.FragmentArtDetailsBinding
import com.linkan.artbookhilt.util.Resource
import com.linkan.artbookhilt.util.Status
import com.linkan.artbookhilt.viewmodel.ArtViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArtFragmentDetails @Inject constructor(private val glide : RequestManager) : Fragment(R.layout.fragment_art_details) {

    private var mBinding : FragmentArtDetailsBinding? = null

    private val mViewModel : ArtViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mBinding = FragmentArtDetailsBinding.bind(view)
        Toast.makeText(context, "ArtFragmentDetails >> onViewCreated", Toast.LENGTH_SHORT).show()

        initBackCallBack()
        initClickListener()
        subscribeToObserver()
    }

    private fun subscribeToObserver() {
        mViewModel.selectedImageUrl.observe(viewLifecycleOwner){imageUrl ->
            mBinding?.apply {
                glide.load(imageUrl).into(imgArt)
            }
        }

        mViewModel.insertArtMessage.observe(viewLifecycleOwner){ result ->
          when(result.status){
               Status.SUCCESS -> {
                   Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                   findNavController().popBackStack()
                   mViewModel.resetInsertArtMsg()
               }
              Status.ERROR -> {
                  Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
              }
              Status.LOADING -> {}
          }
        }
    }

    private fun initClickListener() {
        mBinding?.imgArt?.setOnClickListener {
            findNavController().navigate(ArtFragmentDetailsDirections.actionArtFragmentDetailsToImageApiFragment())
        }

        mBinding?.btnSave?.setOnClickListener {
            mBinding?.apply {
                mViewModel.makeArt(name = etArtName.text.toString(),
                    artistName = etArtistName.text.toString(),
                    year = etArtYear.text.toString())
            }
        }
    }

    private fun initBackCallBack() {

        val backCallBack = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(backCallBack)
    }


    override fun onDestroyView() {
        mBinding = null
        Toast.makeText(context, "ArtFragmentDetails >> onDestroyView", Toast.LENGTH_SHORT).show()
        super.onDestroyView()
    }

}
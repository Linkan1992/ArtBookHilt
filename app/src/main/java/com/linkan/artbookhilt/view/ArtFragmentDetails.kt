package com.linkan.artbookhilt.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.linkan.artbookhilt.R
import com.linkan.artbookhilt.databinding.FragmentArtDetailsBinding

class ArtFragmentDetails : Fragment(R.layout.fragment_art_details) {

    var mBinding : FragmentArtDetailsBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mBinding = FragmentArtDetailsBinding.bind(view)
        Toast.makeText(context, "ArtFragmentDetails >> onViewCreated", Toast.LENGTH_SHORT).show()
        mBinding?.imgArt?.setOnClickListener {
            findNavController().navigate(ArtFragmentDetailsDirections.actionArtFragmentDetailsToImageApiFragment())
        }
    }


    override fun onDestroyView() {
        mBinding = null
        Toast.makeText(context, "ArtFragmentDetails >> onDestroyView", Toast.LENGTH_SHORT).show()
        super.onDestroyView()
    }

}
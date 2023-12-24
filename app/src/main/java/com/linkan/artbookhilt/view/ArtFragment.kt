package com.linkan.artbookhilt.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.linkan.artbookhilt.R
import com.linkan.artbookhilt.databinding.FragmentArtBinding

class ArtFragment : Fragment(R.layout.fragment_art) {

    var mBinding : FragmentArtBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mBinding = FragmentArtBinding.bind(view)
        Toast.makeText(context, "ArtFragment >> onViewCreated", Toast.LENGTH_SHORT).show()

        mBinding?.fbButton?.setOnClickListener {
            findNavController().navigate(ArtFragmentDirections.actionArtFragmentToArtFragmentDetails())
        }

    }


    override fun onDestroyView() {
        mBinding = null
        Toast.makeText(context, "ArtFragment >> onDestroyView", Toast.LENGTH_SHORT).show()
        super.onDestroyView()
    }

}
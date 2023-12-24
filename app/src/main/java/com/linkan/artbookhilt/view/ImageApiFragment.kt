package com.linkan.artbookhilt.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.linkan.artbookhilt.R
import com.linkan.artbookhilt.databinding.FragmentImageApiBinding
import kotlinx.coroutines.launch

class ImageApiFragment : Fragment(R.layout.fragment_image_api) {

    private var mBinding : FragmentImageApiBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Toast.makeText(context, "ImageApiFragment >> onViewCreated", Toast.LENGTH_SHORT).show()
        mBinding = FragmentImageApiBinding.bind(view)
    }

    override fun onDestroyView() {
        mBinding = null
        Toast.makeText(context, "ImageApiFragment >> onDestroyView", Toast.LENGTH_SHORT).show()
        super.onDestroyView()
    }


}
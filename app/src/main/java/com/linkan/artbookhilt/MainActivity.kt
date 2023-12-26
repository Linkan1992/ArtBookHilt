package com.linkan.artbookhilt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.linkan.artbookhilt.view.ArtFragmentFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var artFragmentFactory: ArtFragmentFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = artFragmentFactory
        setContentView(R.layout.activity_main)
    }
}
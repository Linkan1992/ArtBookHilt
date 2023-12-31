package com.linkan.artbookhilt.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linkan.artbookhilt.api.model.ImageResponse
import com.linkan.artbookhilt.repo.ArtRepositoryInterface
import com.linkan.artbookhilt.roomdb.Art
import com.linkan.artbookhilt.util.Resource
import com.linkan.artbookhilt.util.runOnBackgroundDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtViewModel @Inject constructor(
    private val artRepository: ArtRepositoryInterface
) : ViewModel() {


    // ArtFragment
    val artList = artRepository.getArt()

    // Image API fragments
    private val images = MutableLiveData<Resource<ImageResponse>>()
    val imageList: LiveData<Resource<ImageResponse>>
        get() = images

    private val selectedImage = MutableLiveData<String>()
    val selectedImageUrl: LiveData<String>
        get() = selectedImage


    // Art Detail Fragment
    private var insertArtMsg = MutableLiveData<Resource<Art>>()
    val insertArtMessage: LiveData<Resource<Art>>
        get() = insertArtMsg

    fun resetInsertArtMsg(){
        insertArtMsg = MutableLiveData<Resource<Art>>()
    }

    fun setSelectedImage(imageUrl : String){
        selectedImage.postValue(imageUrl)
    }

    fun deleteArt(art: Art) = viewModelScope.launch {
        artRepository.deleteArt(art)
    }

    fun insertArt(art: Art) = viewModelScope.launch {
        artRepository.insertArt(art)
    }

    fun makeArt(name : String, artistName : String, year : String) {
        if (name.isEmpty() || artistName.isEmpty() || year.isEmpty() ) {
            insertArtMsg.postValue(Resource.error("Enter name, artist, year", null))
            return
        }
        val yearInt = try {
            year.toInt()
        } catch (e: Exception) {
            insertArtMsg.postValue(Resource.error("Year should be number",null))
            return
        }

        val art = Art(name, artistName, yearInt,selectedImage.value?: "")
        insertArt(art)
        setSelectedImage("")
        insertArtMsg.postValue(Resource.success(art))
    }

    fun searchForImage (searchString : String) {

        if(searchString.isEmpty()) {
            return
        }

        /*images.value = Resource.loading(null)
        viewModelScope.launch {
            Log.d("ArtViewModel", "I'm working in Thread = ${Thread.currentThread().name}")
            val response = artRepository.searchImage(searchString)
            images.value = response
        }*/

        viewModelScope.runOnBackgroundDispatcher(startLoader = {
            images.value = Resource.loading(null)
        }, backgroundFunc = {
            artRepository.searchImage(searchString)
        }, callback = { response ->
            images.value = response
        })
    }

}
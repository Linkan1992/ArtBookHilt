package com.linkan.artbookhilt.repo

import androidx.lifecycle.LiveData
import com.linkan.artbookhilt.api.model.ImageResponse
import com.linkan.artbookhilt.roomdb.Art
import com.linkan.artbookhilt.util.Resource

interface ArtRepositoryInterface {
    suspend fun insertArt(art: Art)
    suspend fun deleteArt(art: Art)
    fun getArt() : LiveData<List<Art>>
    suspend fun searchImage(searchImage : String) : Resource<ImageResponse>
}
package com.linkan.artbookhilt.repo

import androidx.lifecycle.LiveData
import com.linkan.artbookhilt.api.model.ImageResponse
import com.linkan.artbookhilt.api.model.apiservice.RetrofitAPI
import com.linkan.artbookhilt.roomdb.Art
import com.linkan.artbookhilt.roomdb.ArtDao
import com.linkan.artbookhilt.util.Resource
import com.linkan.artbookhilt.util.safeApiCall
import javax.inject.Inject

class ArtRepository @Inject constructor(
    private val artDao: ArtDao,
    private val retrofitAPI: RetrofitAPI) : ArtRepositoryInterface {
    override suspend fun insertArt(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDao.observeArts()
    }

    override suspend fun searchImage(searchImage: String): Resource<ImageResponse> {
        return safeApiCall {
            retrofitAPI.imageSearch(searchQuery = searchImage)
        }
    }
}
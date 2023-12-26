package com.linkan.artbookhilt.api.model.apiservice

import com.linkan.artbookhilt.util.Util
import com.linkan.artbookhilt.api.model.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {
    @GET("/api")
    suspend fun imageSearch(
        @Query("key") apiKey : String = Util.API_KEY, @Query("q") searchQuery : String
    ) : Response<ImageResponse>

}
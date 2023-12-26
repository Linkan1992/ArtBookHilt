package com.linkan.artbookhilt.dependency

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.linkan.artbookhilt.R
import com.linkan.artbookhilt.util.Util
import com.linkan.artbookhilt.api.model.apiservice.RetrofitAPI
import com.linkan.artbookhilt.repo.ArtRepository
import com.linkan.artbookhilt.repo.ArtRepositoryInterface
import com.linkan.artbookhilt.roomdb.ArtDao
import com.linkan.artbookhilt.roomdb.ArtDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun injectArtDatabase(@ApplicationContext appContext : Context) : ArtDatabase = Room.databaseBuilder(appContext,
        ArtDatabase::class.java, "ArtBookDB").build()
    @Provides
    @Singleton
    fun injectArtDao(artDatabase: ArtDatabase) : ArtDao = artDatabase.getArtDao()

    @Provides
    @Singleton
    fun injectGson() : Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun injectGsonConverterFactory() : GsonConverterFactory = GsonConverterFactory.create()
    @Provides
    @Singleton
    fun injectRetrofitApi(gsonConverterFactory: GsonConverterFactory) : RetrofitAPI {
        return Retrofit.Builder()
            .baseUrl(Util.BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(RetrofitAPI::class.java)
    }
    
    @Provides
    @Singleton
    fun injectArtRepositoryInterface(artDao: ArtDao, retrofitAPI: RetrofitAPI) : ArtRepositoryInterface = ArtRepository(artDao, retrofitAPI)

    @Provides
    @Singleton
    fun injectGlide(@ApplicationContext context: Context) : RequestManager = Glide.with(context).
            setDefaultRequestOptions(RequestOptions.placeholderOf(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background))

}
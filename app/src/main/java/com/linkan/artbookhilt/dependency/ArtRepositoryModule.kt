package com.linkan.artbookhilt.dependency

import com.linkan.artbookhilt.repo.ArtRepository
import com.linkan.artbookhilt.repo.ArtRepositoryInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ArtRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindArtRepositoryInterface(artRepository: ArtRepository) : ArtRepositoryInterface

}
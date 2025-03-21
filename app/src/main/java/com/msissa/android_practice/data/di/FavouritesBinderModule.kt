package com.msissa.android_practice.data.di

import com.msissa.android_practice.data.favourites.FavouritesDataSource
import com.msissa.android_practice.data.favourites.FavouritesDataSourceImpl
import com.msissa.android_practice.data.favourites.FavouritesRepository
import com.msissa.android_practice.data.favourites.FavouritesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FavouritesBinderModule {

    @Binds
    abstract fun bindFavouritesDataSource(favouritesDataSourceImpl: FavouritesDataSourceImpl): FavouritesDataSource

    @Binds
    abstract fun bindFavouritesRepository(favouritesRepositoryImpl: FavouritesRepositoryImpl): FavouritesRepository
}
package com.msissa.android_practice.data.di

import android.content.Context
import androidx.room.Room
import com.msissa.android_practice.data.favourites.FavouritesContract.DB_NAME
import com.msissa.android_practice.data.favourites.FavouritesDao
import com.msissa.android_practice.data.favourites.FavouritesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FavouritesProviderModule {

    @Provides
    @Singleton
    fun provideFavouritesDatabase(@ApplicationContext context: Context): FavouritesDatabase {
        return Room.databaseBuilder(
            context,
            FavouritesDatabase::class.java,
            DB_NAME
        ).build()
    }

    /* Use the automatic implementation offered by Room to provide an implementation of the FavouritesDao interface */
    @Provides
    fun provideFavouritesDao(favouritesDatabase: FavouritesDatabase): FavouritesDao = favouritesDatabase.favouritesDao()
}
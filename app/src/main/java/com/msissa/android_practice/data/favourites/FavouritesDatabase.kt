package com.msissa.android_practice.data.favourites

import androidx.room.Database
import androidx.room.RoomDatabase
import com.msissa.android_practice.data.favourites.model.DatabaseQuotationDto

@Database(entities = [DatabaseQuotationDto::class], version = 1)
abstract class FavouritesDatabase : RoomDatabase() {

    /**
     * Abstract function automatically implemented by the Room library
     * It returns an implementation of the FavouritesDao interface (providing access to the database data)
     */
    abstract fun favouritesDao(): FavouritesDao

}
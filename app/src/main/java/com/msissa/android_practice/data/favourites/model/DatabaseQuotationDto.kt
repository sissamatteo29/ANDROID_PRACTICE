package com.msissa.android_practice.data.favourites.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.msissa.android_practice.data.favourites.FavouritesContract.FavouritesTable.COLUMN_AUTHOR
import com.msissa.android_practice.data.favourites.FavouritesContract.FavouritesTable.COLUMN_ID
import com.msissa.android_practice.data.favourites.FavouritesContract.FavouritesTable.TABLE_NAME
import com.msissa.android_practice.data.favourites.FavouritesContract.FavouritesTable.COLUMN_TEXT

/**
 * Representation of the data as structured in the database
 */
@Entity(tableName = TABLE_NAME)
data class DatabaseQuotationDto(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    val id: String,

    @ColumnInfo(name = COLUMN_TEXT)
    val text: String,

    @ColumnInfo(name = COLUMN_AUTHOR)
    val author: String
)

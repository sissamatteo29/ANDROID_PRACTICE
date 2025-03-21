package com.msissa.android_practice.data.favourites

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.msissa.android_practice.data.favourites.FavouritesContract.FavouritesTable.COLUMN_ID
import com.msissa.android_practice.data.favourites.FavouritesContract.FavouritesTable.TABLE_NAME
import com.msissa.android_practice.data.favourites.model.DatabaseQuotationDto
import kotlinx.coroutines.flow.Flow

/**
 * Data access object converting methods into SQL queries
 */
@Dao
interface FavouritesDao {

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAllFavourites(): Flow<List<DatabaseQuotationDto>>

    @Query("SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = :id")
    fun getQuotationById(id: String): Flow<DatabaseQuotationDto?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuotation(quotation: DatabaseQuotationDto)

    @Delete
    suspend fun removeQuotation(quotation: DatabaseQuotationDto)

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun removeAllQuotations()
}
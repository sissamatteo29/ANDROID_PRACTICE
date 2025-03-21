package com.msissa.android_practice.data.favourites

import com.msissa.android_practice.data.favourites.model.DatabaseQuotationDto
import kotlinx.coroutines.flow.Flow


/**
 * Abstraction layer separating the application from the concrete implementation of the data source.
 */
interface FavouritesDataSource {

    fun getAllFavourites(): Flow<List<DatabaseQuotationDto>>

    fun getQuotationById(id: String): Flow<DatabaseQuotationDto?>

    suspend fun insertQuotation(quotation: DatabaseQuotationDto)

    suspend fun removeQuotation(quotation: DatabaseQuotationDto)

    suspend fun removeAllQuotations()
}
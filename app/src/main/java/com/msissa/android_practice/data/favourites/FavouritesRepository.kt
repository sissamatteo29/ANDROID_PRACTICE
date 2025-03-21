package com.msissa.android_practice.data.favourites

import com.msissa.android_practice.domain.model.Quotation
import kotlinx.coroutines.flow.Flow

interface FavouritesRepository {

    fun getAllFavourites(): Flow<List<Quotation>>

    fun getQuotationById(id: String): Flow<Quotation?>

    suspend fun insertQuotation(quotation: Quotation)

    suspend fun removeQuotation(quotation: Quotation)

    suspend fun removeAllQuotations()

}
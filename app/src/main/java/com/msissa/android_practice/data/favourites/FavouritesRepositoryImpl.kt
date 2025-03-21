package com.msissa.android_practice.data.favourites

import com.msissa.android_practice.data.favourites.model.toDatabaseDto
import com.msissa.android_practice.data.favourites.model.toDomain
import com.msissa.android_practice.domain.model.Quotation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavouritesRepositoryImpl
    @Inject constructor(private val favouritesDataSource: FavouritesDataSource)
    : FavouritesRepository {

    override fun getAllFavourites(): Flow<List<Quotation>> {
        return favouritesDataSource.getAllFavourites().map { list ->
            list.map { sourceQuotation ->
                sourceQuotation.toDomain()      // Map all quotations to the domain model and return the list
            }
        }
    }

    override fun getQuotationById(id: String): Flow<Quotation?> {
        return favouritesDataSource.getQuotationById(id).map { sourceQuotation ->
            sourceQuotation?.toDomain()
        }
    }

    override suspend fun insertQuotation(quotation: Quotation) {
        favouritesDataSource.insertQuotation(quotation.toDatabaseDto())
    }

    override suspend fun removeQuotation(quotation: Quotation) {
        favouritesDataSource.removeQuotation(quotation.toDatabaseDto())
    }

    override suspend fun removeAllQuotations() {
        favouritesDataSource.removeAllQuotations()
    }

}
package com.msissa.android_practice.data.favourites

import com.msissa.android_practice.data.favourites.model.DatabaseQuotationDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavouritesDataSourceImpl
    @Inject constructor(private val favouritesDao: FavouritesDao)
    : FavouritesDataSource {

    override fun getAllFavourites(): Flow<List<DatabaseQuotationDto>> {
       return favouritesDao.getAllFavourites()
    }

    override fun getQuotationById(id: String): Flow<DatabaseQuotationDto?> {
        return favouritesDao.getQuotationById(id)
    }

    override suspend fun insertQuotation(quotation: DatabaseQuotationDto) {
        favouritesDao.insertQuotation(quotation)
    }

    override suspend fun removeQuotation(quotation: DatabaseQuotationDto) {
        favouritesDao.removeQuotation(quotation)
    }

    override suspend fun removeAllQuotations() {
        favouritesDao.removeAllQuotations()
    }
}
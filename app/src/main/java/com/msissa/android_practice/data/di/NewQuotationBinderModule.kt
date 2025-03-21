package com.msissa.android_practice.data.di

import com.msissa.android_practice.data.newquotation.NewQuotationDataSource
import com.msissa.android_practice.data.newquotation.NewQuotationDataSourceImpl
import com.msissa.android_practice.data.newquotation.NewQuotationRepository
import com.msissa.android_practice.data.newquotation.NewQuotationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NewQuotationBinderModule {

    @Binds
    abstract fun bindNewQuotationRepository(newQuotationRepositoryImpl : NewQuotationRepositoryImpl) : NewQuotationRepository

    @Binds
    abstract fun bindNewQuotationDataSource(newQuotationDataSourceImpl : NewQuotationDataSourceImpl) : NewQuotationDataSource

}
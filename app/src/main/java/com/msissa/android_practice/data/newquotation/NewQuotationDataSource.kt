package com.msissa.android_practice.data.newquotation

import com.msissa.android_practice.data.newquotation.model.RemoteQuotationDto
import retrofit2.Response

interface NewQuotationDataSource {

    suspend fun getQuotation(language: String): Response<RemoteQuotationDto>

}
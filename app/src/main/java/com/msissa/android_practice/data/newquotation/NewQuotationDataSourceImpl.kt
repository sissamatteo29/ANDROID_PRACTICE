package com.msissa.android_practice.data.newquotation

import com.msissa.android_practice.data.newquotation.model.RemoteQuotationDto
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject


class NewQuotationDataSourceImpl @Inject constructor(retrofit: Retrofit) : NewQuotationDataSource {

    /* Interface to define the endpoints of the API to use with Retrofit */
    interface NewQuotationRetrofit {
        @GET("api/1.0/?method=getQuote&format=json")
        suspend fun getQuotation(@Query("lang") language: String): Response<RemoteQuotationDto> // lang dynamically added to the URL
    }

    private val retrofitQuotationService = retrofit.create(NewQuotationRetrofit::class.java)


    override suspend fun getQuotation(language: String): Response<RemoteQuotationDto> {
        return try {
            retrofitQuotationService.getQuotation(language)
        } catch(e : Exception) {
            Response.error(
                400,
                ResponseBody.create(MediaType.parse("text/plain"), e.toString())
            )
        }
    }
}
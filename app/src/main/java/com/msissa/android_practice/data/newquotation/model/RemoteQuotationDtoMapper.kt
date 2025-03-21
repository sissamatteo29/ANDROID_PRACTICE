package com.msissa.android_practice.data.newquotation.model

import com.msissa.android_practice.domain.model.Quotation
import retrofit2.Response
import java.io.IOException

/* Map a remote quotation to a domain quotation */
fun RemoteQuotationDto.toDomain() = Quotation(id = quoteLink, text = quoteText, author = quoteAuthor)

fun Response<RemoteQuotationDto>.toDomain() =
    if(isSuccessful) Result.success((body() as RemoteQuotationDto).toDomain())  // If successful response return the result mapped to the domain model
                                            else Result.failure(IOException())
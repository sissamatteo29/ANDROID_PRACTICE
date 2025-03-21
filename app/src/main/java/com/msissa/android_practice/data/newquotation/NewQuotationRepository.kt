package com.msissa.android_practice.data.newquotation

import com.msissa.android_practice.domain.model.Quotation

interface NewQuotationRepository {

    suspend fun getNewQuotation(): Result<Quotation>

}
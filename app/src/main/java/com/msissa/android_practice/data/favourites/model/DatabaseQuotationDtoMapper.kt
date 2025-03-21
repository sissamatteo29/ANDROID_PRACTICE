package com.msissa.android_practice.data.favourites.model

import com.msissa.android_practice.domain.model.Quotation

/**
 * Mapper function: this function extends the functionalities of the DatabaseQuotationDto class
 * Returns an object of type Quotation with the data extracted from the DB
 */
fun DatabaseQuotationDto.toDomain(): Quotation {
    return Quotation(
        id = id,
        text = text,
        author = author
    )
}

/**
 * Mapper function: this function maps a Quotation object to a DatabaseQuotationDto object
 */
fun Quotation.toDatabaseDto(): DatabaseQuotationDto {
    return DatabaseQuotationDto(
        id = id,
        text = text,
        author = author
    )
}
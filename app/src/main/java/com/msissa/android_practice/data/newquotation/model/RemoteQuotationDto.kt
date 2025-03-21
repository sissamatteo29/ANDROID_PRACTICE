package com.msissa.android_practice.data.newquotation.model

import com.squareup.moshi.JsonClass

/**
 * Representation of the data coming from the external API
 */
@JsonClass(generateAdapter = true)
class RemoteQuotationDto(
    var quoteText : String,
    var quoteAuthor : String,
    var senderName : String,
    var senderLink : String,
    var quoteLink : String)
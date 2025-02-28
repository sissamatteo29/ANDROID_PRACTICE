package com.msissa.android_practice.ui.favourites

import androidx.lifecycle.ViewModel
import com.msissa.android_practice.domain.model.Quotation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FavouritesViewModel : ViewModel() {

    private var _favourites : MutableStateFlow<List<Quotation>> = MutableStateFlow(getFavouriteQuotations())
    val favourites : StateFlow<List<Quotation>> = _favourites.asStateFlow()




    private fun getFavouriteQuotations() : List<Quotation> {
        var favourites = listOf<Quotation>()
        var num : Int

        // Generate 20 random favourites
        for(i in 1..20) {
            num = (0..99).random()
            favourites += Quotation(id = "$num", text = "Quotation text #$num", author = "Author #$num")
        }

        return favourites
    }

}
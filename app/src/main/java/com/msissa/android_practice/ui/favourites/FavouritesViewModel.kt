package com.msissa.android_practice.ui.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msissa.android_practice.data.favourites.FavouritesRepository
import com.msissa.android_practice.domain.model.Quotation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val favouritesRepository: FavouritesRepository
): ViewModel() {

    val favourites : StateFlow<List<Quotation>> = favouritesRepository.getAllFavourites().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList())

    val isDeleteAllMenuVisible = favourites.map { list -> list.isNotEmpty() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = true)



    fun deleteAllQuotations() {
        viewModelScope.launch {
            favouritesRepository.removeAllQuotations()
        }
    }

    fun deleteQuotationAtPosition(position : Int) {
        viewModelScope.launch {
            favouritesRepository.removeQuotation(favourites.value[position])
        }
    }

}
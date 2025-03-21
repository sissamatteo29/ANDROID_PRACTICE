package com.msissa.android_practice.ui.newquotation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msissa.android_practice.data.favourites.FavouritesRepository
import com.msissa.android_practice.data.newquotation.NewQuotationRepository
import com.msissa.android_practice.data.settings.SettingsRepository
import com.msissa.android_practice.domain.model.Quotation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewQuotationViewModel @Inject constructor(
    private val newQuotationRepository : NewQuotationRepository,
    settingsRepository: SettingsRepository,
    private val favouritesRepository: FavouritesRepository,
) : ViewModel() {

    val userName : StateFlow<String> = settingsRepository.getUserName().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ""
    )

    private val _quote : MutableStateFlow<Quotation?> = MutableStateFlow(null)
    val quote : StateFlow<Quotation?> = _quote.asStateFlow()

    private val _showLoading : MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showLoading : StateFlow<Boolean> = _showLoading.asStateFlow()

    private val _showWelcome : MutableStateFlow<Boolean> = MutableStateFlow(true)
    val showWelcome : StateFlow<Boolean> = _showWelcome.asStateFlow()

    // Manage visibility of the button to add a quote to the favourite list
    val showAddFavButton : StateFlow<Boolean> = quote.flatMapLatest { currentQuotation ->
        if(currentQuotation == null) flowOf(false)  // if the current quote is null, do not show the button
        else favouritesRepository.getQuotationById(currentQuotation.id).map { quotation ->
            quotation == null       // if the favouritesRepository returns null, show the AddFavButton because you can add the quote
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = false
    )

    // Optimisation to avoid triggering too many coroutines on the UI
    private var isFirstRefresh = true

    private var _showErrorMessage = MutableStateFlow<Throwable?>(null)
    val showErrorMessage : StateFlow<Throwable?> = _showErrorMessage.asStateFlow()


    /**
     * Get a new quotation from the external service, this function is called
     * every time the user triggers a new quotation request from the UI
     */
    fun getNewQuotation() {
        // Show icon while loading
        _showLoading.update { true }

        // Load data from external service
        viewModelScope.launch {
            newQuotationRepository.getNewQuotation().fold(
                onSuccess = { newQuote ->
                    // Log.d("QuoteDebug", "Received quote: ${newQuote.text} by ${newQuote.author}")  -> Verification: the API returns multiple times the same quote
                    _quote.update { newQuote }
                },
                onFailure = { error ->
                    _showErrorMessage.update { error }
                }
            )

            // Hide icon after loading
            _showLoading.update { false }
        }
    }

    // Expose a function to the UI to manage the visibility of the welcome message
    fun notifyFirstRefresh() {
        if(isFirstRefresh) {
            isFirstRefresh = false
            _showWelcome.update { false }
        }
    }

    fun addToFavourites() {
        viewModelScope.launch {
            favouritesRepository.insertQuotation(quote.value!!)
        }
    }

    fun resetError() {
        _showErrorMessage.update { null }
    }
}
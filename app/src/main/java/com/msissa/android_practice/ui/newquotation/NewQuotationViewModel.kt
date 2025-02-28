package com.msissa.android_practice.ui.newquotation

import androidx.lifecycle.ViewModel
import com.msissa.android_practice.domain.model.Quotation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NewQuotationViewModel : ViewModel() {
    
    private val _userName : MutableStateFlow<String> = MutableStateFlow(getUserName())
    val userName : StateFlow<String> = _userName.asStateFlow()

    private val _quote : MutableStateFlow<Quotation?> = MutableStateFlow(null)
    val quote : StateFlow<Quotation?> = _quote.asStateFlow()

    private val _showLoading : MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showLoading : StateFlow<Boolean> = _showLoading.asStateFlow()

    private val _showWelcome : MutableStateFlow<Boolean> = MutableStateFlow(true)
    val showWelcome : StateFlow<Boolean> = _showWelcome.asStateFlow()

    // Manage visibility of the button to add a quote to the favourite list
    private val _showAddFavButton : MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showAddFavButton : StateFlow<Boolean> = _showAddFavButton.asStateFlow()

    // Optimisation to avoid triggering too many coroutines on the UI
    private var isFirstRefresh = true

    private fun getUserName(): String {
        return setOf("Alice", "Bob", "Charlie", "David", "Emma", "").random()
    }


    /**
     * Get a new quotation from the external service, this function is called
     * every time the user triggers a new quotation request from the UI
     */
    fun getNewQuotation() {
        // Show icon while loading
        _showLoading.update { true }

        // Load data from external service
        val num = (0..99).random()
        _quote.update {
            Quotation( id = "$num", text = "Quotation text #$num", author = "Author #$num")
        }

        // Hide icon after loading
        _showLoading.update { false }

        _showAddFavButton.update { true }
    }

    // Expose a function to the UI to manage the visibility of the welcome message
    fun notifyFirstRefresh() {
        if(isFirstRefresh) {
            isFirstRefresh = false
            _showWelcome.update { false }
        }
    }



    fun addToFavourites() {
        _showAddFavButton.update { false }
    }
}
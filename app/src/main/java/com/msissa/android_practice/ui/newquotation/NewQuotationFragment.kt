package com.msissa.android_practice.ui.newquotation


import android.net.http.NetworkException
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import com.msissa.android_practice.R
import com.msissa.android_practice.data.utils.NoInternetException
import com.msissa.android_practice.databinding.FragmentNewQuotationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@AndroidEntryPoint
class NewQuotationFragment : Fragment(R.layout.fragment_new_quotation), MenuProvider {

    // Make _binding nullable in order to set it to null when the view is destroyed (avoid memory leaks)
    // Create the binding property to access the binding (readable way)
    private var _binding : FragmentNewQuotationBinding? = null
    private val binding
        get() = _binding!!

    // Get an instance of the associated view model through the factory
    private val newQuotationViewModel : NewQuotationViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentNewQuotationBinding.bind(view)

        // Add the menu to the fragment only when the fragment is active
        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        connectWithViewModel(view)      // Observe relevant properties from the view model
        manageErrorMessage(view)        // Manage the dialog popping up when errors occur
        setupListeners()                // Setup the listeners of the fragment

    }


    /* Container function with all the listeners and observers from the view model*/
    private fun connectWithViewModel(view: View) {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                // Observe changes to the user name
                launch {
                    newQuotationViewModel.userName.collect { userName ->
                        binding.tvWelcomeMessage.text = getString(
                            R.string.welcome_message,
                            userName.ifEmpty { getString(R.string.anonymous_user) })
                    }
                }

                // Manage the loading icon and welcome message
                launch {
                    newQuotationViewModel.showLoading.collect { showLoading ->
                        binding.srlRefreshLayoutNewQuotation.isRefreshing = showLoading
                    }
                }

                launch {
                    newQuotationViewModel.showWelcome.collect { showWelcome ->
                        binding.tvWelcomeMessage.isVisible = showWelcome
                    }
                }

                // Update the quote witgh the text from the viewModel (content + author)
                launch {
                    newQuotationViewModel.quote.collect { quote ->
                        if(quote != null) {
                            binding.tvQuoteText.text = quote.text
                            binding.tvAuthorName.text = if (quote.author == "") "Anonymous" else quote.author
                        }
                    }
                }

                // Manage the visibility of the add to favourites button
                launch {
                    newQuotationViewModel.showAddFavButton.collect { showAddFavButton ->
                        binding.fabAddToFavourites.isVisible = showAddFavButton
                    }
                }

            }
        }   // viewLifecycleOwner.lifecycleScope.launch

    }


    /* Container function to manage the dialog for error messages */
    private fun manageErrorMessage(view : View) {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Observe changes in the error state and display the error to the user
                launch {
                    newQuotationViewModel.showErrorMessage.collect { error ->
                        if (error == null) return@collect   // Ignore null
                        val messageToUser: String
                        when (error) {
                            is NoInternetException -> messageToUser =
                                getString(R.string.error_network) // No internet
                            is SocketTimeoutException -> messageToUser =
                                getString(R.string.error_timeout) // Request timeout
                            is IOException -> messageToUser =
                                getString(R.string.error_network_generic) // Generic network failure
                            is IllegalArgumentException -> messageToUser =
                                getString(R.string.error_bad_data) // Invalid data
                            is NullPointerException -> messageToUser =
                                getString(R.string.error_null) // Null reference
                            else -> messageToUser =
                                getString(R.string.error_unknown) // Unexpected errors
                        }

                        Snackbar.make(view, messageToUser, Snackbar.LENGTH_LONG).show()
                        newQuotationViewModel.resetError() // Clear the error after showing the message
                    }
                }
            }
        }
    }


    /* Container function to setup the listeners of the fragment and respond to UI actions */
    private fun setupListeners() {
        // The swipe to refresh action triggers a new quotation to show up
        binding.srlRefreshLayoutNewQuotation.setOnRefreshListener {
            newQuotationViewModel.getNewQuotation()
            newQuotationViewModel.notifyFirstRefresh()
        }

        // Setup the action to add a new quotation to the favourites list
        binding.fabAddToFavourites.setOnClickListener {
            newQuotationViewModel.addToFavourites()
        }
    }


    /* Manage the menu of the fragment */
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_new_quotation, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId) {
            R.id.itNewQuotationAccessibility -> {
                newQuotationViewModel.getNewQuotation()
                newQuotationViewModel.notifyFirstRefresh()
                return true
            }
        }
        return false
    }


    /* On destruction, set the binding to null so that it can be garbaged (avoid memory-leaks) */
    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}


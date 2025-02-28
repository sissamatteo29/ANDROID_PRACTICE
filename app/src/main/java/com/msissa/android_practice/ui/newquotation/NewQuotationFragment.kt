package com.msissa.android_practice.ui.newquotation

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
import com.msissa.android_practice.R
import com.msissa.android_practice.databinding.FragmentNewQuotationBinding
import kotlinx.coroutines.launch

class NewQuotationFragment : Fragment(R.layout.fragment_new_quotation), MenuProvider {
    private var _binding : FragmentNewQuotationBinding? = null
    private val binding
        get() = _binding!!

    private val newQuotationViewModel : NewQuotationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentNewQuotationBinding.bind(view)

        // Add the menu to the fragment only when the fragment is active
        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)


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



        // Action listeners

        // The swipe to refresh action triggers a new quotation
        binding.srlRefreshLayoutNewQuotation.setOnRefreshListener {
            newQuotationViewModel.getNewQuotation()
            newQuotationViewModel.notifyFirstRefresh()
        }

        // Manage the action for the add to favourites button
        binding.fabAddToFavourites.setOnClickListener {
            newQuotationViewModel.addToFavourites()
        }






    }  // override fun onViewCreated


    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }


    // Methods to manage the menu of the fragment
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



}


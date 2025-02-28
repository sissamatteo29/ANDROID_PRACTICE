package com.msissa.android_practice.ui.favourites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.msissa.android_practice.R
import com.msissa.android_practice.databinding.FragmentFavouritesBinding
import com.msissa.android_practice.databinding.FragmentNewQuotationBinding

class FavouritesFragment : Fragment(R.layout.fragment_favourites) {
    private var _binding : FragmentFavouritesBinding? = null
    private val binding
        get() = _binding!!

    private val favouritesViewModel : FavouritesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentFavouritesBinding.bind(view)

        // Create the adapter
        val adapter = QuotationListAdapter()

        val recyclerView = binding.rvFavourites
        recyclerView.adapter = adapter
        adapter.submitList(favouritesViewModel.favourites.value)


    }


    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}


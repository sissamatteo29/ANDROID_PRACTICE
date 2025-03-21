package com.msissa.android_practice.ui.favourites

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.msissa.android_practice.R
import com.msissa.android_practice.databinding.FragmentFavouritesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavouritesFragment : Fragment(R.layout.fragment_favourites), MenuProvider {
    private var _binding : FragmentFavouritesBinding? = null
    private val binding
        get() = _binding!!

    private val favouritesViewModel : FavouritesViewModel by activityViewModels()

    /* Define swiping action on a quote from the favourites list */
    private val touchHelper : ItemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.END) {

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun isLongPressDragEnabled(): Boolean {
            return false
        }

        override fun isItemViewSwipeEnabled(): Boolean {
            return true
        }

        /* When the user swipes, delete the quotation */
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            favouritesViewModel.deleteQuotationAtPosition(viewHolder.adapterPosition)
        }
    })


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentFavouritesBinding.bind(view)

        /* Create the adapter to manage the quotations and define the click listener */
        val adapter = QuotationListAdapter({
            authorName -> if(authorName == "Anonymous") {
                Snackbar.make(binding.root, "Anonymous author", Snackbar.LENGTH_SHORT).show()
            } else {
                val url = "https://en.wikipedia.org/wiki/Special:Search?search=$authorName"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

                try {
                    startActivity(intent)
                } catch (e : Exception) {
                    Snackbar.make(binding.root, "Impossible to handle your request", Snackbar.LENGTH_SHORT).show()
                }
            }
        })

        /* Setup the recycler view on the UI with the adapter*/
        val recyclerView = binding.rvFavourites
        recyclerView.adapter = adapter
        adapter.submitList(favouritesViewModel.favourites.value)

        /* Setup the menu for this fragment */
        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        /* Add the touchHelper to the recycler view */
        touchHelper.attachToRecyclerView(binding.rvFavourites)

        /* Observe changes in the ViewModel properties */
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                // Manage visibility of the delete all button
                launch {
                    favouritesViewModel.isDeleteAllMenuVisible.collect {
                        // Every time the boolean changes, invalidate the menu so that the prepare method can be invoked again
                        requireActivity().invalidateMenu()
                    }
                }

                // Update the list of favourites when there is a change
                launch {
                    favouritesViewModel.favourites.collect {
                        newList -> adapter.submitList(newList)
                    }
                }
            }
        }

    }


    /* Setup the menu for this fragment */

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_favourites, menu)

    }


    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId) {
            R.id.itDeleteAllFav -> {
                findNavController().navigate(R.id.action_favouritesFragment_to_deleteAllDialogFragment)
                return true
            }
        }
        return false
    }

    override fun onPrepareMenu(menu: Menu) {
        super.onPrepareMenu(menu)

        /* Visibility of the menu item to delete all quotations in the favourite list */
        val deleteAllQuotations = menu.findItem(R.id.itDeleteAllFav)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                favouritesViewModel.isDeleteAllMenuVisible.collect {
                    deleteAllQuotations.isVisible = favouritesViewModel.isDeleteAllMenuVisible.value
                }
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}


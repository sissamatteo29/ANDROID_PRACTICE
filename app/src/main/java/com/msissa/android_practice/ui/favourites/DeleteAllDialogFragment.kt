package com.msissa.android_practice.ui.favourites

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels

class DeleteAllDialogFragment : DialogFragment() {

    private val viewModel : FavouritesViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Delete all favourite quotations")
            .setMessage("Do you really want to delete all your Quotations?")
            .setPositiveButton("yes") {
                _, _ -> viewModel.deleteAllQuotations()
            }
            .setNegativeButton("no") {
                _, _ -> dismiss()
            }
            .create()
    }
}
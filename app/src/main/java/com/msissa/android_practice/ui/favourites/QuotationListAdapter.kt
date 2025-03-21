package com.msissa.android_practice.ui.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.msissa.android_practice.databinding.QuotationItemBinding
import com.msissa.android_practice.domain.model.Quotation

class QuotationListAdapter(
    private val onItemClick : (String) -> Unit
) : ListAdapter<Quotation, QuotationListAdapter.QuotationViewHolder>(QuotationDiff) {

    /**
     * Singleton instance of the class defining the difference between Quotation elements
     */
    object QuotationDiff : DiffUtil.ItemCallback<Quotation>() {

        override fun areItemsTheSame(oldItem: Quotation, newItem: Quotation): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Quotation, newItem: Quotation): Boolean {
            return oldItem == newItem
        }
    }


    /**
     * ViewHolder class dedicated to a Quotation that actualises the UI for a single Quotation
     */
    class QuotationViewHolder(
        private val binding : QuotationItemBinding,
        val onItemClick : (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(quotation : Quotation) {
            binding.tvQuoteTextFavourite.text = quotation.text
            binding.tvAuthorNameFavourite.text = quotation.author
        }

        init {
            binding.root.setOnClickListener({
                onItemClick(binding.tvAuthorNameFavourite.text.toString())
            })
        }

    }   // class QuotationViewHolder


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = QuotationItemBinding.inflate(inflater, parent, false)
        return QuotationViewHolder(binding, onItemClick)
    }


    override fun onBindViewHolder(holder: QuotationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}
package com.mitchelletakuro.gbemisolatakurofilter.ui.filterList

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mitchelletakuro.gbemisolatakurofilter.databinding.ItemFilterBinding
import com.mitchelletakuro.gbemisolatakurofilter.model.FilterItem
import com.mitchelletakuro.gbemisolatakurofilter.utils.OnAdapterItemClickListener

class FilterAdapter(
    private val item: List<FilterItem>,
    private val clickListener: OnAdapterItemClickListener
) : RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val view = ItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilterViewHolder(
            view
        )
    }

    override fun getItemCount() = item.size

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) =
        holder.bind(item[position], clickListener)

    class FilterViewHolder(private val itemBinding: ItemFilterBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: FilterItem, clickListener: OnAdapterItemClickListener) = with(itemBinding) {
            dateRangeText.text = item.start_year.toString().plus(" - ").plus(item.end_year)
            if (item.gender.isEmpty()) genderText.text = "NONE" else genderText.text = item.gender
            if (item.countries.isEmpty()) countriesText.text = "NONE" else countriesText.text =
                item.countries.toString().replace("[", "").replace("]", "")
            if (item.colors.isEmpty()) colorText.text = "NONE" else colorText.text =
                item.colors.toString().replace("[", "").replace("]", "")
            itemBinding.root.setOnClickListener {
                clickListener.onItemClick(item)
            }
        }
    }
}
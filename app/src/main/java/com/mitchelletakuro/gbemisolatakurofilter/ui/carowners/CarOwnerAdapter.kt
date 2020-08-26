package com.mitchelletakuro.gbemisolatakurofilter.ui.carowners

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mitchelletakuro.gbemisolatakurofilter.databinding.ItemCarOwnersBinding
import com.mitchelletakuro.gbemisolatakurofilter.model.CarOwnerItem

class CarOwnerAdapter(private var data: List<CarOwnerItem>) : RecyclerView.Adapter<CarOwnerAdapter.OwnersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OwnersViewHolder {
        return OwnersViewHolder(
            ItemCarOwnersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: OwnersViewHolder, position: Int) =
        holder.bind(data[position])

    class OwnersViewHolder(private val binding: ItemCarOwnersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CarOwnerItem) = with(binding) {
            binding.name.text = item.first_name.plus(" ").plus(item.last_name)
            binding.genderContent.text = item.gender
            binding.emailContent.text = item.email
            binding.countryContent.text = item.country
            binding.bioContent.text = item.bio
            binding.jobContent.text = item.job_title
            binding.carModelContent.text =
                item.car_model.plus(", ").plus(item.car_color).plus(", ").plus(item.car_model_year)
        }
    }
}
package com.example.android.capstoneproject_aroundtheworld.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.capstoneproject_aroundtheworld.databinding.ItemCountryBinding
import com.example.android.capstoneproject_aroundtheworld.databinding.ItemTripBinding
import com.example.android.capstoneproject_aroundtheworld.models.Trip

class TripsAdapter(var trips: ArrayList<Trip>): RecyclerView.Adapter<TripsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): TripsAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemTripBinding = ItemTripBinding.inflate(inflater, parent, false)
        return TripsAdapter.ViewHolder(
                itemTripBinding)
    }

    class ViewHolder(val binding: ItemTripBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(trip: Trip) {
            binding.tripListNameText.text = trip.name
            binding.tripListDescriptionText.text = trip.description
            binding.tripListDateFromText.text = trip.dateFrom
            binding.tripListDateToText.text = trip.dateTo
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(trips!![position])
    }

    override fun getItemCount(): Int {
        return trips.size
    }
}
package com.example.android.capstoneproject_aroundtheworld.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.capstoneproject_aroundtheworld.databinding.ItemTripBinding
import com.example.android.capstoneproject_aroundtheworld.models.Country
import com.example.android.capstoneproject_aroundtheworld.models.Trip

class TripAdapter(val clicklistener: TripListener
): RecyclerView.Adapter<TripAdapter.ViewHolder>() {

    var trips: List<Trip> = listOf()

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemTripBinding = ItemTripBinding.inflate(inflater, parent, false)
        return ViewHolder(
                itemTripBinding)
    }

    class ViewHolder(val binding: ItemTripBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(trip: Trip,
        clicklistener: TripListener) {
            // Set the trip property to clicked trip
            binding.trip = trip
            binding.clickListener = clicklistener
            binding.tripListNameText.text = trip.name
            binding.tripListDescriptionText.text = trip.description
            binding.tripListDateFromText.text = trip.dateFrom
            binding.tripListDateToText.text = trip.dateTo
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(trips!![position], clicklistener)
    }

    override fun getItemCount(): Int {
        return trips.size
    }

    class TripListener(val clickListener: (Trip?) -> Unit) {
        fun onClick(trip: Trip?) = clickListener(trip)
    }

    // Get current trip item for swipe to delete
    fun getTripAt(position: Int) = trips[position]

}
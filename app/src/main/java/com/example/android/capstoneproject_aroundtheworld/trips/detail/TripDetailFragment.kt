package com.example.android.capstoneproject_aroundtheworld.trips.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.android.capstoneproject_aroundtheworld.R
import com.example.android.capstoneproject_aroundtheworld.databinding.FragmentTripDetailBinding
import com.example.android.capstoneproject_aroundtheworld.databinding.FragmentTripsListBinding
import com.example.android.capstoneproject_aroundtheworld.models.Trip
import com.example.android.capstoneproject_aroundtheworld.trips.TripsViewModel

class TripDetailFragment : Fragment() {

    private lateinit var binding: FragmentTripDetailBinding
    private val viewModel: TripsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_trip_detail, container, false)

        //Initializing ViewModel
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        // Observing changes in TripsList
        viewModel.tripList.observe(viewLifecycleOwner, Observer {
            for (trip in it) {
                binding.trip = trip
            }
        })

        return binding.root
    }

}
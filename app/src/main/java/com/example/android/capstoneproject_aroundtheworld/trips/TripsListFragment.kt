package com.example.android.capstoneproject_aroundtheworld.trips

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.android.capstoneproject_aroundtheworld.R
import com.example.android.capstoneproject_aroundtheworld.databinding.FragmentTripsListBinding

class TripsListFragment : Fragment() {

    private lateinit var binding: FragmentTripsListBinding
    private val viewModel: TripsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Data Binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_trips_list, container, false)

        // Navigation OnclickListener for My Trips Button
        binding.newTripCardView.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_tripsListFragment_to_newTripFragment)
        }

        // Observing changes in TripsList
        viewModel.tripList.observe(viewLifecycleOwner, Observer {
            for (trip in it) {
                DataBindingUtil.inflate<FragmentTripsListBinding>(
                        layoutInflater,
                        R.layout.item_trip,
                        binding.tripsRecycler,
                        true
                ).apply {
                    this.trip = trip

                }
            }
        })

        return binding.root
    }

}
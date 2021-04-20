package com.example.android.capstoneproject_aroundtheworld.trips

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.android.capstoneproject_aroundtheworld.R
import com.example.android.capstoneproject_aroundtheworld.databinding.FragmentTripsListBinding

class TripsListFragment : Fragment() {

    private lateinit var binding: FragmentTripsListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Data Binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_trips_list, container, false)

        // Navigation OnclickListener for My Trips Button
        binding.newTripButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_tripsListFragment_to_newTripFragment)
        }

        return binding.root
    }

}
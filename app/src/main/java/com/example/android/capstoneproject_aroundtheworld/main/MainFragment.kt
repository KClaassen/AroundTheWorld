package com.example.android.capstoneproject_aroundtheworld.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.android.capstoneproject_aroundtheworld.R
import com.example.android.capstoneproject_aroundtheworld.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var binding : FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        // Navigation OnclickListener for Countries Button
        binding.countriesButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_mainFragment_to_countriesListFragment)
        }

        // Navigation OnclickListener for My Trips Button
        binding.myTripsButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_mainFragment_to_tripsListFragment)
        }

        // Navigation OnclickListener for New Trip Button
        binding.newTripButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_mainFragment_to_newTripFragment)
        }

        return binding.root
    }

}
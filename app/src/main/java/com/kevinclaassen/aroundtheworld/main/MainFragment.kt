package com.kevinclaassen.aroundtheworld.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.kevinclaassen.aroundtheworld.R
import com.kevinclaassen.aroundtheworld.databinding.FragmentMainBinding

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

        return binding.root
    }

}
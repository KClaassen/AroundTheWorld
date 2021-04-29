package com.example.android.capstoneproject_aroundtheworld.trips

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.android.capstoneproject_aroundtheworld.R
import com.example.android.capstoneproject_aroundtheworld.databinding.FragmentNewTripBinding
import com.example.android.capstoneproject_aroundtheworld.models.Trip

class NewTripFragment : Fragment() {

    private lateinit var binding: FragmentNewTripBinding
    private val viewModel: TripsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_trip, container, false)

        //Initializing ViewModel
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        binding.trip = Trip(
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        )


        return binding.root
    }

}
package com.example.android.capstoneproject_aroundtheworld.trips

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.capstoneproject_aroundtheworld.R
import com.example.android.capstoneproject_aroundtheworld.adapter.CountryAdapter
import com.example.android.capstoneproject_aroundtheworld.adapter.TripAdapter
import com.example.android.capstoneproject_aroundtheworld.countries.CountriesListFragmentDirections
import com.example.android.capstoneproject_aroundtheworld.countries.CountriesListViewModel
import com.example.android.capstoneproject_aroundtheworld.databinding.FragmentTripsListBinding
import com.example.android.capstoneproject_aroundtheworld.models.Trip
import com.example.android.capstoneproject_aroundtheworld.repository.TripsRepository
import kotlinx.android.synthetic.main.fragment_trips_list.*

class TripsListFragment : Fragment() {

    private lateinit var binding: FragmentTripsListBinding
    private lateinit var adapter: TripAdapter

    /**
     * Lazily initialize our [TripsViewModel].
     */
    private val viewModel: TripsViewModel by lazy {
        val activity = requireNotNull(this.activity) {
        }
        ViewModelProvider(this, TripsViewModel.Factory(activity.application)).get(
                TripsViewModel::class.java
        )
    }

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

        viewModel.getAllTrips().observe(viewLifecycleOwner, Observer {
            adapter.trips = it
            adapter.notifyDataSetChanged()
        })

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
                    viewModel.getAllTrips()
                }
            }
        })

        adapter = TripAdapter(TripAdapter.TripListener {
            //Trip -> Toast.makeText(context, "${Trip}", Toast.LENGTH_SHORT).show()
            Trip ->
            if (Trip != null) {
                viewModel.onTripClicked(Trip)
            }
        })

        viewModel.navigateToTrip.observe(viewLifecycleOwner, Observer { trip ->
            trip?.let {
                this.findNavController().navigate(TripsListFragmentDirections
                        .actionTripsListFragmentToTripDetailFragment(it))
                viewModel.onTripNavigated()
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        trips_recycler.layoutManager = LinearLayoutManager(requireContext())
        trips_recycler.adapter = adapter

//        adapter = TripAdapter(TripAdapter.TripListener {
//            Trip -> Toast.makeText(context, "${Trip}", Toast.LENGTH_SHORT).show()
//            //Country -> viewModel.onTripClicked(Country)
//        })
    }

}
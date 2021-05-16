package com.example.android.capstoneproject_aroundtheworld.trips.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.capstoneproject_aroundtheworld.R
import com.example.android.capstoneproject_aroundtheworld.databinding.FragmentTripDetailBinding
import com.example.android.capstoneproject_aroundtheworld.trips.TripsViewModel
import kotlinx.android.synthetic.main.fragment_trip_detail.*

class TripDetailFragment : Fragment() {

    private lateinit var binding: FragmentTripDetailBinding
    private lateinit var adapter: ImageListAdapter

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
        // Get a reference to the binding object and inflate the fragment views.
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_trip_detail, container, false)

        //Initializing ViewModel
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        val arguments = TripDetailFragmentArgs.fromBundle(requireArguments()).trip
        binding.trip = arguments

        // Observing changes in TripsList
//        viewModel.tripList.observe(viewLifecycleOwner, Observer {
//            for (trip in it) {
//                binding.trip = trip
//            }
//        })

        adapter = ImageListAdapter(ImageListAdapter.ImageListListener {
            Toast.makeText(context, "test", Toast.LENGTH_SHORT).show()
        })

        // TODO: Read the list from the database and always add the empty value to show the add button
        val images = ArrayList<String>()
        images.add("")

        // Observing changes in TripsList
        viewModel.imageList.observe(viewLifecycleOwner, Observer {
            for (image in images) {
                DataBindingUtil.inflate<ItemTripDetailImageBinding>(
                        layoutInflater,
                        R.layout.item_trip_add_image,
                        binding.imageListRecycler,
                        true)
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        image_list_recycler.layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
//        image_list_recycler.adapter = adapter
    }

    companion object {
        private const val CAMERA_PERMISSION_CODE = 1
        private const val CAMERA = 2
    }

}
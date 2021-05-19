package com.example.android.capstoneproject_aroundtheworld.trips.detail

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.capstoneproject_aroundtheworld.R
import com.example.android.capstoneproject_aroundtheworld.adapter.ImageListAdapter
import com.example.android.capstoneproject_aroundtheworld.databinding.FragmentTripDetailBinding
import com.example.android.capstoneproject_aroundtheworld.trips.TripsViewModel
import kotlinx.android.synthetic.main.fragment_trip_detail.*
import kotlinx.android.synthetic.main.item_trip_add_image.*
import kotlinx.android.synthetic.main.item_trip_view_image.*

class TripDetailFragment : Fragment(), ImageListAdapter.ImageListListener {

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

        add_image_card_view.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERA_REQUEST_CODE)
            } else {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
            }
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val images = ArrayList<String>()
        images.add("Path to image 1")
        images.add("Path to image 2")
        images.add("Path to image 3")
        image_list_recycler.layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
        adapter = ImageListAdapter(requireActivity(), this, images)
        image_list_recycler.adapter = adapter
    }

    override fun onClick(string: String) {
        TODO("Not yet implemented")
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERA_REQUEST_CODE)
            } else {
                Toast.makeText(requireContext(), "Permission denied, please check in settings", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE) {
                val thumbnail: Bitmap = data!!.extras!!.get("data") as Bitmap
                trip_detail_view_image.setImageBitmap(thumbnail)
            }
        }
    }

    companion object {
        private const val CAMERA_PERMISSION_CODE = 1
        private const val CAMERA_REQUEST_CODE = 2
    }

}
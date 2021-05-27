package com.example.android.capstoneproject_aroundtheworld.trips.detail

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.text.Layout
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.inflate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.capstoneproject_aroundtheworld.R
import com.example.android.capstoneproject_aroundtheworld.adapter.ImageListAdapter
import com.example.android.capstoneproject_aroundtheworld.databinding.FragmentCountriesListBinding.inflate
import com.example.android.capstoneproject_aroundtheworld.databinding.FragmentTripDetailBinding
import com.example.android.capstoneproject_aroundtheworld.trips.TripsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.fragment_trip_detail.*
import kotlinx.android.synthetic.main.item_trip_add_image.*
import kotlinx.android.synthetic.main.item_trip_view_image.*
import java.net.URI
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf

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
        //requireActivity().window.statusBarColor = Color.WHITE

        //Initializing ViewModel
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        val arguments = TripDetailFragmentArgs.fromBundle(requireArguments()).trip
        binding.trip = arguments


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

    override fun onClick() {
        Log.i("listener", "Camera clicked")
//            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
//                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                startActivityForResult(intent, CAMERA)
//            } else {
//                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), PERMISSION_REQUEST_CODE)
//            }
//        val pictureDialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
//        //pictureDialog.setTitle("Select Action")
//        val pictureDialogItems =
//                arrayOf("Select photo from gallery", "Capture photo from camera")
//        pictureDialog.setItems(
//                pictureDialogItems
//        ) { dialog, which ->
//            when (which) {
//                // Here we have create the methods for image selection from GALLERY
//                0 -> Toast.makeText(requireContext(), "Camera", Toast.LENGTH_SHORT).show()
//                1 -> Toast.makeText(requireContext(), "Gallery", Toast.LENGTH_SHORT).show()
//            }
//        }
//        pictureDialog.show()
        costomImageSelectionDialog()
    }

    private fun choosePhotoFromCameraOrGallery() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                + ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
        + ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            // When permissions not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                            requireActivity(), Manifest.permission.CAMERA
            ) || ActivityCompat.shouldShowRequestPermissionRationale(
                            requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) || ActivityCompat.shouldShowRequestPermissionRationale(
                            requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE
            )) {

                // Explanation of requested permissions, show alert with request explanation
                AlertDialog.Builder(requireContext()).setMessage("Permissions for this feature are turned disabled. Please go to settings to enable these")
                        .setPositiveButton("Settings") { _,_ ->
                            try {
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                val uri = Uri.fromParts("package", activity?.packageName, null)
                                intent.data = uri
                                startActivity(intent)
                            } catch (e: ActivityNotFoundException) {
                                e.printStackTrace()
                            }
                        }.setNegativeButton("Cancel") { dialog, _ ->
                            dialog.dismiss()
                        }.show()
                }
            } else {
                // Display Toast when permissions are already granted
                Toast.makeText(requireContext(), "Permissions already granted", Toast.LENGTH_SHORT).show()
        }
        }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERA)
            } else {
                Toast.makeText(requireContext(), "Permission denied, please check in settings", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA) {
                val thumbnail: Bitmap = data!!.extras!!.get("data") as Bitmap
                trip_detail_view_image.setImageBitmap(thumbnail)
            }
        }
    }

    private fun costomImageSelectionDialog() {
        val dialog = BottomSheetDialog(requireContext())
        val customDialog  = layoutInflater.inflate(R.layout.custom_bottom_dialog_image_selection, null)
        dialog.setContentView(customDialog)
        dialog.show()
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1
        private const val CAMERA = 2
        private const val GALLERY = 3

    }

}
package com.example.android.capstoneproject_aroundtheworld.trips.detail

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.capstoneproject_aroundtheworld.R
import com.example.android.capstoneproject_aroundtheworld.adapter.ImageListAdapter
import com.example.android.capstoneproject_aroundtheworld.databinding.FragmentTripDetailBinding
import com.example.android.capstoneproject_aroundtheworld.models.Trip
import com.example.android.capstoneproject_aroundtheworld.trips.TripsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.custom_bottom_dialog_image_selection.*
import kotlinx.android.synthetic.main.custom_bottom_dialog_image_selection.view.*
import kotlinx.android.synthetic.main.fragment_trip_detail.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*
import kotlin.collections.ArrayList

class TripDetailFragment : Fragment(), ImageListAdapter.ImageListListener {

    private lateinit var binding: FragmentTripDetailBinding
    private lateinit var adapter: ImageListAdapter

    private var images: ArrayList<String> = ArrayList()
    private lateinit var trip: Trip

    // A global variable for stored image path.
    private var imagePath: String = ""


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

        trip = TripDetailFragmentArgs.fromBundle(requireArguments()).trip
        binding.trip = trip

        viewModel.getTripById(trip.id).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            trip = it
            adapter = ImageListAdapter(requireActivity(), this, trip.images)
            image_list_recycler.layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
            image_list_recycler.adapter = adapter
        })

        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        //val images = ArrayList<String>()
////        images.add("Path to image 1")
////        images.add("Path to image 2")
////        images.add("Path to image 3")
//    }

    override fun onClick() {
        Log.i("listener", "Camera clicked")
        customImageSelectionDialog()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA) {

                data?.extras?.let {
                    val thumbnail: Bitmap =
                            data.extras!!.get("data") as Bitmap // Bitmap from camera

                    // Set Capture Image bitmap to the imageView using Glide
                    // Setting ImagePath before adding it in the ArrayList
                    imagePath = saveImageToInternalStorage(thumbnail)
                    // Adding imagePath to ArrayList
                    //images.add(imagePath)
                    Log.i("ImagePath", imagePath)
                    // Send changes to adapter
                    adapter.notifyDataSetChanged()
                    trip.images.add(imagePath)
                    viewModel.updateTripImages(trip)
                }
            } else if (requestCode == GALLERY) {

                data?.let {
                    // Here we will get the select image URI.
                    val selectedPhotoUri = data.data

                    // Set Selected Image URI to the imageView using Glide
                    //val images = ArrayList<String>()
                    //images.add(selectedPhotoUri.toString())
                    adapter.notifyDataSetChanged()

                    trip.images.add(selectedPhotoUri.toString())
                    viewModel.updateTripImages(trip)

//                    Glide.with(requireActivity())
//                            .load(selectedPhotoUri)
//                            .centerCrop()
//                            .diskCacheStrategy(DiskCacheStrategy.ALL)
//                            .listener(object : RequestListener<Drawable> {
//                                override fun onLoadFailed(
//                                        @Nullable e: GlideException?,
//                                        model: Any?,
//                                        target: Target<Drawable>?,
//                                        isFirstResource: Boolean
//                                ): Boolean {
//                                    // log exception
//                                    Log.e("TAG", "Error loading image", e)
//                                    return false // important to return false so the error placeholder can be placed
//                                }
//
//                                override fun onResourceReady(
//                                        resource: Drawable,
//                                        model: Any?,
//                                        target: Target<Drawable>?,
//                                        dataSource: DataSource?,
//                                        isFirstResource: Boolean
//                                ): Boolean {
//
//                                    val bitmap: Bitmap = resource.toBitmap()
//
//                                    imagePath = saveImageToInternalStorage(bitmap)
//                                    Log.i("ImagePath", imagePath)
//                                    return false
//                                }
//                            })
//                            .into(trip_detail_view_image)
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.e("Cancelled", "Cancelled")
        }
    }

    private fun customImageSelectionDialog() {
        val dialog = BottomSheetDialog(requireContext())
        val customDialog  = layoutInflater.inflate(R.layout.custom_bottom_dialog_image_selection, null)
        dialog.setContentView(customDialog)

        customDialog.photo_from_camera.setOnClickListener {
           // Toast.makeText(requireContext(), "Camera", Toast.LENGTH_SHORT).show()
            takePhotoFromCamera(dialog)
            dialog.dismiss()
        }

        customDialog.select_from_gallery.setOnClickListener {
            //Toast.makeText(requireContext(), "Gallery", Toast.LENGTH_SHORT).show()
            choosePhotoFromGallery(dialog)
            dialog.dismiss()
        }

        dialog.show()
    }

    /**
     * A method is used  asking the permission for camera and storage and image capturing and selection from Camera.
     */
    private fun takePhotoFromCamera(dialog: BottomSheetDialog) {
        Log.i("camera", "take photo from camera")
        Dexter.withContext(requireActivity())
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        //Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                )
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        report?.let {
                            // Here after all the permission are granted launch the CAMERA to capture an image.
                            if (report.areAllPermissionsGranted()) {
                                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                startActivityForResult(intent, CAMERA)
                            }
                        }

                    }

                    override fun onPermissionRationaleShouldBeShown(
                            permissions: MutableList<PermissionRequest>?,
                            token: PermissionToken?
                    ) {
                        showRationalDialogForPermissions()
                    }
                }).onSameThread()
                .check()

        dialog.dismiss()
    }

    /**
     * A method is used for image selection from GALLERY / PHOTOS of phone storage.
     */
    private fun choosePhotoFromGallery(dialog: BottomSheetDialog) {
        Dexter.withContext(requireActivity())
                .withPermission(
                        Manifest.permission.READ_EXTERNAL_STORAGE
                        //Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .withListener(object : PermissionListener {

                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        val galleryIntent = Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        )

                        startActivityForResult(galleryIntent, GALLERY)
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        Toast.makeText(
                                requireContext(),
                                "You have denied the storage permission to select image.",
                                Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onPermissionRationaleShouldBeShown(
                            permission: PermissionRequest?,
                            token: PermissionToken?) {
                        showRationalDialogForPermissions()
                    }

                }).onSameThread()
                .check()

        dialog.dismiss()
    }

    /**
     * A function used to show the alert dialog when the permissions are denied and need to allow it from settings app info.
     */
    private fun showRationalDialogForPermissions() {
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



    /**
     * A function to save a copy of an image to internal storage for App to use.
     */
    private fun saveImageToInternalStorage(bitmap: Bitmap): String {
        // Get the context wrapper instance
        val wrapper = ContextWrapper(requireContext())

        // Initializing a new file
        // The bellow line return a directory in internal storage
        // Changed getDir to getExternalFilesDir as per google developer documentation
        var file = wrapper.getExternalFilesDir(IMAGE_DIRECTORY)

        // Mention a file name to save the image
        file = File(file, "${UUID.randomUUID()}.jpg")

        try {
            // Get the file output stream
            val stream: OutputStream = FileOutputStream(file)

            // Compress bitmap
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

            // Flush the stream
            stream.flush()

            // Close stream
            stream.close()
        } catch (e: IOException) { // Catch the exception
            e.printStackTrace()
        }

        // Return the saved image absolute path
        return file.absolutePath
    }

    companion object {
        private const val CAMERA = 1
        private const val GALLERY = 2
        private const val IMAGE_DIRECTORY = "AroundTheWorldImages"

    }

}
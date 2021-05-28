package com.example.android.capstoneproject_aroundtheworld.trips.detail

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
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
import androidx.annotation.Nullable
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.inflate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
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
import kotlinx.android.synthetic.main.custom_bottom_dialog_image_selection.*
import kotlinx.android.synthetic.main.custom_bottom_dialog_image_selection.view.*
import kotlinx.android.synthetic.main.fragment_trip_detail.*
import kotlinx.android.synthetic.main.item_trip_add_image.*
import kotlinx.android.synthetic.main.item_trip_view_image.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.net.URI
import java.util.*
import kotlin.collections.ArrayList
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf

class TripDetailFragment : Fragment(), ImageListAdapter.ImageListListener {

    private lateinit var binding: FragmentTripDetailBinding
    private lateinit var adapter: ImageListAdapter
    private lateinit var images: ArrayList<String>

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

        val arguments = TripDetailFragmentArgs.fromBundle(requireArguments()).trip
        binding.trip = arguments


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val images = ArrayList<String>()
//        images.add("Path to image 1")
//        images.add("Path to image 2")
//        images.add("Path to image 3")
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

    private fun costomImageSelectionDialog() {
        val dialog = BottomSheetDialog(requireContext())
        val customDialog  = layoutInflater.inflate(R.layout.custom_bottom_dialog_image_selection, null)
        dialog.setContentView(customDialog)

        customDialog.photo_from_camera.setOnClickListener {
            Toast.makeText(requireContext(), "Camera", Toast.LENGTH_SHORT).show()
            takePhotoFromCamera()
            dialog.dismiss()
        }

        customDialog.select_from_gallery.setOnClickListener {
            Toast.makeText(requireContext(), "Gallery", Toast.LENGTH_SHORT).show()
            choosePhotoFromGallery()
            dialog.dismiss()
        }

        dialog.show()
    }

    /**
     * A method is used  asking the permission for camera and storage and image capturing and selection from Camera.
     */
    private fun takePhotoFromCamera() {
        Dexter.withActivity(requireActivity())
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                )
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        // Here after all the permission are granted launch the CAMERA to capture an image.
                        if (report!!.areAllPermissionsGranted()) {
                            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            startActivityForResult(intent, CAMERA)
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
    }

    /**
     * A method is used for image selection from GALLERY / PHOTOS of phone storage.
     */
    private fun choosePhotoFromGallery() {
        Dexter.withActivity(requireActivity())
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {

                        // Here after all the permission are granted launch the gallery to select and image.
                        if (report!!.areAllPermissionsGranted()) {

                            val galleryIntent = Intent(
                                    Intent.ACTION_PICK,
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                            )

                            startActivityForResult(galleryIntent, GALLERY)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA) {

                data?.extras?.let {
                    val thumbnail: Bitmap =
                            data.extras!!.get("data") as Bitmap // Bitmap from camera

                    // Set Capture Image bitmap to the imageView using Glide
                    data.data.toString()

                    imagePath = saveImageToInternalStorage(thumbnail)
                    Log.i("ImagePath", imagePath)
                }
            } else if (requestCode == GALLERY) {

                data?.let {
                    // Here we will get the select image URI.
                    val selectedPhotoUri = data.data

                    // Set Selected Image URI to the imageView using Glide
                    images.add(selectedPhotoUri.toString())
                    adapter.notifyDataSetChanged()
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

    /**
     * A function to save a copy of an image to internal storage for HappyPlaceApp to use.
     */
    private fun saveImageToInternalStorage(bitmap: Bitmap): String {
        // Get the context wrapper instance
        val wrapper = ContextWrapper(requireContext())

        // Initializing a new file
        // The bellow line return a directory in internal storage
        /**
         * The Mode Private here is
         * File creation mode: the default mode, where the created file can only
         * be accessed by the calling application (or all applications sharing the
         * same user ID).
         */
        var file = wrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE)

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
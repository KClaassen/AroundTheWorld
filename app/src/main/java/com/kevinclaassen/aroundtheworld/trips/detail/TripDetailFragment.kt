package com.kevinclaassen.aroundtheworld.trips.detail

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.kevinclaassen.aroundtheworld.R
import com.kevinclaassen.aroundtheworld.adapter.ImageListAdapter
import com.kevinclaassen.aroundtheworld.databinding.FragmentTripDetailBinding
import com.kevinclaassen.aroundtheworld.models.Trip
import com.kevinclaassen.aroundtheworld.trips.TripsViewModel
import kotlinx.android.synthetic.main.custom_bottom_dialog_image_selection.view.*
import kotlinx.android.synthetic.main.fragment_trip_detail.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*


class TripDetailFragment : Fragment(), ImageListAdapter.ImageListListener, ImageListAdapter.ImageExpandListener {

    private lateinit var binding: FragmentTripDetailBinding
    private lateinit var adapter: ImageListAdapter

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

        //Initializing ViewModel
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        trip = TripDetailFragmentArgs.fromBundle(requireArguments()).trip
        binding.trip = trip

        viewModel.getTripById(trip.id).observe(viewLifecycleOwner, {
            trip = it
            adapter = ImageListAdapter(requireActivity(), this, this, trip.images)
            image_list_recycler.layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
            image_list_recycler.adapter = adapter
        })

        return binding.root
    }

    override fun onClick() {
        Log.i("listener", "Camera clicked")
        customImageSelectionDialog()
    }

    override fun onImageClick(imagePath: String) {
        Log.i("expand image", "expand image clicked")
        Glide.with(requireActivity())
                .load(imagePath)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                    ): Boolean {
                        e?.printStackTrace()
                        Log.i("onLoadFailed", "onLoadFailed called")
                        return false
                    }

                    override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                    ): Boolean {
                        Log.i("onResourceReady", "onResourceReady called")
                        binding.tripDetailLayout.transitionToStart()
                        binding.tripDetailLayout.transitionToEnd()

                        return false
                    }
                })
                .into(expanded_trip_view_image)

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

                    // Implement for persistent data permission otherwise this will be lost after reboot
                    val contentResolver = requireContext().applicationContext.contentResolver

                    val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                            Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    // Check for the freshest data.
                    contentResolver.takePersistableUriPermission(data.data!!, takeFlags)


                    adapter.notifyDataSetChanged()

                    trip.images.add(selectedPhotoUri.toString())
                    viewModel.updateTripImages(trip)
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
            Log.i("camera click", "camera clicked")
            takePhotoFromCamera(dialog)
            dialog.dismiss()
        }

        customDialog.select_from_gallery.setOnClickListener {
            Log.i("gallery click", "gallery clicked")
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
                .withPermission(
                        Manifest.permission.READ_EXTERNAL_STORAGE
                )
                .withListener(object : PermissionListener {

                    override fun onPermissionGranted(permission: PermissionGrantedResponse?) {
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(intent, CAMERA)
                    }

                    override fun onPermissionDenied(permission: PermissionDeniedResponse?) {
                        Toast.makeText(
                                requireContext(),
                                "You have denied the permission to take picture.",
                                Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
                        showRationalDialogForPermissions()

                        token?.continuePermissionRequest()
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
                )
                .withListener(object : PermissionListener {

                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        val galleryIntent = Intent(
                                Intent.ACTION_OPEN_DOCUMENT,
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

                        token?.continuePermissionRequest()
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
                .setPositiveButton("Settings") { _, _ ->
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
package com.example.android.capstoneproject_aroundtheworld.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.capstoneproject_aroundtheworld.R
import com.example.android.capstoneproject_aroundtheworld.databinding.ItemTripAddImageBinding
import com.example.android.capstoneproject_aroundtheworld.databinding.ItemTripViewImageBinding
import com.example.android.capstoneproject_aroundtheworld.trips.detail.TripDetailFragment
import kotlinx.android.synthetic.main.item_trip_add_image.view.*
import kotlinx.android.synthetic.main.item_trip_view_image.view.*

class ImageListAdapter(
    val context: Context, val clicklistener: ImageListListener, val images: ArrayList<String>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val IMAGE_ADD = 1
        private const val IMAGE_VIEW = 2
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): RecyclerView.ViewHolder {
        if (viewType == IMAGE_ADD) {
                 val inflater = LayoutInflater.from(parent.context)
                 val ItemTripAddBinding = ItemTripAddImageBinding.inflate(inflater, parent, false)
            return ImageAddViewHolder(ItemTripAddBinding)
        } else {
            val inflater = LayoutInflater.from(parent.context)
            val ItemTripViewBinding = ItemTripViewImageBinding.inflate(inflater, parent, false)
            return ImageViewViewHolder(ItemTripViewBinding)
            }
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ImageAddViewHolder) {
            holder.addItem.setImageResource(R.drawable.outline_add_24)
        } else if (holder is ImageViewViewHolder) {
            // Add image from Camera or Gallery
            //holder.viewItem.setImageResource()
        }

    }

    class ImageAddViewHolder(val binding: ItemTripAddImageBinding) : RecyclerView.ViewHolder(binding.root) {

        val addItem = binding.tripDetailAddImage

        fun addBindView(listener: ImageListListener) {
            binding.listener = listener
            listener.onClick()
        }
    }

    class ImageViewViewHolder(val binding: ItemTripViewImageBinding) : RecyclerView.ViewHolder(binding.root) {

        val viewItem = binding.tripDetailViewImage

//        fun bindView(position: Int) {
//
//        }
    }


    override fun getItemCount(): Int {
        return images.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return IMAGE_ADD
        } else {
            return IMAGE_VIEW
        }
    }

    interface ImageListListener {
        fun onClick()
    }

}
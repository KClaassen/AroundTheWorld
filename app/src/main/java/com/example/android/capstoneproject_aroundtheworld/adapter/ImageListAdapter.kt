package com.example.android.capstoneproject_aroundtheworld.adapter

import android.animation.LayoutTransition
import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.android.capstoneproject_aroundtheworld.R
import com.example.android.capstoneproject_aroundtheworld.databinding.ItemTripAddImageBinding
import com.example.android.capstoneproject_aroundtheworld.databinding.ItemTripViewImageBinding
import com.example.android.capstoneproject_aroundtheworld.models.TripImage

class ImageListAdapter(val context: Context, val clicklistener: ImageListListener
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val IMAGE_ADD = 0
        private const val IMAGE_VIEW = 1
    }

    var images: List<String> = listOf()


    private inner class ImageAddViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(position: Int) {

        }
    }

    private inner class ImageViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(position: Int) {

        }
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            IMAGE_ADD -> {
                val view = LayoutInflater.from(context).inflate(R.layout.item_trip_add_image, parent, false)
                ImageAddViewHolder(view)
            }
            IMAGE_VIEW -> {
                val view = LayoutInflater.from(context).inflate(R.layout.item_trip_view_image, parent, false)
                ImageAddViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        if (images[position].viewType === VIEW_TYPE_ONE) {
//            (holder as ImageAddViewHolder).bind(position)
//        } else {
//            (holder as ImageViewViewHolder).bind(position)
//        }

    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun getItemViewType(position: Int): Int {
        val comparable = images[position]
        return when (comparable) {
            is String -> IMAGE_ADD
            is String -> IMAGE_VIEW
            else -> throw IllegalArgumentException("Invalid type of data " + position)
        }
    }

    class ImageListListener(val clickListener: () -> Unit) {
        fun onClick() = clickListener()
    }

}
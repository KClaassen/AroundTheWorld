package com.example.android.capstoneproject_aroundtheworld.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.android.capstoneproject_aroundtheworld.R
import com.example.android.capstoneproject_aroundtheworld.databinding.ItemTripDetailImageBinding

class ImageListAdapter(val clicklistener: ImageListListener
): RecyclerView.Adapter<ImageListAdapter.ViewHolder>() {

    companion object {
        private const val IMAGE_ADD = 0
        private const val IMAGE_VIEW = 1
    }

    var images: List<String> = listOf()

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val itemTripDetailImageBinding = ItemTripDetailImageBinding.inflate(inflater, parent, false)
        return ViewHolder(
                itemTripDetailImageBinding)
    }

    class ViewHolder(val binding: ItemTripDetailImageBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(image: String,
                     clickListener: ImageListListener) {

            class ImageAddViewHolder(itemView: View) : ViewHolder(binding) {
                override fun bind(item: DataModel) {
                    //Do your view assignment here from the data model
                    binding.tripDetailImage.setImageResource(R.drawable.outline_add_24)
                    binding.listener = clickListener
                }
            }

            class ImageViewHolder(itemView: View) : ViewHolder(binding) {
                override fun bind(item: DataModel) {
                    //Do your view assignment here from the data model
                    binding.tripDetailImage.setImageURI(image.toUri())
                }
            }

//            // If image is empty, then we should render an add photo button
//            // otherwise just render the photo
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(images!![position], clicklistener)
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
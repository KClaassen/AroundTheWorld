package com.example.android.capstoneproject_aroundtheworld.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.android.capstoneproject_aroundtheworld.R
import com.example.android.capstoneproject_aroundtheworld.databinding.ItemTripDetailImageBinding

class ImageListAdapter(val clicklistener: ImageListListener
): RecyclerView.Adapter<ImageListAdapter.ViewHolder>() {

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

            // If image is empty, then we should render an add photo button
            // otherwise just render the photo

            if (image == "") {
                binding.tripDetailImage.setImageResource(R.drawable.outline_add_24)
                binding.listener = clickListener
            } else {
                binding.tripDetailImage.setImageURI(image.toUri())
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(images!![position], clicklistener)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    class ImageListListener(val clickListener: () -> Unit) {
        fun onClick() = clickListener()
    }

}
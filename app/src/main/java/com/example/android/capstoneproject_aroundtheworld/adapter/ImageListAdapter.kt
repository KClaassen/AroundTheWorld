package com.example.android.capstoneproject_aroundtheworld.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.android.capstoneproject_aroundtheworld.databinding.ItemTripAddImageBinding
import com.example.android.capstoneproject_aroundtheworld.databinding.ItemTripViewImageBinding

class ImageListAdapter(
    val context: Context, val imageListListener: ImageListListener, val images: ArrayList<String>
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
        when(getItemViewType(position)) {
            IMAGE_ADD -> {
                (holder as ImageAddViewHolder).bind(imageListListener)
            }
            IMAGE_VIEW -> {
                (holder as ImageViewViewHolder).bind(images[position - 1], context)
            }
        }

    }

    class ImageAddViewHolder(val binding: ItemTripAddImageBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: ImageListListener) {
            binding.addImageCardView.setOnClickListener {
                listener.onClick()
            }
        }
    }

    class ImageViewViewHolder(val binding: ItemTripViewImageBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(imagePath: String, context: Context) {
            //binding.tripDetailViewImage.setImageResource(imagePath)
            Glide.with(context)
                    .load(imagePath)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(binding.tripDetailViewImage)
        }
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
package com.kevinclaassen.aroundtheworld.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kevinclaassen.aroundtheworld.models.Country
import com.kevinclaassen.aroundtheworld.databinding.ItemCountryBinding

class CountryAdapter(val listener: CountryListener): ListAdapter<Country, CountryAdapter.ViewHolder>(DiffCallBack()) {

    class DiffCallBack : DiffUtil.ItemCallback<Country>() {
        override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem == newItem
        }

    }

    private var countries: List<Country> = listOf()

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemCountryBinding = ItemCountryBinding.inflate(inflater, parent, false)
        return ViewHolder(
                itemCountryBinding)
    }

    class ViewHolder(val binding: ItemCountryBinding) : RecyclerView.ViewHolder(binding.root) {

        // Added countryCheckCallBack to listen to changes for countries selected (for country count)
        fun bindView(country: Country, listener: CountryListener) {
            //Holds the Textview that will add each item to
            binding.clickListener = listener
            binding.countryListName.text = country.name
            binding.country = country
            binding.countrySelectButton.setOnCheckedChangeListener { buttonView, isChecked ->
                if (buttonView.isPressed) {
                    country.isSelected = isChecked
                    listener.onChecked(country)
                }
            }

        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Added ::selectedCountriesCount which is the function below to get the number of selected countries
        holder.bindView(countries!![position], listener)
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    fun setData(country: List<Country>?) {
        if (country != null) {
            this.countries = country
        };
    }

    interface CountryListener {
        fun onClick(country: Country) = Unit
        fun onChecked(country: Country) = Unit
    }
}
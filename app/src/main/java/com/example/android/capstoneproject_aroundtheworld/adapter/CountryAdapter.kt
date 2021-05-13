package com.example.android.capstoneproject_aroundtheworld.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.capstoneproject_aroundtheworld.models.Country
import com.example.android.capstoneproject_aroundtheworld.countries.CountriesListFragment
import com.example.android.capstoneproject_aroundtheworld.databinding.ItemCountryBinding

class CountryAdapter(val listener: CountryListener,
                     //val context: CountriesListFragment,
                     //val fragment: Fragment
                     ): ListAdapter<Country, CountryAdapter.ViewHolder>(DiffCallBack()) {

//    // Live Data to keep track of Countries count selected
//    private val _selectedCountriesCount = MutableLiveData(0)
//    val selectedCountriesCount: LiveData<Int>
//        get() = _selectedCountriesCount

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
        fun bindView(//context: CountriesListFragment,
                     country: Country,
                     listener: CountryListener) {
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
//    fun selectedCountriesCount() {
//        // Added _selectedCountriesCount.value which connects to the LiveData to keep track of selected countries
//        _selectedCountriesCount.value = countries.filter { it.isSelected }.size
//    }
}
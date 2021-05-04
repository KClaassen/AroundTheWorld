package com.example.android.capstoneproject_aroundtheworld.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.android.capstoneproject_aroundtheworld.models.Country
import com.example.android.capstoneproject_aroundtheworld.countries.CountriesListFragment
import com.example.android.capstoneproject_aroundtheworld.databinding.ItemCountryBinding

class CountryAdapter(val clicklistener: CountryListener,
                     //val context: CountriesListFragment,
                     //val fragment: Fragment
                     ): RecyclerView.Adapter<CountryAdapter.ViewHolder>() {

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

        fun bindView(//context: CountriesListFragment,
                     country: Country,
                     clicklistener: CountryListener) {
            //Holds the Textview that will add each item to
            binding.clickListener = clicklistener
            binding.countryListName.text = country.name
            binding.country = country
            binding.countrySelectButton.setOnCheckedChangeListener { buttonView, isChecked ->
                country.isSelected = isChecked
            }

        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(countries!![position], clicklistener)
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    fun setData(country: List<Country>?) {
        if (country != null) {
            this.countries = country
        };
    }

    class CountryListener(val clickListener: (Country) -> Unit) {
        fun onClick(country: Country) = clickListener(country)
    }


    fun selectedCountriesCount() {
        countries.filter { it.isSelected }.size
    }
}
package com.example.android.capstoneproject_aroundtheworld.countries

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.capstoneproject_aroundtheworld.R
import com.example.android.capstoneproject_aroundtheworld.adapter.CountryAdapter
import com.example.android.capstoneproject_aroundtheworld.databinding.FragmentCountriesListBinding
import com.example.android.capstoneproject_aroundtheworld.models.Country
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_countries_list.*

class CountriesListFragment : Fragment(), CountryAdapter.CountryListener {

    private lateinit var binding: FragmentCountriesListBinding
    private lateinit var countryAdapter: CountryAdapter

    /**
     * Lazily initialize our [CountriesListViewModel].
     */
    private val viewModel: CountriesListViewModel by lazy {
        val activity = requireNotNull(this.activity) {
        }
        ViewModelProvider(this, CountriesListViewModel.Factory(activity.application)).get(
                CountriesListViewModel::class.java
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Data Binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_countries_list, container, false)

        binding.viewmodel = viewModel

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        viewModel.countryListLiveData.observe(viewLifecycleOwner, Observer {
            processList(it)
        })
        
//        viewModel.errorStateLiveData.observe(viewLifecycleOwner, Observer {
//            Snackbar.make(this.requireView(), "Error, please try again", Snackbar.LENGTH_SHORT).show()
//        })

        fetchCountries()

        viewModel.navigateToCountry.observe(viewLifecycleOwner, Observer { country ->
            country?.let {
                this.findNavController().navigate(CountriesListFragmentDirections
                        .actionCountriesListFragmentToCountryDetailFragment(country))
                viewModel.onCountryNavigated()
            }
        })


//        val adapter = CountryAdapter(CountryAdapter.CountryListener {
//            Country -> Toast.makeText(context, "${Country}", Toast.LENGTH_SHORT).show()
//        })

        //binding.countriesRecycler.adapter = adapter

//        binding.countriesRecycler.layoutManager = LinearLayoutManager(requireContext())
//        val adapter = countryAdapter
//        binding.countriesRecycler.adapter = adapter

        return binding.root
    }

    private fun fetchCountries(){
       viewModel.getDataFromRepo()
    }

    private fun processList(it: List<Country>?) {
//        val countryAdapter = CountryAdapter(CountryAdapter.CountryListener {
//            //Country -> Toast.makeText(context, "${Country}", Toast.LENGTH_SHORT).show()
//            Country -> viewModel.onCountryClicked(Country)
//        })
        val countryAdapter = CountryAdapter(this)
        countries_recycler.layoutManager = LinearLayoutManager(requireContext())
        countries_recycler.adapter = countryAdapter
        countryAdapter.setData(it)
        progress_bar.visibility = View.GONE

//        // Country count Observer and translates the size to String
//        countryAdapter.selectedCountriesCount.observe(viewLifecycleOwner) {
//            selectedCountryCount -> binding.countriesBeenCount.text = selectedCountryCount.toString()
//        }
    }

    companion object {
        const val TAG = "CountriesListFragment"
    }

    override fun onClick(country: Country) {
        viewModel.onCountryClicked(country)
    }

    override fun onChecked(country: Country) {
        viewModel.updateCountry(country)
    }

}
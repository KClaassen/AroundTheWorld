package com.example.android.capstoneproject_aroundtheworld.countries

import android.app.Application
import android.util.Log
import android.view.animation.Transformation
import androidx.lifecycle.*
import com.example.android.capstoneproject_aroundtheworld.adapter.CountryAdapter
import com.example.android.capstoneproject_aroundtheworld.data.CountryDatabase.Companion.getDatabase
//import com.example.android.capstoneproject_aroundtheworld.data.CountryDatabase
//import com.example.android.capstoneproject_aroundtheworld.data.CountryDatabase.Companion.getDatabase
import com.example.android.capstoneproject_aroundtheworld.models.Country
import com.example.android.capstoneproject_aroundtheworld.repository.CountriesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CountriesListViewModel(application: Application) : ViewModel() {

    private val database = getDatabase(application)
    private val repository = CountriesRepository(database)
   // private var countries: List<Country> = listOf()

    /**
     *  Countries List
     */

    var countryListLiveData: LiveData<List<Country>> = database.countryDao.getCountries()
    //val errorStateLiveData: MutableLiveData<String> = MutableLiveData()

    init {
        viewModelScope.launch {
            try {
                countryListLiveData = repository.countries
            } catch (e: java.lang.Exception) {
                Log.e("CountriesListViewModel", e.message!!)
            }
        }
    }

    fun getDataFromRepo()  {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                repository.getAllCountries()
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    /**
     *  Count number of countries checked
     */

    val selectCountriesCount = Transformations.map(LiveData<Country>())

    // Live Data to keep track of Countries count selected
    private val _selectedCountriesCount = MutableLiveData(0)
    val selectedCountriesCount: LiveData<Int>
        get() = _selectedCountriesCount

    fun updateCountry(country: Country) {
//        // Added _selectedCountriesCount.value which connects to the LiveData to keep track of selected countries
        CoroutineScope(Dispatchers.IO).launch {
            _selectedCountriesCount.value = 0
            database.countryDao.updateCountry(country)
        }

    }

    /**
     *  Navigate to Detail Screen
     */
    // To navigate and complete navigation for selected Country onclick

    private val _navigateToCountry = MutableLiveData<Country>()
    val navigateToCountry
        get() = _navigateToCountry

    fun onCountryClicked(id: Country){
        _navigateToCountry.value = id
    }

    fun onCountryNavigated() {
        _navigateToCountry.value = null
    }

    // Factory for constructing CountriesListViewModel with parameter
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CountriesListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CountriesListViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}


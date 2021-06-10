package com.kevinclaassen.aroundtheworld.countries

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.kevinclaassen.aroundtheworld.data.CountryDatabase.Companion.getDatabase
import com.kevinclaassen.aroundtheworld.models.Country
import com.kevinclaassen.aroundtheworld.repository.CountriesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CountriesListViewModel(application: Application) : ViewModel() {

    private val database = getDatabase(application)
    private val repository = CountriesRepository(database)


    /**
     *  Countries List
     */

    var countryListLiveData: LiveData<List<Country>> = database.countryDao.getCountries()

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

    fun counter(): LiveData<Int> = Transformations.map(countryListLiveData) { countries ->
        countries.filter { it.isSelected }.size
    }

    fun updateCountry(country: Country) {
        CoroutineScope(Dispatchers.IO).launch {
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


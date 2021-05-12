package com.example.android.capstoneproject_aroundtheworld.countries

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
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

    fun updateCountry(country: Country) {

        database.countryDao.updateCountry(country)
    }

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


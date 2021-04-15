package com.example.android.capstoneproject_aroundtheworld.countries

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.android.capstoneproject_aroundtheworld.models.Country
import com.example.android.capstoneproject_aroundtheworld.repository.CountriesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CountriesListViewModel(application: Application) : ViewModel() {

//    private val database = CountryDatabase.getDatabase(application)
    private val repository = CountriesRepository()

    var countryListLiveData: MutableLiveData<List<Country>> = MutableLiveData()
    val errorStateLiveData: MutableLiveData<String> = MutableLiveData()

    init {
        viewModelScope.launch {
            try {
                countryListLiveData.postValue(repository.getAllCountries())
            } catch (e: java.lang.Exception) {
                Log.e("CountriesListViewModel", e.message!!)
            }
        }
    }

    fun getDataFromRepo()  {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val list = repository.getAllCountries()
                countryListLiveData.postValue(list)
            }catch (e:Exception){
                errorStateLiveData.postValue("Error occurred, Please try again!!")
            }
        }
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


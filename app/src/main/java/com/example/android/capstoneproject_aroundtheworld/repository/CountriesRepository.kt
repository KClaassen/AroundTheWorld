package com.example.android.capstoneproject_aroundtheworld.repository

import android.util.Log
import com.example.android.capstoneproject_aroundtheworld.models.Country
import com.example.android.capstoneproject_aroundtheworld.models.Currency
import com.example.android.capstoneproject_aroundtheworld.models.Language
import com.example.android.capstoneproject_aroundtheworld.network.CountryApi

class CountriesRepository() {



//    val countriesList: LiveData<List<Country>> = Transformations.map(countryDao()) {
//        it.asDomainModel()
//    }

    suspend fun getAllCountries(): List<Country>? {
//        var listOfCountries: List<CountryItem>? = ArrayList()
        var listOfCountries: List<Country>? = ArrayList()
        try {
            listOfCountries = CountryApi.retrofitService.getCountries()
            /*if (response != null) {
                listOfCountries = getAllCountries()
            }*/
        } catch (e:Exception) {
            e.printStackTrace()
        }
        Log.i("Repository", "${listOfCountries}")
//        listOfCountries = ArrayList<Country>()
//        listOfCountries.add(Country("Capital", Currency("currency"), "flag", Language("spanish"),"the name","he region"))
        return listOfCountries
    }
}
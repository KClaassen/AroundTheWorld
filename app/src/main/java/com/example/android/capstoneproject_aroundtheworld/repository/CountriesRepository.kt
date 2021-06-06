package com.example.android.capstoneproject_aroundtheworld.repository

import android.util.Log
import com.example.android.capstoneproject_aroundtheworld.data.CountryDatabase
import com.example.android.capstoneproject_aroundtheworld.models.Country
import com.example.android.capstoneproject_aroundtheworld.network.CountryApi

class CountriesRepository(private val database: CountryDatabase) {

    val countries = database.countryDao.getCountries()

    suspend fun getAllCountries() {
        val countries = CountryApi.retrofitService.getCountries()
        var listOfCountries: List<Country>? = ArrayList()
        if (listOfCountries == null) {
            try {
                listOfCountries = countries
            } catch (e: Exception) {
                e.printStackTrace()
            }
            Log.i("Repository", "${listOfCountries}")
        }
//        listOfCountries = ArrayList<Country>()
//        listOfCountries.add(Country("Capital", Currency("currency"), "flag", Language("spanish"),"the name","he region"))
        //database.countryDao.getCountries()
        database.countryDao.insertAll(countries)
    }
}
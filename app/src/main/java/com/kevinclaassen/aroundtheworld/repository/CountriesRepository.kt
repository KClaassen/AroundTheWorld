package com.kevinclaassen.aroundtheworld.repository

import android.util.Log
import com.kevinclaassen.aroundtheworld.data.CountryDatabase
import com.kevinclaassen.aroundtheworld.models.Country
import com.kevinclaassen.aroundtheworld.network.CountryApi

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
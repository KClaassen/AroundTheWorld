package com.kevinclaassen.aroundtheworld.repository

import com.kevinclaassen.aroundtheworld.data.CountryDatabase
import com.kevinclaassen.aroundtheworld.network.CountryApi

class CountriesRepository(private val database: CountryDatabase) {

    val countries = database.countryDao.getCountries()

    suspend fun getAllCountries() {
        val countries = CountryApi.retrofitService.getCountries()
        database.countryDao.insertAll(countries)
    }
}
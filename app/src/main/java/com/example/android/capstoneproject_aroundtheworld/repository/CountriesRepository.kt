package com.example.android.capstoneproject_aroundtheworld.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.android.capstoneproject_aroundtheworld.data.CountryDatabase
//import com.example.android.capstoneproject_aroundtheworld.data.CountryDatabase
import com.example.android.capstoneproject_aroundtheworld.models.Country
import com.example.android.capstoneproject_aroundtheworld.network.CountryApi
import com.example.android.capstoneproject_aroundtheworld.network.CountryApiService
import com.example.android.capstoneproject_aroundtheworld.network.retrofitRESTClient
import kotlinx.android.synthetic.main.fragment_trips_list.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.doAsyncResult
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountriesRepository(
        private val database: CountryDatabase
        ) {

    //fun updateCountry = database.countryDao.updateCountry()

    val countries = database.countryDao.getCountries()

    suspend fun getAllCountries() {
        val countries = CountryApi.retrofitService.getCountries()
        var listOfCountries: List<Country>? = ArrayList()
        try {
            listOfCountries = countries
        } catch (e:Exception) {
            e.printStackTrace()
        }
        Log.i("Repository", "${listOfCountries}")
//        listOfCountries = ArrayList<Country>()
//        listOfCountries.add(Country("Capital", Currency("currency"), "flag", Language("spanish"),"the name","he region"))
        //database.countryDao.getCountries()
        database.countryDao.insertAll(countries)
    }
}
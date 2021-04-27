package com.example.android.capstoneproject_aroundtheworld.repository

import android.util.Log
import com.example.android.capstoneproject_aroundtheworld.models.Country
import com.example.android.capstoneproject_aroundtheworld.models.Currency
import com.example.android.capstoneproject_aroundtheworld.models.Language
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

class CountriesRepository() {



//    val countriesList: LiveData<List<Country>> = Transformations.map(countryDao()) {
//        it.asDomainModel()
//    }

//    fun fetchCountries(): ArrayList<Country> {
//        var listOfCountries: ArrayList<Country> = ArrayList()
//        val countryApiService: CountryApiService = retrofitRESTClient.create(CountryApiService::class.java)
//        val call = countryApiService.getCountries2()
//        try {
//
//            val response: Response<List<Map<String, Object>>>? = call.execute()
//            val listOfCountriesResponse: List<Map<String, Object>>? = response!!.body()
//
//            val listOfCountriesIterator = listOfCountriesResponse?.iterator()
//
//            while (listOfCountriesIterator?.hasNext() == true) {
//                val countryResponse = listOfCountriesIterator.next()
//                val currencyResponse = countryResponse.getValue("currencies") as List<Map<String, String>>
//                val languageResponse = countryResponse.getValue("languages")  as List<Map<String, String>>
//                // - Create a currency object
//                val currency = Currency(currencyResponse[0].getValue("name"))
//                // - Create language object
//                val language = Language(languageResponse[0].getValue("name"))
//                // - Create a country object
//                val country = Country(
//                        countryResponse.getValue("name") as String,
//                        countryResponse.getValue("capital") as String,
//                        countryResponse.getValue("region") as String,
//                        countryResponse.getValue("flag") as String,
//                        currency,
//                        language)
//                // - Add the country object to the listOfCountries array
//                listOfCountries.add(country)
//            }
//        } catch (ex: Exception) {
//            Log.e("RESPONSE_ERROR", ex.message.toString())
//        }
//        return listOfCountries
//    }

    suspend fun getAllCountries(): List<Country>? {
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
        return listOfCountries
    }
}
package com.example.android.capstoneproject_aroundtheworld.network

import com.example.android.capstoneproject_aroundtheworld.models.Country
import com.example.android.capstoneproject_aroundtheworld.utils.Constants.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val retrofitRESTClient = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface CountryApiService {

    @GET("all?fields=name;capital;currencies;languages;flag;subregion")
    suspend fun getCountries():List<Country>

//    @GET("all?fields=name;capital;currencies;languages;flag;region")
//    fun getCountries2(): Call<List<Map<String, Object>>>

}

object CountryApi {
    val retrofitService: CountryApiService by lazy {
        retrofitRESTClient.create(CountryApiService::class.java)
    }
}
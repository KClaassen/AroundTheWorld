package com.example.android.capstoneproject_aroundtheworld.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

// use this class as a return type of your api call
// suspend fun getCountries():List<MainObject>
@Parcelize
data class Country(
        val currencies: List<Currency>,
        val languages: List<Language>,
        val flag: String,
        val name: String,
        val capital: String,
        val region: String
):Parcelable
// all the properties default set to null because for some of the object they don't exist.
@Parcelize
data class Currency(
        val code: String? = null,
        val name: String? = null,
        val symbol: String? = null
):Parcelable

@Parcelize
data class Language(
        @Json(name = "iso639_1")
        val iso6391: String? = null,
        @Json(name = "iso639_2")
        val iso6392: String? = null,
        val name: String? = null,
        val nativeName: String? = null
):Parcelable
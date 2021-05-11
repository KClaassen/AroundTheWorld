package com.example.android.capstoneproject_aroundtheworld.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

// use this class as a return type of your api call
// suspend fun getCountries():List<MainObject>
@Parcelize
@Entity()
data class Country(
        @SerializedName("Currency")
        val currencies: List<Currency>,
        @SerializedName("Language")
        val languages: List<Language>,
        val flag: String,
        @PrimaryKey
        val name: String,
        val capital: String,
        val region: String
):Parcelable {
        @Ignore
        var isSelected: Boolean = false
        // all the properties default set to null because for some of the object they don't exist.
        @Parcelize
        data class Currency(
                //val code: String? = null,
                val name: String?,
                //val name: String? = null,
                //val symbol: String? = null
        ) : Parcelable

        @Parcelize
        data class Language(
//        @Json(name = "iso639_1")
//        val iso6391: String? = null,
//        @Json(name = "iso639_2")
//        val iso6392: String? = null,
                val name: String?,
                //val name: String? = null,
                // val nativeName: String? = null
        ) : Parcelable
}
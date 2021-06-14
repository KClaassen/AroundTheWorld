package com.kevinclaassen.aroundtheworld.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

// use this class as a return type of your api call
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
        val subregion: String
):Parcelable {
        var isSelected: Boolean = false
        // all the properties default set to null because for some of the object they don't exist.
        @Parcelize
        data class Currency(
                val name: String?,
        ) : Parcelable

        @Parcelize
        data class Language(
                val name: String?,
        ) : Parcelable
}
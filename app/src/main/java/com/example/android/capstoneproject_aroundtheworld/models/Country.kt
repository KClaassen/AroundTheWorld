package com.example.android.capstoneproject_aroundtheworld.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Country(
        val name: String?,
        val capital: String?,
        val region: String?,
        val flag: String?,
        val currency: Currency?,
        val language: Language?

) : Parcelable

@Parcelize
data class Currency(
        val name: String?
) : Parcelable

@Parcelize
data class Language(
        val name: String?
) : Parcelable
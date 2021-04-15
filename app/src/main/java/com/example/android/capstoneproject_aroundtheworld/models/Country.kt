package com.example.android.capstoneproject_aroundtheworld.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Country(
        val capital: String?,
        val currency: Currency?,
        val flag: String?,
        val language: Language?,
        val name: String?,
        val region: String?
)

data class Currency(
        val name: String?
)

data class Language(
        val name: String?
)
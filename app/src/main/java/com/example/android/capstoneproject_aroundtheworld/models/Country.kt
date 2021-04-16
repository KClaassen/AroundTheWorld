package com.example.android.capstoneproject_aroundtheworld.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Country(
        val capital: String?,
        val currency: Currency?,
        val flag: String?,
        val language: Language?,
        val name: String?,
        val region: String?
) : Parcelable

@Parcelize
data class Currency(
        val name: String?
) : Parcelable

@Parcelize
data class Language(
        val name: String?
) : Parcelable
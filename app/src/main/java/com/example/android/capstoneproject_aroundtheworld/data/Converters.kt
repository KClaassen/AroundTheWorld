package com.example.android.capstoneproject_aroundtheworld.data

import android.net.Uri
import androidx.room.TypeConverter
import com.example.android.capstoneproject_aroundtheworld.models.Country
import com.example.android.capstoneproject_aroundtheworld.models.Trip
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converters {

    @TypeConverter
    fun fromCurrencytoJson(value: String): List<Country.Currency>? {
        val listType: Type =
                object : TypeToken<List<Country.Currency>>() {}.type
        return Gson().fromJson<List<Country.Currency>>(value, listType)
    }
    @TypeConverter
    fun JsontoListCurrency(value: List<Country.Currency>): String? {
        val gson = Gson()
        return gson.toJson(value)
    }

    @TypeConverter
    fun fromLanguagetoJson(value: String): List<Country.Language>? {
        val listType: Type =
                object : TypeToken<List<Country.Language>>() {}.type
        return Gson().fromJson<List<Country.Language>>(value, listType)
    }
    @TypeConverter
    fun JsontoListLanguage(value: List<Country.Language>): String? {
        val gson = Gson()
        return gson.toJson(value)
    }

    @TypeConverter
    fun fromImagetoString(value: String): ArrayList<Trip.Images>? {
        val listType: Type =
                object: TypeToken<ArrayList<Trip.Images>>() {}.type
        return 
    }
}
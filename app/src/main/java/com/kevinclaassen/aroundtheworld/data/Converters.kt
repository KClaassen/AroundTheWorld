package com.kevinclaassen.aroundtheworld.data

import androidx.room.TypeConverter
import com.kevinclaassen.aroundtheworld.models.Country
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
    fun fromArrayListToJson(value: String): ArrayList<String>? {
        val listType: Type =
                object: TypeToken<ArrayList<String>>() {}.type
        return Gson().fromJson<ArrayList<String>>(value, listType)
    }

    @TypeConverter
    fun JsontoArrayList(value: ArrayList<String>): String? {
        val gson = Gson()
        return gson.toJson(value)
    }
}
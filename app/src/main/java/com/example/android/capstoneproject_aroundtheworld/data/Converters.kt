package com.example.android.capstoneproject_aroundtheworld.data

import androidx.room.TypeConverter
import com.example.android.capstoneproject_aroundtheworld.models.Currency
import com.example.android.capstoneproject_aroundtheworld.models.Language

class Converters {

    companion object {

        @TypeConverter
        fun fromCurrency(currency: Currency): String? {
            return currency.name
        }

        @TypeConverter
        fun toCurrency(name: String): Currency {
            return Currency(name)
        }

        @TypeConverter
        fun fromLanguage(language: Language): String? {
            return language.name
        }

        @TypeConverter
        fun toLanguage(name: String): Language {
            return Language(name)
        }
    }
}
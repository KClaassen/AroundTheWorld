//package com.example.android.capstoneproject_aroundtheworld.data
//
//import androidx.room.Entity
//import androidx.room.PrimaryKey
//import androidx.room.TypeConverters
//import com.example.android.capstoneproject_aroundtheworld.models.Country
//
//
///**
// *  Countries
// */
//
//@Entity(tableName = "databasecountry")
//@TypeConverters(Converters::class)
//data class DatabaseCountry constructor (
//        @PrimaryKey
//        val name: String,
//        val flag: String,
//        val capital: String,
//        val currency: Currency,
//        val language: Language,
//        val region: String
//)
//
//data class Currency(
//        val name: String
//)
//
//data class Language(
//        val name: String
//)
//
//
//
//
//// Converts Database Objects to Domain Objects
//fun List<DatabaseCountry>.asDomainModel(): List<Country>{
//    return map {
//        Country(
//                name = it.name,
//                flag = it.flag,
//                capital = it.capital,
//                region = it.region,
//                currency = com.example.android.capstoneproject_aroundtheworld.models.Currency(it.currency.name),
//                language = com.example.android.capstoneproject_aroundtheworld.models.Language(it.language.name)
//        )
//    }
//}
//
//
//
///**
// *  Trips
// */
//
//@Entity(tableName = "trips_database")
//data class Trips constructor (
//        @PrimaryKey
//        val name: String,
//        val description: String,
//        val airline: String,
//        val bookingNumber: String,
//        val Dates: Int,
//)
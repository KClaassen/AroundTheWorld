package com.example.android.capstoneproject_aroundtheworld.models

import androidx.databinding.BaseObservable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class Trip(
        @PrimaryKey
        var name: String,
        var description: String,
        var airline: String,
        var bookingNr: String,
        var accomodation: String,
        var dateFrom: String,
        var dateTo: String) : BaseObservable()
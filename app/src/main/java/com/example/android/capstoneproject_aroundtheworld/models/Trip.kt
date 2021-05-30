package com.example.android.capstoneproject_aroundtheworld.models

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity()
data class Trip(
        @PrimaryKey
        var name: String,
        var description: String,
        var airline: String?,
        var bookingNr: String?,
        var accomodation: String?,
        var dateFrom: String,
        var dateTo: String
       // val image: String
        )
        : Parcelable
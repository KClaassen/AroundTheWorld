package com.kevinclaassen.aroundtheworld.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity()
data class Trip(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
       // @PrimaryKey
        var name: String,
        var description: String,
        var airline: String?,
        var bookingNr: String?,
        var accomodation: String?,
        var dateFrom: String,
        var dateTo: String,
        val images: ArrayList<String>
        ) : Parcelable
package com.example.android.capstoneproject_aroundtheworld.models

import androidx.databinding.BaseObservable

data class Trip(var name: String, var description: String, var airline: String, var bookingNr: String,
                var accomodation: String, var dates: Int) : BaseObservable()
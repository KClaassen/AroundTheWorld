package com.example.android.capstoneproject_aroundtheworld.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.android.capstoneproject_aroundtheworld.data.TripDatabase
import com.example.android.capstoneproject_aroundtheworld.models.Trip

class TripsRepository (
    private val database: TripDatabase
    ) {

    suspend fun saveTrip(trip: Trip) = database.tripDao.saveTrip(trip)

    fun deleteTrip(trip: Trip) = database.tripDao.deleteTrip(trip)

    fun getAllTrips() = database.tripDao.getAllTrips()
    
}
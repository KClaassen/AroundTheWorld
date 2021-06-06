package com.example.android.capstoneproject_aroundtheworld.repository

import com.example.android.capstoneproject_aroundtheworld.data.TripDatabase
import com.example.android.capstoneproject_aroundtheworld.models.Trip

class TripsRepository (
    private val database: TripDatabase
    ) {

    suspend fun saveTrip(trip: Trip) = database.tripDao.saveTrip(trip)

    fun deleteTrip(trip: Trip) = database.tripDao.deleteTrip(trip)

    fun getTripById(id: Int) = database.tripDao.getTripById(id)

    fun updateTripImages(trip: Trip) = database.tripDao.updateTripImages(trip)

    fun getAllTrips() = database.tripDao.getAllTrips()
    
}
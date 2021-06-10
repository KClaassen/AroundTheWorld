package com.kevinclaassen.aroundtheworld.repository

import com.kevinclaassen.aroundtheworld.data.TripDatabase
import com.kevinclaassen.aroundtheworld.models.Trip

class TripsRepository (
    private val database: TripDatabase
    ) {

    suspend fun saveTrip(trip: Trip) = database.tripDao.saveTrip(trip)

    fun deleteTrip(trip: Trip) = database.tripDao.deleteTrip(trip)

    fun getTripById(id: Int) = database.tripDao.getTripById(id)

    fun updateTripImages(trip: Trip) = database.tripDao.updateTripImages(trip)

    fun getAllTrips() = database.tripDao.getAllTrips()
    
}
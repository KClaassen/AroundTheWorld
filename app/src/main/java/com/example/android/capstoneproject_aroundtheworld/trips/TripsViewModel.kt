package com.example.android.capstoneproject_aroundtheworld.trips

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.android.capstoneproject_aroundtheworld.data.CountryDatabase
import com.example.android.capstoneproject_aroundtheworld.data.TripDatabase
import com.example.android.capstoneproject_aroundtheworld.models.Trip
import com.example.android.capstoneproject_aroundtheworld.repository.TripsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TripsViewModel(
    application: Application
): ViewModel() {

    private val database = TripDatabase.getDatabase(application)
    private val repository = TripsRepository(database)

    //Create Live Data Object
    private var _tripList = MutableLiveData<ArrayList<Trip>>()
    val tripList: LiveData<ArrayList<Trip>>
        get() = _tripList

    suspend fun onSaveClick(v: View, trip: Trip){
        withContext(Dispatchers.IO) {
            Log.i("savebutton", "${trip}")
            _tripList.value?.add(trip)
            v.findNavController().navigate(NewTripFragmentDirections.actionNewTripFragmentToTripDetailFragment())
            repository.saveTrip(trip)
        }
    }

    suspend fun deleteTrip(trip: Trip) {
        withContext(Dispatchers.IO) {
            repository.deleteTrip(trip)
        }
    }

    suspend fun getAllTrips() = repository.getAllTrips()

    init {
        _tripList.value = ArrayList()
    }



    // Factory for constructing TripsListViewModel with parameter
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TripsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TripsViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
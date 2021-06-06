package com.example.android.capstoneproject_aroundtheworld.trips

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import androidx.navigation.findNavController
import com.example.android.capstoneproject_aroundtheworld.data.TripDatabase
import com.example.android.capstoneproject_aroundtheworld.models.Trip
import com.example.android.capstoneproject_aroundtheworld.repository.TripsRepository
import kotlinx.coroutines.*

class TripsViewModel(
    application: Application
): ViewModel() {

    private val database = TripDatabase.getDatabase(application)
    private val repository = TripsRepository(database)

    /**
     *  Trip List
     */

    //Create Live Data Object
    private var _tripList = MutableLiveData<MutableList<Trip>>()
    val tripList: LiveData<MutableList<Trip>>
        get() = _tripList

    fun onSaveClick(v: View, trip: Trip){
        CoroutineScope(Dispatchers.Main).launch {
            Log.i("savebutton", "${trip}")
            _tripList.value?.add(trip)
            v.findNavController().navigate(NewTripFragmentDirections.actionNewTripFragmentToTripsListFragment())
            repository.saveTrip(trip)
        }
    }

    fun deleteTrip(trip: Trip){
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteTrip(trip)
        }
    }

    fun getAllTrips() = repository.getAllTrips()

    init {
        _tripList.value = ArrayList()
    }


    /**
     *  Navigate to Detail Screen
     */
    // To navigate and complete navigation for selected Country onclick

    private val _navigateToTrip = MutableLiveData<Trip>()
    val navigateToTrip
        get() = _navigateToTrip

    fun onTripClicked(id: Trip){
        _navigateToTrip.value = id
    }

    fun onTripNavigated() {
        _navigateToTrip.value = null
    }

    /**
     *  Image List
     */

    fun updateTripImages(trip: Trip){
        CoroutineScope(Dispatchers.IO).launch {
            repository.updateTripImages(trip)
        }
    }

    fun getTripById(id: Int) = repository.getTripById(id)

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
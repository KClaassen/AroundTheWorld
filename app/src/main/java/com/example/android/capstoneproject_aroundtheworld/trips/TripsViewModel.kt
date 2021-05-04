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
import com.example.android.capstoneproject_aroundtheworld.models.Trip
import com.example.android.capstoneproject_aroundtheworld.repository.TripsRepository

class TripsViewModel(
    application: Application
): ViewModel() {

//    private val database = TripDatabase.getDatabase(application)
//    private val repository = TripsRepository(database)

    //Create Live Data Object
    private var _tripList = MutableLiveData<ArrayList<Trip>>()
    val tripList: LiveData<ArrayList<Trip>>
        get() = _tripList

    fun onSaveClick(v: View, trip: Trip){
        Log.i("savebutton", "${trip}")
        _tripList.value?.add(trip)
        v.findNavController().navigate(NewTripFragmentDirections.actionNewTripFragmentToTripDetailFragment())
    }

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
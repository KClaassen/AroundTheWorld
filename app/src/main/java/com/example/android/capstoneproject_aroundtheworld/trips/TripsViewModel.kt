package com.example.android.capstoneproject_aroundtheworld.trips

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.example.android.capstoneproject_aroundtheworld.data.CountryDatabase
import com.example.android.capstoneproject_aroundtheworld.models.Trip
import com.example.android.capstoneproject_aroundtheworld.repository.CountriesRepository

class TripsViewModel(
    //application: Application
): ViewModel() {

//    private val database = CountryDatabase.getDatabase(application)
//    private val repository = CountriesRepository(database)

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
}
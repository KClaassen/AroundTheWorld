package com.example.android.capstoneproject_aroundtheworld.trips

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.example.android.capstoneproject_aroundtheworld.models.Trip

class TripsViewModel: ViewModel() {

    //Create Live Data Object
    private var _tripList = MutableLiveData<ArrayList<Trip>>()
    val tripList: LiveData<ArrayList<Trip>>
        get() = _tripList

    fun onSaveClick(v: View, trip: Trip){
        Log.i("savebutton", "save button called")
        _tripList.value?.add(trip)
        v.findNavController().navigate(NewTripFragmentDirections.actionNewTripFragmentToTripDetailFragment())

    }

    init {
        _tripList.value = ArrayList()
    }
}
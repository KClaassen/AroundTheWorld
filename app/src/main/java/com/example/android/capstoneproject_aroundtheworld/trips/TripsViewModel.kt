package com.example.android.capstoneproject_aroundtheworld.trips

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.capstoneproject_aroundtheworld.models.Trip

class TripsViewModel: ViewModel() {

    //Create Live Data Object
    private var _tripList = MutableLiveData<ArrayList<Trip>>()
    val tripList: LiveData<ArrayList<Trip>>
        get() = _tripList

    fun onSaveClick(v: View, trip: Trip){
        _tripList.value?.add(trip)

    }
}
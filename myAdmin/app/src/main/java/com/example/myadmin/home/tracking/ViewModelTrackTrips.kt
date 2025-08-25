package com.example.myadmin.home.tracking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myadmin.api.Repository
import com.example.myadmin.home.showtrips.availabletrips.AllTripsAvailbleResponse
import kotlinx.coroutines.launch

class ViewModelTrackTrips:ViewModel() {
    private val repository=Repository()
    private val _tripsTracking:MutableLiveData<AllTripsAvailbleResponse?> = MutableLiveData()
    val tripsTracking:LiveData<AllTripsAvailbleResponse?>
        get() = _tripsTracking
    fun getTrackTrips(token:String){
        viewModelScope.launch {
            _tripsTracking.postValue(repository.getTrackTrips(token))
        }
    }
}
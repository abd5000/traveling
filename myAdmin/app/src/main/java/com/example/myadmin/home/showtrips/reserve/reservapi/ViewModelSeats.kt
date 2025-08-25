package com.example.myadmin.home.showtrips.reserve.reservapi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myadmin.api.Repository

import com.example.myadmin.home.showtrips.reserve.completereserve.ResponseOneTrip
import kotlinx.coroutines.launch

class ViewModelSeats:ViewModel() {
    private val _trips: MutableLiveData<ResponseOneTrip?> = MutableLiveData()
    val trips: LiveData<ResponseOneTrip?>
        get() = _trips
    private val repository=Repository()
    fun getOneTrip(tripId: Int){
        viewModelScope.launch {
            _trips.postValue(repository.getOneTrip(tripId))
        }
    }
}
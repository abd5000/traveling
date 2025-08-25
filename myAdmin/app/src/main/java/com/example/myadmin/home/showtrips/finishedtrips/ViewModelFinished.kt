package com.example.myadmin.home.showtrips.finishedtrips

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myadmin.api.Repository
import com.example.myadmin.home.addtrip.ResponCity
import kotlinx.coroutines.launch

class ViewModelFinished:ViewModel() {
    private val _filterTrips:MutableLiveData<FinishedfFilterResponse?> = MutableLiveData()
    val filterTrips:LiveData<FinishedfFilterResponse?>
        get() = _filterTrips
    private val repository=Repository()
    fun filterByCity(token:String,idFrom:Int,idTo:Int){
        viewModelScope.launch {
            _filterTrips.postValue(repository.getFinishedTripsByCites(token,idFrom, idTo))
        }
    }
    private val _allTrips:MutableLiveData<FinishedfFilterResponse?> = MutableLiveData()
    val allTrips:LiveData<FinishedfFilterResponse?>
        get() = _allTrips
    fun getAllTripsFinished(token:String){
        viewModelScope.launch {
            _allTrips.postValue(repository.getAllTripsFinished(token))
        }
    }
    private val _cities:MutableLiveData<ResponCity?> = MutableLiveData()
    val cities:LiveData<ResponCity?>
        get() = _cities
    fun getCities(){
        viewModelScope.launch {
            _cities.postValue(repository.cites())
        }
    }
}
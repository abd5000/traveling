package com.example.myadmin.home.showtrips.availabletrips

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myadmin.api.Repository
import com.example.myadmin.home.addtrip.ResponCity
import kotlinx.coroutines.launch

class ViewModelAvailable:ViewModel() {
    private val repository= Repository()
    private val _allTrips:MutableLiveData<AllTripsAvailbleResponse?> = MutableLiveData()
    val allTrips:LiveData<AllTripsAvailbleResponse?>
        get() = _allTrips
    fun getAllTripsAvailable(token: String){
        viewModelScope.launch {
            _allTrips.postValue(repository.getAllTripsAvailable(token))
        }
    }
    //
    private val _busAndCites:MutableLiveData<AllTripsAvailbleResponse?> = MutableLiveData()
    val busAndCites:LiveData<AllTripsAvailbleResponse?>
        get() = _busAndCites
    fun filterBuBusAndCites(token:String,idFrom:Int,idTo:Int,numberBus:Int){
        viewModelScope.launch {
            _busAndCites.postValue(repository.filterByBusAndCites(token, idFrom, idTo, numberBus))
        }
    }
    //
    private val _cities:MutableLiveData<ResponCity?> = MutableLiveData()
    val cities:LiveData<ResponCity?>
        get() = _cities
    fun getCities(){
        viewModelScope.launch {
            _cities.postValue(repository.cites())
        }
    }
    //
    private val _tripsByCites:MutableLiveData<AllTripsAvailbleResponse?> = MutableLiveData()
    val tripsByCites:LiveData<AllTripsAvailbleResponse?>
        get() = _tripsByCites
    fun filterByCites(token:String,idFrom:Int,idTo:Int){
        viewModelScope.launch {
            _tripsByCites.postValue(repository.filterByCites(token, idFrom, idTo))
        }
    }
    //
    private val _tripsByBus:MutableLiveData<AllTripsAvailbleResponse?> = MutableLiveData()
    val tripsByBus:LiveData<AllTripsAvailbleResponse?>
        get() = _tripsByCites
    fun filterByBus(token:String,numberBus:Int){
        viewModelScope.launch {
            _tripsByCites.postValue(repository.filterTripsBus(token,numberBus))
        }
    }
//    private val _filterTrips: MutableLiveData<AllTripsAvailbleResponse?> = MutableLiveData()
//    val filterTrips: LiveData<AllTripsAvailbleResponse?>
//        get() = _filterTrips
//    fun filterCity(token:String,idFrom:Int,idTo:Int){
//        viewModelScope.launch {
//            _filterTrips.postValue(repository.getAvailableTrips(token, idFrom, idTo))
//        }
//    }
}
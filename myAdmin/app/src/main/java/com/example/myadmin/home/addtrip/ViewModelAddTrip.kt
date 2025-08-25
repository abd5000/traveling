package com.example.myadmin.home.addtrip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myadmin.api.Repository
import com.example.myadmin.home.addtrip.buss.ResponseGetBus
import com.example.myadmin.home.addtrip.duration.DurationResponse
import kotlinx.coroutines.launch

class ViewModelAddTrip:ViewModel() {
    private val _bus:MutableLiveData<ResponseGetBus?> = MutableLiveData()
    val bus:LiveData<ResponseGetBus?>
        get() = _bus
    private val _duration:MutableLiveData<DurationResponse?> = MutableLiveData()
    val duration:LiveData<DurationResponse?>
        get() = _duration
    private val _cities:MutableLiveData<ResponCity?> = MutableLiveData()
    val cities:LiveData<ResponCity?>
        get() = _cities
    private val _trip:MutableLiveData<ResponseAddTrip?> = MutableLiveData()
    val trip:LiveData<ResponseAddTrip?>
        get() = _trip
    private val repository=Repository()
    fun getBus(token:String,arrCity:String,depCity:String,date:String,time:String){
        viewModelScope.launch {
            _bus.postValue(repository.getBus(token, arrCity, depCity, date, time))
        }
    }
    fun getDuration(arrCity:String,depCity:String){
        viewModelScope.launch {
            _duration.postValue(repository.getDuration(arrCity,depCity))
        }
    }
    fun getCities(){
        viewModelScope.launch {
            _cities.postValue(repository.cites())
        }
    }
    fun addTrip(destination: Int, starting: Int, tripDate: String, tripTime: String, busid: Int,isPeriodic:Boolean ){
        viewModelScope.launch {
            _trip.postValue(repository.addTrip(destination, starting, tripDate, tripTime, busid,isPeriodic))
        }
    }
}
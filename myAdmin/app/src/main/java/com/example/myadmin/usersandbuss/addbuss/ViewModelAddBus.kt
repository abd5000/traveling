package com.example.myadmin.usersandbuss.addbuss

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myadmin.api.Repository
import com.example.myadmin.home.addtrip.ResponCity
import com.example.myadmin.usersandbuss.addbuss.availableDrivers.DraiversAvailableResponse
import com.example.myadmin.usersandbuss.addbuss.typBus.TypeBusResponse
import kotlinx.coroutines.launch

class ViewModelAddBus:ViewModel() {
    private val _bus:MutableLiveData<AddBusResponse?> = MutableLiveData()
    val bus:LiveData<AddBusResponse?>
        get() = _bus
    private val repository=Repository()
    fun addBus(token: String,busNumber: Int,type:String,place:String,driverId:Int){
        viewModelScope.launch {
            _bus.postValue(repository.addBus(token, busNumber, type, place,driverId))
        }
    }
    //
    private val _cities:MutableLiveData<ResponCity?> = MutableLiveData()
    val cities:LiveData<ResponCity?>
        get() = _cities
    fun getCities() {
        viewModelScope.launch {
            _cities.postValue(repository.cites())
        }
    }
    //
    private val _typeBus:MutableLiveData<TypeBusResponse?> = MutableLiveData()
    val typeBus:LiveData<TypeBusResponse?>
        get() = _typeBus
    fun getTypeBus(){
        viewModelScope.launch {
            _typeBus.postValue(repository.getTypeBus())
        }
    }
    //
    private val _driver:MutableLiveData<DraiversAvailableResponse?> = MutableLiveData()
    val driver:LiveData<DraiversAvailableResponse?>
        get() = _driver
    fun getAvailableDrivers(token: String){
        viewModelScope.launch {
            _driver.postValue(repository.getAvailableDrivers(token))
        }
    }
}
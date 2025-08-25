package com.example.myadmin.usersandbuss.deletebus.editeBus

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myadmin.api.Repository
import com.example.myadmin.usersandbuss.addbuss.availableDrivers.DraiversAvailableResponse
import kotlinx.coroutines.launch

class ViewModelEditeBus:ViewModel() {
    private val repository=Repository()
    private val _edite:MutableLiveData<EditeBusResponse?> = MutableLiveData()
    val edite:LiveData<EditeBusResponse?>
        get() =_edite
    fun upDateBus(busId:Int,driverId:Int){
        viewModelScope.launch {
            _edite.postValue(repository.upDateBus(busId, driverId))
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
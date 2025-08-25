package com.example.myadmin.usersandbuss.deleteDriver

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myadmin.api.Repository
import com.example.myadmin.usersandbuss.deleteDriver.deleteapi.DeleteDrivereResponse
import kotlinx.coroutines.launch

class ViewModelDeleteDriver:ViewModel() {
    private val _drivers:MutableLiveData<AllDriversResponse?> = MutableLiveData()
    val drivers:LiveData<AllDriversResponse?>
        get() = _drivers
    private val repository=Repository()
    fun getAllDrivers(token:String){
        viewModelScope.launch {
            _drivers.postValue(repository.getAllDrivers(token))
        }
    }
    //
    private val _delete:MutableLiveData<DeleteDrivereResponse?> = MutableLiveData()
    val delete:LiveData<DeleteDrivereResponse?>
        get() = _delete
    fun deleteDriver(driverId:Int){
        viewModelScope.launch {
            _delete.postValue(repository.deleteDriver(driverId))
        }
    }
}
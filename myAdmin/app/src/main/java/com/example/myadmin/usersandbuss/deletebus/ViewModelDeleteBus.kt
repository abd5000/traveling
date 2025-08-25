package com.example.myadmin.usersandbuss.deletebus

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myadmin.api.Repository
import com.example.myadmin.usersandbuss.deletebus.allbuss.AllBussResponse
import kotlinx.coroutines.launch

class ViewModelDeleteBus:ViewModel() {
    private val _delete:MutableLiveData<DeleteBusResponse?> = MutableLiveData()
    val delete:LiveData<DeleteBusResponse?>
        get() = _delete
    private val repository=Repository()
    fun deleteBus(busId:Int){
        viewModelScope.launch {
            _delete.postValue(repository.deleteBus(busId))
        }
    }
    //
    private val _allBuss:MutableLiveData<AllBussResponse?> = MutableLiveData()
    val allBuss:LiveData<AllBussResponse?>
        get() =_allBuss
    fun getAllBuss(token:String){
        viewModelScope.launch {
            _allBuss.postValue(repository.getAllBuss(token))
        }
    }
}
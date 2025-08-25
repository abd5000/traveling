package com.example.mytestnav.reserve

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytestnav.api.Repositry
import kotlinx.coroutines.launch

class ViewModelReserve:ViewModel() {
    private val _trip:MutableLiveData<ReserveResponse?> = MutableLiveData()
    val trip:LiveData<ReserveResponse?>
        get() = _trip
    private val repositry=Repositry()
    fun reserve(token:String,tripId:Int,steat:List<Int>){
        viewModelScope.launch {
            _trip.postValue(repositry.reserve(token,tripId,steat))
        }
    }
}
package com.example.mytestnav.mybook.notpaid.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytestnav.api.Repositry
import kotlinx.coroutines.launch

class ViewModelCancelReserve:ViewModel() {
    private val _trip:MutableLiveData<CancelResarevResponse?> = MutableLiveData()
    val trip:LiveData<CancelResarevResponse?>
        get() = _trip
    private val repositry=Repositry()
    fun cancelReserve(token:String,tripId:Int){
        viewModelScope.launch {
            _trip.postValue(repositry.cancelReserve(token,tripId))
        }
    }
}
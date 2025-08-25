package com.example.myadmin.home.showtrips.reserve.completereserve

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myadmin.api.Repository
import com.example.myadmin.home.showtrips.availabletrips.AllTripsAvailbleResponse

import com.example.myadmin.home.showtrips.reserve.reservapi.ReserveResponse
import kotlinx.coroutines.launch

class ViewModelReserveByAdmin:ViewModel() {
    private val _reserve: MutableLiveData<ReserveResponse?> = MutableLiveData()
    val reserve: LiveData<ReserveResponse?>
        get() = _reserve
    private val repository= Repository()
    fun reserveSeats(token: String, tripId: Int, name:String, seats: List<Int>){
        viewModelScope.launch {
            _reserve.postValue(repository.reserveSeats(token, tripId, seats,name))
        }
    }

}
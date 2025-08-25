package com.example.myadmin.home.showtrips.detailstrips

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myadmin.api.Repository
import kotlinx.coroutines.launch

class ViewModelDetails:ViewModel() {
    private val _details:MutableLiveData<CustomerTripAvailable?> = MutableLiveData()
    val details:LiveData<CustomerTripAvailable?>
        get() = _details
    private val repository=Repository()
    fun getCustomer(token:String,tripId:Int){
        viewModelScope.launch {
            _details.postValue(repository.getCustomerTrip(token,tripId))
        }
    }
    private val _filterCustomer:MutableLiveData<CustomerTripAvailable?> = MutableLiveData()
    val filterCustomer:LiveData<CustomerTripAvailable?>
        get()=_filterCustomer
    fun filterCustomerBuySeat(tripId: Int,seat:Int){
        viewModelScope.launch {
            _filterCustomer.postValue(repository.filterCustomerBuySeat(tripId, seat))
        }
    }
    //
    private val _confirm:MutableLiveData<ConfermResponse?> = MutableLiveData()
    val confirm:LiveData<ConfermResponse?>
        get()=_confirm
    fun confirmReserve(customerId:Int,tripId: Int){
        viewModelScope.launch {
            _confirm.postValue(repository.confirmReserve(customerId, tripId))
        }
    }
    //
    private val _cancel:MutableLiveData<ConfermResponse?> = MutableLiveData()
    val cancel:LiveData<ConfermResponse?>
        get()=_cancel
    fun cancelCustomerReserve(customerId:Int,tripId: Int){
        viewModelScope.launch {
            _cancel.postValue(repository.cancelCustomerReserve(customerId, tripId))
        }
    }
    private val _paid:MutableLiveData<CustomerTripAvailable?> = MutableLiveData()
    val paid:LiveData<CustomerTripAvailable?>
        get() = _paid
    fun getCustomerPaid(tripId: Int){
        viewModelScope.launch {
            _paid.postValue(repository.getCustomerPaid(tripId))
        }
    }
}

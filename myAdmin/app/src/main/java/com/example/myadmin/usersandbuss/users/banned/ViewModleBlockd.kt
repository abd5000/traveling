package com.example.myadmin.usersandbuss.users.banned

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myadmin.api.Repository
import com.example.myadmin.usersandbuss.users.banned.custoersBlocked.AllCustomersBlockedResponse
import kotlinx.coroutines.launch

class ViewModleBlockd:ViewModel() {
    private val _customers:MutableLiveData<AllCustomersBlockedResponse?> = MutableLiveData()
    val customers:LiveData<AllCustomersBlockedResponse?>
        get() = _customers
    private val response=Repository()
    fun getAllCustomersBlocked(token:String){
        viewModelScope.launch {
            _customers.postValue(response.getAllCustomersBlocked(token))
        }
    }
    private val _cancel:MutableLiveData<BannedResponse?> = MutableLiveData()
    val cancel:LiveData<BannedResponse?>
        get() = _cancel
    fun  cancelBlockCustomer(token: String,customerId:Int){
        viewModelScope.launch {
            _cancel.postValue(response.cancelBlockCustomer(token,customerId))
        }
    }
}
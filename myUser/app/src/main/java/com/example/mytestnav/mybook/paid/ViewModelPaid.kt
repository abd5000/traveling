package com.example.mytestnav.mybook.paid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytestnav.api.Repositry
import kotlinx.coroutines.launch

class ViewModelPaid:ViewModel() {
    private val _tripsPaid:MutableLiveData<PaidResponse?> = MutableLiveData()
    val tripsPaid:LiveData<PaidResponse?>
        get() = _tripsPaid

    private val repositry= Repositry()
    fun paid(token:String){
        viewModelScope.launch {
            _tripsPaid.postValue(repositry.paid(token))
        }
    }

}
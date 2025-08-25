package com.example.mytestnav.mybook.notpaid

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytestnav.api.Repositry
import com.example.mytestnav.mybook.paid.PaidResponse
import kotlinx.coroutines.launch

class ViewModelNotPaid:ViewModel() {
    private val _tripNotPaid: MutableLiveData<PaidResponse?> = MutableLiveData()
    val tripNotPaid: LiveData<PaidResponse?>
        get() = _tripNotPaid
    private val repositry= Repositry()
    fun notPaid(token:String){
        viewModelScope.launch {
            _tripNotPaid.postValue(repositry.notPaid(token))
            Log.e("paidresult", "${_tripNotPaid.value?.success} ", )
        }
    }

}
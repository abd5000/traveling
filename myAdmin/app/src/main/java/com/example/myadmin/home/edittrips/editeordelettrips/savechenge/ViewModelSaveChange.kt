package com.example.myadmin.home.edittrips.editeordelettrips.savechenge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myadmin.api.Repository
import kotlinx.coroutines.launch

class ViewModelSaveChange:ViewModel() {
    private val _change:MutableLiveData<SaveChengesResponse?> = MutableLiveData()
    val change:LiveData<SaveChengesResponse?>
        get() = _change
    private val repository=Repository()
    fun upDateTrip(date:String,time:String,tripId:Int){
        viewModelScope.launch {
            _change.postValue(repository.upDateTrip(date, time, tripId ))
        }
    }
}
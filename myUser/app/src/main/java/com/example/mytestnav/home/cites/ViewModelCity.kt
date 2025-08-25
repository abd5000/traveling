package com.example.mytestnav.home.cites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytestnav.api.Repositry
import kotlinx.coroutines.launch

class ViewModelCity:ViewModel() {
    private val _cities: MutableLiveData<ResponCity?> = MutableLiveData()
    val cities:LiveData<ResponCity?>
        get() = _cities

    private val repositry=Repositry()
    fun getCities(){
        viewModelScope.launch {
            _cities.postValue(repositry.cites())
        }
    }
}
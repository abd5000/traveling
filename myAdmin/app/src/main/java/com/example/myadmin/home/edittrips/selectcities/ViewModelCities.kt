package com.example.myadmin.home.edittrips.selectcities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myadmin.api.Repository
import com.example.myadmin.home.addtrip.ResponCity
import kotlinx.coroutines.launch

class ViewModelCities:ViewModel() {
    private val _cities: MutableLiveData<ResponCity?> = MutableLiveData()
    val cities: LiveData<ResponCity?>
        get() = _cities
    private val repository= Repository()
    fun getCities(){
        viewModelScope.launch {
            _cities.postValue(repository.cites())
        }
    }
}
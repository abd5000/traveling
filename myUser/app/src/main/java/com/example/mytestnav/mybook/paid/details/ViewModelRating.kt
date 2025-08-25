package com.example.mytestnav.mybook.paid.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytestnav.api.Repositry
import kotlinx.coroutines.launch

class ViewModelRating:ViewModel() {
    private val _rateTrip: MutableLiveData<RatingResponse?> = MutableLiveData()
    val rateTrip: LiveData<RatingResponse?>
        get() = _rateTrip

    private val repositry= Repositry()
    fun rating(token:String,tripId:Int,rate:Int){
        viewModelScope.launch {
            _rateTrip.postValue(repositry.rating(token,tripId, rate))
        }
    }
}
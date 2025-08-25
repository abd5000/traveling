package com.example.mytestnav.home.refreshtoken

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytestnav.api.Repositry
import kotlinx.coroutines.launch

class ViewModelToken:ViewModel() {
    private val _token :MutableLiveData<RefreshTokenResponse?> =MutableLiveData()
    val token: LiveData<RefreshTokenResponse?>
        get() = _token
    val repositry=Repositry()
    fun updateToken(refreshToken: String) {
        viewModelScope.launch {
        _token.postValue(repositry.refreshToken(refreshToken))
        }
    }
}
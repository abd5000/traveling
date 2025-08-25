package com.example.myadmin.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myadmin.api.Repository
import kotlinx.coroutines.launch

class ViewModelLogin:ViewModel(){

    private val _user: MutableLiveData<LoginResponse?> = MutableLiveData()
    var user: MutableLiveData<LoginResponse?> = _user
    val repostry=Repository()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val result= repostry.login(email, password)
            _user.postValue(result)

        }
    }
    fun deleteObserv(){
        user.postValue(null)
    }


}
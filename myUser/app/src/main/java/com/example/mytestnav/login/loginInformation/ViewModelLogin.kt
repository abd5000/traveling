package com.example.mytestnav.login.loginInformation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytestnav.api.Repositry
import kotlinx.coroutines.launch

class ViewModelLogin:ViewModel() {

    private val _user: MutableLiveData<ResoultLogin?> = MutableLiveData()
    var user: MutableLiveData<ResoultLogin?> = _user
    val repostry=Repositry()

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
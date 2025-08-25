package com.example.mydriver.Login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydriver.Api.Repository
import kotlinx.coroutines.launch

class ViewModelLogin:ViewModel() {
    private val _driver:MutableLiveData<LoginResponse?> = MutableLiveData()
    val diver:LiveData<LoginResponse?>
        get() = _driver
    private val repository=Repository()
    fun login(email:String,password:String){
        viewModelScope.launch {
            _driver.postValue(repository.login(email, password))
        }
    }

}
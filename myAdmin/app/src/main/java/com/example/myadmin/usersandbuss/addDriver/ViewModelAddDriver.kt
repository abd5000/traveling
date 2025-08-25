package com.example.myadmin.usersandbuss.addDriver

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myadmin.api.Repository
import kotlinx.coroutines.launch

class ViewModelAddDriver:ViewModel() {
    private val _driver:MutableLiveData<AddDriverResponse?> = MutableLiveData()
    val driver:LiveData<AddDriverResponse?>
        get() = _driver
    private val repository=Repository()
    fun addDriver(token: String, fullname: String, email: String, password: String,confirmpassword: String, phone: String){
        viewModelScope.launch {
            _driver.postValue(repository.addDriver(token, fullname, email, password, confirmpassword, phone))
        }
    }
}
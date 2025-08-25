package com.example.mytestnav.login.signupinfomation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytestnav.api.Repositry
import kotlinx.coroutines.launch

class ViewModelSignup :ViewModel() {
    private val _user: MutableLiveData<SignupResult?> = MutableLiveData()
    var user: MutableLiveData<SignupResult?> = _user
    val repositry= Repositry()
    fun signup(fullName:String,email: String?, password: String?,confirmpassword:String?,fcmToken:String) {
        viewModelScope.launch {
            val result= repositry.signup(fullName,email, password, confirmpassword,fcmToken)
            _user.postValue(result)

        }

    }
    fun deleteObserv(){
        user.postValue(null)
    }
}
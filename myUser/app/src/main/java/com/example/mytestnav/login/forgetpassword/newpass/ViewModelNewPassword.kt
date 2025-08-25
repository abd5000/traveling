package com.example.mytestnav.login.forgetpassword.newpass

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytestnav.api.Repositry
import kotlinx.coroutines.launch

class ViewModelNewPassword:ViewModel() {
    private val _password:MutableLiveData<NewPasswordResult?> = MutableLiveData()
    val password: MutableLiveData<NewPasswordResult?>
        get() = _password
    private val repositry= Repositry()
     fun newPassword(id:Int, passwordS: String, confirm:String){
         viewModelScope.launch {
             val result=repositry.newPassword(id,passwordS,confirm)
             _password.postValue(result)
         }
    }
    fun deleteObserv(){
        password.postValue(null)
    }
}
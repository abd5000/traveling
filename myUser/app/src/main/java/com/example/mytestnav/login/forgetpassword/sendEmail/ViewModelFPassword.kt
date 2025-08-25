package com.example.mytestnav.login.forgetpassword.sendEmail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytestnav.api.Repositry
import kotlinx.coroutines.launch

class ViewModelFPassword: ViewModel() {
    private val _email: MutableLiveData<ForgetEmailResult?> = MutableLiveData()
    val email: MutableLiveData<ForgetEmailResult?>
        get() = _email
   private val repostry = Repositry()


    fun sendEmail(eEmail: String) {

        viewModelScope.launch {
            val result = repostry.sendEmail(eEmail)
            _email.postValue(result)

        }


    }
    fun deleteObserv(){
        email.postValue(null)
    }






}
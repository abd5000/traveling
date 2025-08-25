package com.example.mytestnav.login.forgetpassword.codeconfirm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytestnav.api.Repositry
import kotlinx.coroutines.launch

class ViewModelCodePassword:ViewModel() {
    private val _code:MutableLiveData<ConfirmPasswordResylt?> = MutableLiveData()
    val code: MutableLiveData<ConfirmPasswordResylt?>
        get() = _code
    private val repositry=Repositry()
    fun confirmEmailForget(id:Int,code: String){
        viewModelScope.launch {
            val result=repositry.confirmEmailForget(id,code)

                _code.postValue(result)
        }
    }
    fun deleteObserv(){
        code.postValue(null)
    }
}
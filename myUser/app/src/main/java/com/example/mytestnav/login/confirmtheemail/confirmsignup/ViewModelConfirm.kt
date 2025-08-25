package com.example.mytestnav.login.confirmtheemail.confirmsignup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytestnav.api.Repositry
import kotlinx.coroutines.launch

class ViewModelConfirm:ViewModel() {
    private val _codeEmail: MutableLiveData<ConfirmResult?> = MutableLiveData()
    val codeEmail: MutableLiveData<ConfirmResult?>
        get()=_codeEmail
    val repostry= Repositry()

    fun confirmEmail(id:Int, code: String) {
        viewModelScope.launch {
            val result= repostry.confirmEmail(id,code)
            _codeEmail.postValue(result)

        }
    }
    fun deleteObserv(){
        codeEmail.postValue(null)
    }
}
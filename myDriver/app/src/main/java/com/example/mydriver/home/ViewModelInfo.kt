package com.example.mydriver.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydriver.Api.Repository
import kotlinx.coroutines.launch

class ViewModelInfo :ViewModel(){
    private val _info: MutableLiveData<DataResponse?> = MutableLiveData()
    val info: LiveData<DataResponse?>
        get() = _info
    private val repository= Repository()
    fun getInfo(token:String){
        viewModelScope.launch {
            _info.postValue(repository.getInfo(token))
        }
    }
}
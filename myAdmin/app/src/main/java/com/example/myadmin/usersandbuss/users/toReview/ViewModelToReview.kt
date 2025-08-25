package com.example.myadmin.usersandbuss.users.toReview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myadmin.api.Repository
import com.example.myadmin.usersandbuss.users.banned.BannedResponse
import kotlinx.coroutines.launch

class ViewModelToReview:ViewModel() {
    private val _users:MutableLiveData<AllUsersToReviewResponse?> = MutableLiveData()
    val users:LiveData<AllUsersToReviewResponse?>
        get() = _users
    private val repository=Repository()
    fun getAllUsersToReview(token:String){
        viewModelScope.launch {
            _users.postValue(repository.getAllUsersToReview(token))
        }
    }
    private val _blockUser:MutableLiveData<BannedResponse?> = MutableLiveData()
    val blockUser:LiveData<BannedResponse?>
        get() = _blockUser
    fun blockCustomer(token: String,customerId:Int){
        viewModelScope.launch {
            _blockUser.postValue(repository.blockCustomer(token, customerId))
        }
    }
}
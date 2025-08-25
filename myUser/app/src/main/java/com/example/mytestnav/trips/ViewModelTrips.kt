package com.example.mytestnav.trips

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytestnav.api.Repositry
import com.example.mytestnav.trips.companys.CompanysResponse
import com.example.mytestnav.trips.filter.typeTrips.TypeTrips
import kotlinx.coroutines.launch

class ViewModelTrips:ViewModel() {
    //للرحل
    private val _trips:MutableLiveData<SearchResponse?> = MutableLiveData()
    val trips:LiveData<SearchResponse?>
        get() = _trips
    //اسماء الشركات مع معرف كل شركة
    private val _company:MutableLiveData<CompanysResponse?> =MutableLiveData()
    val company:LiveData<CompanysResponse?>
        get() = _company
    //ناتج الفلترة حسب الشركة
    private val _companyFilter:MutableLiveData<SearchResponse?> = MutableLiveData()
    val companyFilter:LiveData<SearchResponse?>
        get() = _companyFilter
    //انواع الرحل مع معرف كل نوع
    private val _typeTrip:MutableLiveData<TypeTrips?> =MutableLiveData()
    val typeTrip:LiveData<TypeTrips?>
        get() = _typeTrip
    //نتائج الفلترة حسب نوع الرحلة
    private val _filterTypeTrip:MutableLiveData<SearchResponse?> = MutableLiveData()
    val filterTypeTrip:LiveData<SearchResponse?>
        get() = _filterTypeTrip
    private val repositry=Repositry()
    fun getCompanies(token:String){
        viewModelScope.launch {
            _company.postValue(repositry.getCompanies(token))
        }
    }
    fun getTrips(idFrom:Int,idTo:Int,date:String,token:String){
        viewModelScope.launch {
            _trips.postValue(repositry.getTrips(idFrom, idTo, date,token))
        }
    }
    fun companyFliter(idCompany:String,idCityFrom:Int,idCityTo:Int,date:String,token:String){
        viewModelScope.launch {
            _companyFilter.postValue(repositry.FilterCompany(idCompany,idCityFrom,idCityTo, date,token))
        }
    }
    fun getTypeTrips(){
        viewModelScope.launch {
            _typeTrip.postValue(repositry.typeTrips())
        }
    }
    fun typeTripFilter(idType:String,idCityFrom:Int,idCityTo:Int,date:String,token:String){
        viewModelScope.launch {
            _filterTypeTrip.postValue(repositry.FilterTypeTrips(idType, idCityFrom, idCityTo, date,token))
        }

    }
}
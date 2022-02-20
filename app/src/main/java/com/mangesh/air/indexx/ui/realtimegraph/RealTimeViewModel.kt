package com.mangesh.air.indexx.ui.realtimegraph

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.mangesh.air.indexx.data.AirIndexDatabase
import com.mangesh.air.indexx.data.enitity.AirIndex


class RealTimeViewModel (application: Application): AndroidViewModel(application){

    private val airIndexDatabase = AirIndexDatabase.getDataBase(application)

    private val airIndexDao = airIndexDatabase.getAirIndex()

    var cityName:String=""

    fun getCityAirIndex(cityName:String?):LiveData<AirIndex>{
       return airIndexDao.getAirCityIndex(cityName)
    }
}
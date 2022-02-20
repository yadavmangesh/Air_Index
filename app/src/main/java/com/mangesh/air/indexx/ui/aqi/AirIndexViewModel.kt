package com.mangesh.air.indexx.ui.aqi

import android.app.Application
import androidx.lifecycle.*
import com.mangesh.air.indexx.data.AirIndexDatabase
import com.mangesh.air.indexx.data.enitity.AirIndex
import com.mangesh.air.indexx.data.getAirIndexRange
import com.mangesh.air.indexx.data.getLastMessage

class AirIndexViewModel(application: Application) : AndroidViewModel(application) {

    private val airIndexDatabase = AirIndexDatabase.getDataBase(application)

    private val airIndexDao = airIndexDatabase.getAirIndex()

    /**
     *  transform the live data to add the air quality see [com.mangesh.air.indexx.data.AirQuality]
     */
    fun getAirIndexList(): LiveData<List<AirIndex>> {
        return airIndexDao.getAirIndex().map {
            it.also {
                it.map {
                    airIndex -> airIndex.lastMessage = getLastMessage(airIndex.updatedAt)
                }
            }
        }
    }

}
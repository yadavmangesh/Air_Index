package com.mangesh.air.indexx.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mangesh.air.indexx.data.enitity.AirIndex

@Dao
interface AirIndexDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<AirIndex>)

    @Query("SELECT * , Max(updatedAt) FROM AirIndex GROUP BY cityName")
    fun getAirIndex():LiveData<List<AirIndex>>

    @Query("SELECT * , Max(updatedAt) FROM AirIndex WHERE cityName=:cityName")
    fun getAirCityIndex(cityName:String?):LiveData<AirIndex>
}
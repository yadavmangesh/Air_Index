package com.mangesh.air.indexx.data.enitity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
class AirIndex {

    @PrimaryKey(autoGenerate = true)
    var id :Int = 0

    @SerializedName("city")
    var cityName:String = ""

    @SerializedName("aqi")
    var currentAQI:Float = 0.0f

    var updatedAt:Long = 0

    /**
     *   to show air quality see [com.mangesh.air.indexx.data.AirQuality]
     */
    var airQuality:String = ""

    @Ignore
    var lastMessage:String = ""
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AirIndex

        if (id != other.id) return false
        if (cityName != other.cityName) return false
        if (currentAQI != other.currentAQI) return false
        if (updatedAt != other.updatedAt) return false
        if (airQuality != other.airQuality) return false
        if (lastMessage != other.lastMessage) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + cityName.hashCode()
        result = 31 * result + currentAQI.hashCode()
        result = 31 * result + updatedAt.hashCode()
        result = 31 * result + airQuality.hashCode()
        result = 31 * result + lastMessage.hashCode()
        return result
    }

}
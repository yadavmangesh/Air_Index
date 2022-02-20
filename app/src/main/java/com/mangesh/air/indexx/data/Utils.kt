package com.mangesh.air.indexx.data

import com.jjoe64.graphview.series.DataPoint
import com.mangesh.air.indexx.R
import com.mangesh.air.indexx.data.enitity.AirIndex
import java.util.*
import kotlin.math.pow
import kotlin.math.roundToInt

fun Float.roundTo(numFractionDigits: Int): Float {
    val factor = 10.0f.pow(numFractionDigits.toFloat())
    return (this * factor).roundToInt() / factor
}

fun getAirQualityColor(airQuality: String): Int {
    return when (airQuality) {
        AirQuality.GOOD -> R.color.good
        AirQuality.SATISFACTORY -> R.color.satisfactory
        AirQuality.MODERATE -> R.color.moderate
        AirQuality.POOR -> R.color.poor
        AirQuality.VERY_POOR -> R.color.very_poor
        AirQuality.SEVERE -> R.color.severe
        else -> R.color.severe
    }
}

fun airIndexToValueChart(count: Int, airIndex: AirIndex): DataPoint {
    return DataPoint(count.toDouble(), airIndex.currentAQI.toDouble())
}


fun getLastMessage(lastUpdated: Long): String {
    val current = System.currentTimeMillis()
    val diff = (current - lastUpdated) / 1000
    return when {
        diff < 60 -> {
            "updated few second ago"
        }
        diff in 60..(60 * 2) -> {
            "updated minute ago"
        }
        else -> {
            val date = Date(lastUpdated)
            "" + date.time
        }
    }
}


fun getAirIndexRange(aqiIndex: Float): String {

    return if (aqiIndex >= 0) {
        when (aqiIndex) {
            in 0f..50f -> AirQuality.GOOD
            in 51f..100f -> AirQuality.SATISFACTORY
            in 100.01f..200f -> AirQuality.MODERATE
            in 200.01f..300f -> AirQuality.POOR
            in 300.01f..400f -> AirQuality.VERY_POOR
            in 400.01f..500f -> AirQuality.SEVERE
            else -> AirQuality.SEVERE
        }
    } else {
        AirQuality.GOOD
    }
}
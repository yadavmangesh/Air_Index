package com.mangesh.air.indexx

import com.mangesh.air.indexx.data.AirQuality
import com.mangesh.air.indexx.data.getAirIndexRange
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class AirQualityCategoryTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test_getAirQualityColor(){
        val map = mutableMapOf<Float,String>()
        map[0.1f] = AirQuality.GOOD
        map[-0.1f] = AirQuality.GOOD
        map[0.000000f] = AirQuality.GOOD
        map[300.01f] = AirQuality.VERY_POOR
        map[1012.1f] = AirQuality.SEVERE
        map[00200.00f] = AirQuality.MODERATE
        for ((key,value ) in map){
            assertEquals(value, getAirIndexRange(key))
        }
    }
}
package com.mangesh.air.indexx.data.socket

import com.mangesh.air.indexx.data.enitity.AirIndex
import okhttp3.Response

sealed interface SocketConnectionListener{
    fun onConnectionSuccess(response:Response)
    fun onConnectionFailure(throwable: Throwable,response: Response?)
    fun onConnectionClose(code: Int, reason: String)
    fun onMessage(message: List<AirIndex>)
}
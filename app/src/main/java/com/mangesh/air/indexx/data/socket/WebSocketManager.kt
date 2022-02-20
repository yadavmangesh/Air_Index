package com.mangesh.air.indexx.data.socket

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.google.gson.Gson
import com.mangesh.air.indexx.data.AirIndexDatabase
import com.mangesh.air.indexx.data.enitity.AirIndex
import com.mangesh.air.indexx.data.getAirIndexRange
import com.mangesh.air.indexx.data.socket.SocketConnectionListener
import okhttp3.*
import okio.ByteString
import java.lang.IllegalArgumentException
import java.util.concurrent.TimeUnit

private const val TAG = "WebSocketManager"

object WebSocketManager {

    private const val MAX_NUM = 5
    private const val MILLIS = 5000
    private lateinit var client: OkHttpClient
    private lateinit var request: Request
    private var mWebSocket: WebSocket? = null
    var isConnect = false
    private var connectionAttempt = 0
    private var socketConnectionListener: SocketConnectionListener? = null
    private lateinit var airIndexDatabase: AirIndexDatabase

    // changed  the url from ws to wss to avoid the CLEARTEXT communication error
    // other solution would have been adding the  android:usesCleartextTraffic="true" in manifest
    fun init(context: Context) {
        airIndexDatabase = AirIndexDatabase.getDataBase(context)
        client = OkHttpClient.Builder()
            .writeTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()
        request = Request.Builder().url("wss://city-ws.herokuapp.com/").build()
    }

    fun connect() {
        if (isConnect) {
            Log.d(TAG, "connect: web socket connected")
            return
        }
        client.newWebSocket(request, getWebSocketListener())
    }

    fun reconnect() {
        if (connectionAttempt <= MAX_NUM) {
            try {
                Log.d(TAG, "reconnect: ")
                Thread.sleep(MILLIS.toLong())
                connect()
                connectionAttempt++
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        } else {
            Log.d(TAG, "reconnection failed $MAX_NUM")
        }
    }

    fun close() {
        try {
            mWebSocket?.close(1001, "Activity not visible to user")
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
    }

    fun cancel(){
        mWebSocket?.cancel()
    }

    fun setWebSocketListener(socketConnectionListener: SocketConnectionListener?) {
        this.socketConnectionListener = socketConnectionListener
    }


    private fun getWebSocketListener(): WebSocketListener {

        return object : WebSocketListener() {
            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                Log.d(TAG, "onClosed: code $code")
                Log.d(TAG, "onClosed: reason $reason")
                mWebSocket = webSocket
                isConnect = false
                socketConnectionListener?.onConnectionClose(code, reason)
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosing(webSocket, code, reason)
                Log.d(TAG, "onClosing: code $code")
                Log.d(TAG, "onClosing: reason $reason")
                isConnect = false
                mWebSocket = webSocket
                socketConnectionListener?.onConnectionClose(code, reason)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                Log.d(TAG, "onFailure response : $response")
                Log.d(TAG, "onFailure Throwable : $t")
                mWebSocket = webSocket
                isConnect = false
                socketConnectionListener?.onConnectionFailure(t, response)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                Log.d(TAG, "onMessage: $text")
                var aqiList = Gson().fromJson(text, Array<AirIndex>::class.java).toList()
                aqiList.also {
                    it.map { airIndex ->
                        airIndex.apply {
                            airIndex.updatedAt = java.lang.System.currentTimeMillis()
                            airIndex.airQuality = getAirIndexRange(airIndex.currentAQI)
                        }
                    }
                }
                airIndexDatabase.getAirIndex().insert(aqiList)
                mWebSocket = webSocket
                socketConnectionListener?.onMessage(aqiList)
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                super.onMessage(webSocket, bytes)
            }

            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                Log.d(TAG, "onOpen: response $response")
                mWebSocket = webSocket
                isConnect = response.code == 101
                if (isConnect) socketConnectionListener?.onConnectionSuccess(response)
                else reconnect()

            }

        }
    }
}
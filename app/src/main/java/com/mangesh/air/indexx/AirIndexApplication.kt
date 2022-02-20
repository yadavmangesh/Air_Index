package com.mangesh.air.indexx

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import com.mangesh.air.indexx.data.socket.WebSocketManager

private const val TAG = "AirIndexApplication"
class AirIndexApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(callbacks)
    }


    private val callbacks = object : ActivityLifecycleCallbacks {
        override fun onActivityCreated(p0: Activity, p1: Bundle?) {
            Log.d(TAG, "onActivityCreated: ${p0.componentName}")
            WebSocketManager.init(this@AirIndexApplication)
            WebSocketManager.connect()
        }

        override fun onActivityStarted(p0: Activity) {
            Log.d(TAG, "onActivityStarted: ${p0.componentName}")

        }

        override fun onActivityResumed(p0: Activity) {
            Log.d(TAG, "onActivityResumed: ${p0.componentName}")
            WebSocketManager.connect()
        }

        override fun onActivityPaused(p0: Activity) {
            Log.d(TAG, "onActivityPaused: ${p0.componentName}")
            WebSocketManager.close()
        }

        override fun onActivityStopped(p0: Activity) {
            Log.d(TAG, "onActivityStopped: ${p0.componentName}")
            WebSocketManager.close()
        }

        override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
            Log.d(TAG, "onActivitySaveInstanceState: ${p0.componentName}")
        }

        override fun onActivityDestroyed(p0: Activity) {
            Log.d(TAG, "onActivityDestroyed: ${p0.componentName}")
            WebSocketManager.close()
        }

    }
}
package com.niraj.internetobserverdemo

import android.app.Application
import com.nandroidex.internetobserve.NetworkConnectivityObserver

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        NetworkConnectivityObserver.init(this)
    }
}
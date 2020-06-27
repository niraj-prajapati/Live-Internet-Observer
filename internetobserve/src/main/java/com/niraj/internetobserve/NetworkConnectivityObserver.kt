package com.niraj.internetobserve

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

object NetworkConnectivityObserver {

    private const val SOCKET_HOST_URL = "https://www.google.com"
    private const val CONNECTION_TIME_OUT = 2000
    private const val READ_TIME_OUT = 8000
    private const val REQUEST_METHOD_GET = "GET"

    var internetAvailable: MutableLiveData<Boolean> = MutableLiveData()
        private set

    /**
     * @param app Application context
     */
    fun init(app: Application) {
        internetAvailable.value =
            isNetworkConnected(
                app
            )
        app.registerReceiver(
            NetworkChangeReceiver(),
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    private class NetworkChangeReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (TextUtils.equals(ConnectivityManager.CONNECTIVITY_ACTION, intent.action)) {
                checkInternetConnectivityNow(
                    context
                )
            }
        }
    }

    /**
     * @param context Context
     * @return true if Network is available on device, otherwise false.
     */
    private fun isNetworkConnected(context: Context): Boolean {
        val cm: ConnectivityManager? =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm?.activeNetworkInfo != null
    }

    /**
     * Forcefully checking internet connectivity
     */
    private fun checkInternetConnectivityNow(context: Context) {
        if (isNetworkConnected(
                context
            )
        ) {
            checkHostAvailable()
        } else {
            internetAvailable.value = false
        }
    }

    private fun checkHostAvailable() {
        GlobalScope.launch(Dispatchers.IO) {
            var connection: HttpURLConnection? = null
            try {
                val siteURL = URL(SOCKET_HOST_URL)
                connection = siteURL.openConnection() as HttpURLConnection
                connection.requestMethod =
                    REQUEST_METHOD_GET
                connection.connectTimeout =
                    CONNECTION_TIME_OUT
                connection.readTimeout =
                    READ_TIME_OUT
                connection.connect()
                val code = connection.responseCode
                if (code in 200..299) {
                    internetAvailable.postValue(true)
                } else {
                    internetAvailable.postValue(false)
                }
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: Exception) {
                internetAvailable.postValue(false)
            } finally {
                connection?.disconnect()
            }
        }
    }

    fun checkInternetConnectivityManually(con: Context) {
        checkInternetConnectivityNow(
            con
        )
    }
}
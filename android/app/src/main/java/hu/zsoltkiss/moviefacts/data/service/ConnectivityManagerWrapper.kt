package hu.zsoltkiss.moviefacts.data.service

import android.net.ConnectivityManager
import androidx.core.content.ContextCompat.getSystemService
import hu.zsoltkiss.moviefacts.app.TheApp

class ConnectivityManagerWrapper private constructor() {

    private var connectivityManager: ConnectivityManager =
        getSystemService(TheApp.getAppContext(), ConnectivityManager::class.java) as ConnectivityManager

    private var registredCallback: ConnectivityManager.NetworkCallback? = null

    companion object {
        @Volatile private var instance: ConnectivityManagerWrapper? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: ConnectivityManagerWrapper().also { instance = it }
        }
    }

    fun subscribe(callback: ConnectivityManager.NetworkCallback) {
        connectivityManager.registerDefaultNetworkCallback(callback)
        registredCallback = callback;
    }

    fun unsubscribe() {
        registredCallback?.let {
            connectivityManager.unregisterNetworkCallback(it)
        }

    }
}
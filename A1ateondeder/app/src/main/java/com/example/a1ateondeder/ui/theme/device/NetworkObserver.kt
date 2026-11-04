package com.example.a1ateondeder.ui.theme.device

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

class NetworkObserver(private val context: Context) {

    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val isConnectedWithInternet: Flow<Boolean> = callbackFlow {
        val callback = object : ConnectivityManager.NetworkCallback(){
            override fun onLost(network: Network) {
                super.onLost(network)
                trySend(isValidated())
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                super.onCapabilitiesChanged(network, networkCapabilities)
                trySend(isValidated())
            }

            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                trySend(isValidated())
            }
        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(request,callback)
        trySend(isValidated())

        awaitClose { connectivityManager.unregisterNetworkCallback(callback) }

    }.distinctUntilChanged()

    fun isValidated(): Boolean{
        val network = connectivityManager.activeNetwork
        val caps = connectivityManager.getNetworkCapabilities(network) ?: return false

        return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)

    }
}
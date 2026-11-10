package com.example.tudo.ui.device

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.telecom.ConnectionRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

class NetworkObserver(private val context: Context) {
    val connectionManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val isConnected: Flow<Boolean> = callbackFlow {
        val callback = object : ConnectivityManager.NetworkCallback(){
            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                trySend(isInternetValidated())
            }

            override fun onAvailable(network: Network) {
                trySend(isInternetValidated())
            }

            override fun onLost(network: Network) {
                trySend(isInternetValidated())
            }
        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        trySend(isInternetValidated())

        connectionManager.registerNetworkCallback(request,callback)

        awaitClose { connectionManager.unregisterNetworkCallback(callback) }
    }.distinctUntilChanged()

    private fun isInternetValidated(): Boolean {
        val network = connectionManager.activeNetwork
        val caps = connectionManager.getNetworkCapabilities(network) ?: return false

        return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)

    }
}
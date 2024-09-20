package com.example.appMeals.ui.view_model

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
/**
 * ViewModel responsible for managing connectivity-related functionality, such as checking if the device is connected to a Wi-Fi network.
 */

class ConnectivityViewModel : ViewModel() {


    object NetworkManager {

        @RequiresApi(Build.VERSION_CODES.M)
        fun isWifiConnected(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val network = connectivityManager.activeNetwork ?: return false
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        }

    }
}

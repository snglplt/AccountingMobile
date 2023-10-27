package com.selpar.selparbulut.network

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by Dev  on 10/03/2020.
 */
object NetworkManager {
    fun isConnectToInternet(context: Context?): Boolean {
        var isConnect = false
        if (context != null) {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            if (activeNetwork != null) { // connected to the internet
                isConnect = if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
                    true
                } else if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) {
                    // connected to the mobile app data plan
                    true
                } else {
                    return isConnect
                }
            }
        }
        return isConnect
    }
}
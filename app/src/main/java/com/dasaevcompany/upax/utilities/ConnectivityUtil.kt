package com.dasaevcompany.upax.utilities

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.Settings
import com.dasaevcompany.upax.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ConnectivityUtil {

    fun internet(context: Context):Boolean{
        val internet = isNetworkAvailable(context)
        if (!internet){
            connectionAlertNetwork(context)
        }
        return internet
    }

    private fun isNetworkAvailable(context: Context): Boolean  {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw  = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            return connectivityManager.activeNetworkInfo?.isConnected ?: false
        }
    }

    private fun connectionAlertNetwork(context: Context){
        MaterialAlertDialogBuilder(context)
            .setIcon(R.drawable.ic_network)
            .setTitle(context.getString(R.string.alert_network_title))
            .setMessage(context.getString(R.string.alert_network_message))
            .setPositiveButton(context.getString(R.string.alert_network_connect)) { _, _ ->
                val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
                context.startActivity(intent)
            }.setNegativeButton(context.getString(R.string.alert_network_cancel),null)
            .show().setCancelable(false)
    }
}
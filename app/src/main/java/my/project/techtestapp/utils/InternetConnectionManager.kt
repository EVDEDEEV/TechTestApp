package my.project.techtestapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.provider.Settings
import my.project.techtestapp.app.Application
import javax.inject.Inject

class InternetConnectionManager @Inject constructor(private val application: Application){

    fun isHasInternetConnection(): Boolean {
        val connectivityManager = application.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activityNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities =
            connectivityManager.getNetworkCapabilities(activityNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    fun isAirplaneModeOn(): Boolean {
        if (Settings.System.getInt(application.contentResolver,
                Settings.Global.AIRPLANE_MODE_ON,
                0) == 1
        )
            return true
        return false
    }
}
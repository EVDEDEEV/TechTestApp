package my.project.techtestapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import my.project.techtestapp.InternetConnectionManager

abstract class BaseViewModel(
    private val internetConnectionManager: InternetConnectionManager,
    application: Application,
) : AndroidViewModel(application) {

    fun isHasInternetConnection(): Boolean =
        internetConnectionManager.isHasInternetConnection()

    fun isAirplaneModeOn(): Boolean =
        internetConnectionManager.isAirplaneModeOn()
}
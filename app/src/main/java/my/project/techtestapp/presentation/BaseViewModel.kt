package my.project.techtestapp.presentation

import androidx.lifecycle.ViewModel
import my.project.techtestapp.InternetConnectionManager

abstract class BaseViewModel(private val internetConnectionManager: InternetConnectionManager) :
    ViewModel() {

    fun isHasInternetConnection(): Boolean =
        internetConnectionManager.isHasInternetConnection()

    fun isAirplaneModeOn(): Boolean =
        internetConnectionManager.isAirplaneModeOn()
}
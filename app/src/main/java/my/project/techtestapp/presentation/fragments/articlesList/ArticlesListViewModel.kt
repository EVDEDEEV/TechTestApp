package my.project.techtestapp.presentation.fragments.articlesList

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.provider.Settings
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import my.project.techtestapp.app.Application
import my.project.techtestapp.data.repository.ArticlesListRepository
import my.project.techtestapp.data.worker.ScheduledArticlesRefresh
import my.project.techtestapp.presentation.models.ArticlesUiModel
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ArticlesListViewModel @Inject constructor(
    private val articlesListRepository: ArticlesListRepository,
    private val application: Application,
) : ViewModel() {

    private val _listArticles: MutableStateFlow<List<ArticlesUiModel>> = MutableStateFlow(listOf())
    val listArticles: StateFlow<List<ArticlesUiModel>> = _listArticles.asStateFlow()

    private val trigger = MutableLiveData(true)

    fun trigger() {
        viewModelScope.launch {
            trigger.asFlow().flatMapLatest {
                articlesListRepository.getArticlesFromApi()
            }
                .collect {
                    _listArticles.value = it
                }
        }
    }

    fun refresh() {
        val value = trigger.value
        trigger.value = (!value!!)
    }

    fun deleteFromTab() {
        viewModelScope.launch(Dispatchers.IO) {
            articlesListRepository.deleteFromDb()
        }
    }

    fun refreshArticlesInBackground() {
        viewModelScope.launch {
            val request = PeriodicWorkRequest
                .Builder(ScheduledArticlesRefresh::class.java, 12, TimeUnit.HOURS)
                .build()
            WorkManager.getInstance(application).enqueue(request)
        }
    }

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



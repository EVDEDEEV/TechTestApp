package my.project.techtestapp.presentation.fragments.articlesList

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.provider.Settings
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import my.project.techtestapp.app.Application
import my.project.techtestapp.data.repository.ArticlesListRepository
import my.project.techtestapp.data.worker.ScheduledArticlesRefresh
import my.project.techtestapp.presentation.models.ArticlesListUiModel
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltViewModel
class ArticlesListViewModel @Inject constructor(
    private val articlesListRepository: ArticlesListRepository,
    private val application: Application,
) : ViewModel() {

    private val _listArticles = MutableLiveData<List<ArticlesListUiModel>>()
    val listArticles: LiveData<List<ArticlesListUiModel>> = _listArticles


    fun loadArticles() {
        viewModelScope.launch {
            val result = articlesListRepository.loadArticlesListFromApi()
            _listArticles.postValue(result)
        }
    }

    fun deleteFromTab() {
        viewModelScope.launch(Dispatchers.IO) {
            articlesListRepository.deleteFromDb()
        }
    }

    fun refreshArticlesInBackground() {
        viewModelScope.launch {
            val request = PeriodicWorkRequest
                .Builder(ScheduledArticlesRefresh::class.java, 30, TimeUnit.MINUTES)
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



package my.project.techtestapp.presentation.fragments.articlesList

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import my.project.techtestapp.InternetConnectionManager
import my.project.techtestapp.R
import my.project.techtestapp.app.Application
import my.project.techtestapp.data.repository.ArticlesListRepository
import my.project.techtestapp.data.worker.ScheduledArticlesRefresh
import my.project.techtestapp.presentation.BaseViewModel
import my.project.techtestapp.presentation.models.ArticlesListUiModel
import my.project.techtestapp.utils.ArticlesState
import my.project.techtestapp.utils.mapFromArticlesEntityToUiModel
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class ArticlesListViewModel @Inject constructor(
    private val articlesListRepository: ArticlesListRepository,
    internetConnectionManager: InternetConnectionManager,
    application: Application
) : BaseViewModel(internetConnectionManager, application) {

    private val _articlesState = MutableStateFlow<ArticlesState>(ArticlesState.Loading)
    val articlesState: StateFlow<ArticlesState> = _articlesState

    private val _listArticles = MutableLiveData<List<ArticlesListUiModel>>()
    val listArticles: MutableLiveData<List<ArticlesListUiModel>> = _listArticles

    init {
        isHasInternetConnection()
        isAirplaneModeOn()
        Log.d("Articles Worker", " ArticlesListViewModelCreated")
        refreshArticlesInBackground()
        loadArticles()
    }

    fun loadArticles() = viewModelScope.launch {
        val result = articlesListRepository.loadArticlesListFromApi()
        val cache = articlesListRepository.getCachedArticles().mapFromArticlesEntityToUiModel()
        try {
            if (cache.isNotEmpty()) {
                _listArticles.postValue(cache)
                _articlesState.value = ArticlesState.Data
            } else {
                _listArticles.postValue(result)
                _articlesState.value = ArticlesState.Data
            }
        } catch (e: Exception) {
            _articlesState.value = ArticlesState.Error(getApplication<Application>().getString(R.string.error_answer))
            Log.e("Articles List Exception", "$e")
        }
    }

    fun deleteFromTab() {
        viewModelScope.launch(Dispatchers.IO) {
            articlesListRepository.deleteFromDb()
        }
    }

    private fun refreshArticlesInBackground() {
        articlesListRepository.refreshArticlesInBackground()
    }
}



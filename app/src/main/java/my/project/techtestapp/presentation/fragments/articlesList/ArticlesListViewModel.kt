package my.project.techtestapp.presentation.fragments.articlesList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import my.project.techtestapp.data.models.database.articles.ArticlesEntity
import my.project.techtestapp.data.repository.MainRepository
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ArticlesListViewModel @Inject constructor(
    private val repository: MainRepository,
) : ViewModel() {

    private val _listArticles: MutableStateFlow<List<ArticlesEntity>> = MutableStateFlow(listOf())
    val listArticles: StateFlow<List<ArticlesEntity>> = _listArticles.asStateFlow()

    private val trigger = MutableLiveData<Boolean>(true)

        init {
        viewModelScope.launch {
            trigger.asFlow().flatMapLatest {
                repository.getArticlesFromApi()
            }
                .collect {
                    _listArticles.value = it
                }
        }
    }

    fun initLoad() {
        viewModelScope.launch {
            trigger.asFlow().flatMapLatest {
                repository.getArticlesFromApi()
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
            repository.deleteFromDb()
        }
    }
}
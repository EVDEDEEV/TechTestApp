package my.project.techtestapp.presentation

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import my.project.techtestapp.data.models.database.articles.ArticlesEntity
import my.project.techtestapp.data.models.database.authentication.AuthenticationEntity
import my.project.techtestapp.data.repository.AuthRepository

import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AuthRepository,
) : ViewModel() {

    init {
        Log.d("MVM", this.toString())
    }

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

    fun refresh() {
        val value = trigger.value
        trigger.value = (!value!!)
    }


    private var _authResponse = MutableLiveData<Boolean?>()
    val authResponse: LiveData<Boolean?> = _authResponse


    fun loginFromApi(phone: String, password: String) {
        viewModelScope.launch {
            try {
                val result = repository.loginFromApi(phone, password)
                _authResponse.postValue(result)
            } catch (e: Exception) {
                _authResponse.postValue(false)
                Log.e("Login Exception", "$e")
            }
        }
    }

//    fun checkLogin(phone: String, password: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//           val resp =  repository.loginFromApi(phone, password)
//            authResponse.postValue(resp)
//        }
//    }


//    fun getArticles() {
//
//    }

//    suspend fun getArticlesFromApi() {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.getArticlesFromApi()
//        }
//    }
//
//    fun getArticles() {
//        viewModelScope.launch {
//            val articles = repository.getArticlesFromDb()
//            listArticles.postValue(articles)
//        }
//    }

    fun deleteFromTab() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFromDb()
        }
    }


//    fun getArticles() {
//        viewModelScope.launch(Dispatchers.IO) {
//            val articles = repository.getArticlesFromDb()
//            listArticles.postValue(articles)
//        }
//    }
}
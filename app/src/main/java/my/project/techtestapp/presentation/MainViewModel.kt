package my.project.techtestapp.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import my.project.techtestapp.data.models.remote.authentication.AuthResponse
import my.project.techtestapp.data.models.remote.articles.ArticlesResponse
import my.project.techtestapp.data.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AuthRepository,
) : ViewModel() {

    var listArticles = MutableLiveData<ArticlesResponse?>()

    //    var authResponse = MutableLiveData<Int?>()
//    var authResponse = MutableLiveData<Response<AuthResponse>?>()
//    var authResponse = MutableLiveData<AuthResponse?>()
    var authResponse = MutableLiveData<Boolean?>(false)
//    var authResponse = MutableLiveData<String?>()

    fun getAuth(phone: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.authFuncInRepository(phone, password)
            authResponse.postValue(result)
        }
    }

    fun getArticles() {
        viewModelScope.launch(Dispatchers.IO) {
            val articles = repository.getArticles()
            listArticles.postValue(articles)
        }
    }
}
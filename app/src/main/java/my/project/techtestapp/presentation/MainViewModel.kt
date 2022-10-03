package my.project.techtestapp.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import my.project.techtestapp.data.models.remote.AuthResponse
import my.project.techtestapp.data.models.remote.articles.ArticlesResponse
import my.project.techtestapp.data.repository.AuthRepository
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AuthRepository,
) : ViewModel() {

     val listArticles = MutableLiveData<ArticlesResponse?>()


    fun getAuth(phone: String, password: String) = repository.loginRepository(phone, password)

//    fun getAuth(phone: String, password: String): AuthResponse? {
////        viewModelScope.launch {
////            repository.loginRepository(phone, password)
//////            Log.e("TAG", "Heroes -> $bool")
////        }
//        return repository.loginRepository(phone, password)
//    }

    fun getArticles() {
        viewModelScope.launch(Dispatchers.IO) {
            val articles = repository.getArticles()
            listArticles.postValue(articles)
        }
    }
}
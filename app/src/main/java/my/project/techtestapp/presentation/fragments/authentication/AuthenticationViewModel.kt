package my.project.techtestapp.presentation.fragments.authentication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import my.project.techtestapp.data.repository.MainRepository
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val repository: MainRepository,
) : ViewModel() {

    private var _authResponse = MutableLiveData<Boolean?>()
    val authResponse: LiveData<Boolean?> = _authResponse

    fun loginFromApi(phone: String, password: String) {
        viewModelScope.launch {
            try {
                val result = repository.loadLoginStateFromApi(phone, password)
                _authResponse.postValue(result)
            } catch (e: Exception) {
                _authResponse.postValue(false)
                Log.e("Login Exception", "$e")
            }
        }
    }
}
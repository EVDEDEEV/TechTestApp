package my.project.techtestapp.presentation.fragments.authentication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import my.project.techtestapp.R
import my.project.techtestapp.app.Application
import my.project.techtestapp.data.repository.AuthenticationRepository
import my.project.techtestapp.utils.LoginState
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val context: Application
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Empty)
    val loginState: StateFlow<LoginState> = _loginState

    fun login(phone: String, password: String) = viewModelScope.launch {
        try {
            if (authenticationRepository.loadLoginStateFromApi(phone, password)) {
                _loginState.value = LoginState.Success
            }
        } catch (e: Exception) {
            _loginState.value = LoginState.Error(context.getString(R.string.error_answer))
            Log.e("Login Exception", "$e")
        }
    }

    private var _mask = MutableLiveData<String>()
    val mask: LiveData<String?> = _mask

    fun loadMask() {
        viewModelScope.launch {
            try {
                val result = authenticationRepository.loadMask()
                _mask.postValue(result)
            } catch (e: Exception) {
                _mask.postValue("")
                Log.e("Login Exception", "$e")
            }
        }
    }
}
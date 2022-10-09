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
import my.project.techtestapp.data.repository.MainRepository
import my.project.techtestapp.utils.LoginUiState
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val repository: MainRepository,
    private val context: Application
) : ViewModel() {

    private val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState.Empty)
    val loginUiState: StateFlow<LoginUiState> = _loginUiState

    fun login(phone: String, password: String) = viewModelScope.launch {
        try {
            if (repository.loadLoginStateFromApi(phone, password)) {
                _loginUiState.value = LoginUiState.Success
            }
        } catch (e: Exception) {
            _loginUiState.value = LoginUiState.Error(context.getString(R.string.error_answer))
            Log.e("Login Exception", "$e")
        }
    }

    private var _mask = MutableLiveData<String>()
    val mask: LiveData<String?> = _mask

    fun loadMask() {
        viewModelScope.launch {
            try {
                val result = repository.loadMask()
                _mask.postValue(result)
            } catch (e: Exception) {
                _mask.postValue("")
                Log.e("Login Exception", "$e")
            }
        }
    }
}
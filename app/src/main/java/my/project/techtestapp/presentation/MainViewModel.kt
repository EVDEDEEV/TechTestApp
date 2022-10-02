package my.project.techtestapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import my.project.techtestapp.data.models.remote.AuthResponse
import my.project.techtestapp.data.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AuthRepository,
) : ViewModel() {


//    fun getAuth(phone: String, password: String) = repository.getAuthBool(phone, password)

    fun getAuth(phone: String, password: String): AuthResponse? {
//        viewModelScope.launch {
//            repository.loginRepository(phone, password)
////            Log.e("TAG", "Heroes -> $bool")
//        }
        return repository.loginRepository(phone, password)
    }
}
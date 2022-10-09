package my.project.techtestapp.utils

sealed class LoginUiState {
    object Success : LoginUiState()
    data class Error(val message: String) : LoginUiState()
    object Empty: LoginUiState()
}
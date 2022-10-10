package my.project.techtestapp.utils

sealed class LoginState {
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
    object Empty: LoginState()
}
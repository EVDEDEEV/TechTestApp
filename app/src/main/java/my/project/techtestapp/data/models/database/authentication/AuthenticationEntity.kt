package my.project.techtestapp.data.models.database.authentication

import my.project.techtestapp.data.models.remote.authentication.AuthResponse

data class AuthenticationEntity(
    val success: AuthResponse,
    val userData: UserLoginPassword,
)
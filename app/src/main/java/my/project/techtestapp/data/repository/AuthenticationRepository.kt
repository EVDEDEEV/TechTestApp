package my.project.techtestapp.data.repository

import my.project.techtestapp.data.api.AuthenticationApi
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(
    private val authenticationApi: AuthenticationApi,
) {

    suspend fun loadMask(): String {
        return authenticationApi.getPhoneMaskFromApi().phoneMask
    }

    suspend fun loadLoginStateFromApi(phone: String, password: String): Boolean {
        return authenticationApi.login(phone, password).success
    }
}
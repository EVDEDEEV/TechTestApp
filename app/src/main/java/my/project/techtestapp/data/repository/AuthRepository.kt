package my.project.techtestapp.data.repository

import my.project.techtestapp.data.api.LTechApi
import my.project.techtestapp.data.models.remote.AuthResponse
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: LTechApi,
) {

    fun loginRepository(phone: String, password: String) {
        api.login(phone, password).body()
    }
}
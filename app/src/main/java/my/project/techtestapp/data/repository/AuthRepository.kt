package my.project.techtestapp.data.repository

import my.project.techtestapp.data.api.ArticlesResponseApi
import my.project.techtestapp.data.api.AuthenticationApi
import my.project.techtestapp.data.models.remote.authentication.AuthResponse
import my.project.techtestapp.data.models.remote.articles.ArticlesResponse
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authenticationApi: AuthenticationApi,
    private val articlesApi: ArticlesResponseApi,
) {


    suspend fun authFuncInRepository(phone: String, password: String): Boolean? {
//        return authenticationApi.login(phone, password)?.code()
        return authenticationApi.login(phone, password)?.body()?.success
    }

    suspend fun getArticles(): ArticlesResponse? {
        return articlesApi.getArticles().body()
    }


}
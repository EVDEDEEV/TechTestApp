package my.project.techtestapp.data.repository

import my.project.techtestapp.data.api.ArticlesResponseApi
import my.project.techtestapp.data.api.AuthenticationApi
import my.project.techtestapp.data.models.remote.articles.ArticlesResponse
import my.project.techtestapp.data.models.remote.articles.ArticlesResponseItem
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authenticationApi: AuthenticationApi,
    private val articlesApi: ArticlesResponseApi,
) {

    fun loginRepository(phone: String, password: String) {
        authenticationApi.login(phone, password).body()
    }

    suspend fun getArticles(): ArticlesResponse? {
        return articlesApi.getArticles().body()
    }
}
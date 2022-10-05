package my.project.techtestapp.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import my.project.techtestapp.data.api.ArticlesResponseApi
import my.project.techtestapp.data.api.AuthenticationApi
import my.project.techtestapp.data.models.database.articles.ArticlesDao
import my.project.techtestapp.data.models.database.articles.ArticlesEntity
import my.project.techtestapp.data.models.database.authentication.AuthenticationDao
import my.project.techtestapp.data.models.remote.articles.ArticlesResponse
import my.project.techtestapp.data.models.remote.articles.mapToEntity
import my.project.techtestapp.data.models.remote.authentication.AuthResponse
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authenticationApi: AuthenticationApi,
    private val articlesApi: ArticlesResponseApi,
    private val authenticationDao: AuthenticationDao,
    private val articlesDao: ArticlesDao,
) {

    suspend fun loginFromApi(phone: String, password: String): Boolean {
        Log.d("ddd", "dsdsd")
        val aa = authenticationApi.login(phone, password).success
        Log.d("OwnTag", "$aa")
        return aa
    }

    suspend fun deleteFromDb() = articlesDao.clearTab()

    fun getArticlesFromApi(): Flow<List<ArticlesEntity>> = flow {
        val articlesCache = articlesDao.getArticles()
        if (articlesCache.isNotEmpty()) {
            emit(articlesCache)
        }
        val articlesApi = loadDataFromApi()
        if (articlesApi.isNotEmpty()) {
            emit(articlesApi)
        }
    }

    private suspend fun loadDataFromApi(): List<ArticlesEntity> {
        try {
            val responseBody = articlesApi.getArticles().body()
            if (responseBody != null) {
                val articlesEntity = responseBody.mapToEntity()
                cache(articlesEntity)
                return articlesEntity
            }
        } catch (e: Exception) {
            return emptyList()
        }
        return emptyList()
    }

    private suspend fun cache(response: List<ArticlesEntity>) {
        articlesDao.insertArticles(response)
    }
}
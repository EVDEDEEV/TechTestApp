package my.project.techtestapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import my.project.techtestapp.data.api.ArticlesResponseApi
import my.project.techtestapp.data.api.AuthenticationApi
import my.project.techtestapp.data.models.database.articles.ArticlesDao
import my.project.techtestapp.data.models.database.articles.ArticlesEntity
import my.project.techtestapp.data.models.remote.articles.mapToEntity
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val authenticationApi: AuthenticationApi,
    private val articlesApi: ArticlesResponseApi,
    private val articlesDao: ArticlesDao,
) {

    suspend fun loadLoginStateFromApi(phone: String, password: String): Boolean {
        return authenticationApi.login(phone, password).success
    }

    suspend fun deleteFromDb() {
        articlesDao.clearArticlesTable()
    }

    fun getArticlesFromApi(): Flow<List<ArticlesEntity>> = flow {
        val articlesCache = articlesDao.getArticles()
        val articlesApi = loadDataFromApi()
        when {
            articlesCache.isNotEmpty() -> emit(articlesCache)
            articlesApi.isNotEmpty() -> emit(articlesApi)
        }
    }

    private suspend fun loadDataFromApi(): List<ArticlesEntity> {
        val emptyList = emptyList<ArticlesEntity>()
        try {
            val responseBody = articlesApi.getArticles().body()
            if (responseBody != null) {
                val articlesEntity = responseBody.mapToEntity()
                cacheArticlesFromApi(articlesEntity)
                return articlesEntity
            }
        } catch (e: Exception) {
            return emptyList
        }
        return emptyList
    }

    private suspend fun cacheArticlesFromApi(response: List<ArticlesEntity>) {
        articlesDao.insertArticles(response)
    }
}
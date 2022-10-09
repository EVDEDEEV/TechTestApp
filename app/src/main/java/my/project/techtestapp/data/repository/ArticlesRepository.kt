package my.project.techtestapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import my.project.techtestapp.data.api.ArticlesResponseApi
import my.project.techtestapp.data.models.database.articles.ArticlesDao
import my.project.techtestapp.data.models.database.articles.ArticlesEntity
import my.project.techtestapp.presentation.models.ArticlesUiModel
import my.project.techtestapp.utils.mapToEntity
import my.project.techtestapp.utils.mapToUi
import my.project.techtestapp.utils.mapToUiFromResponse
import javax.inject.Inject

class ArticlesRepository @Inject constructor(
    private val articlesApi: ArticlesResponseApi,
    private val articlesDao: ArticlesDao,
) {

    suspend fun deleteFromDb() {
        articlesDao.clearArticlesTable()
    }

    fun getArticlesFromApi(): Flow<List<ArticlesUiModel>> = flow {
        val articlesCache = articlesDao.getArticles()
        val articlesApi = loadDataFromApi()
        when {
            articlesCache.isNotEmpty() -> emit(articlesCache.mapToUi())
            articlesApi.isNotEmpty() -> emit(articlesApi)
        }
    }

    private suspend fun loadDataFromApi(): List<ArticlesUiModel> {
        val emptyList = emptyList<ArticlesUiModel>()
        try {
            val responseBody = articlesApi.getArticles().body()
            if (responseBody != null) {
                val articlesResponse = responseBody.mapToUiFromResponse()
                cacheArticlesFromApi(articlesResponse.mapToEntity())
                return articlesResponse
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
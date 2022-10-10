package my.project.techtestapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import my.project.techtestapp.data.api.ArticlesResponseApi
import my.project.techtestapp.data.models.database.articlesTable.ArticlesDao
import my.project.techtestapp.data.models.database.articlesTable.ArticlesEntity
import my.project.techtestapp.presentation.models.ArticlesListUiModel
import my.project.techtestapp.utils.mapFromArticlesEntityToUiModel
import my.project.techtestapp.utils.mapFromArticlesResponseToUiModel
import my.project.techtestapp.utils.mapToEntity
import javax.inject.Inject

class ArticlesListRepository @Inject constructor(
    private val articlesApi: ArticlesResponseApi,
    private val articlesDao: ArticlesDao,
) {

    suspend fun deleteFromDb() {
        articlesDao.clearArticlesTable()
    }

    fun getArticlesFromRepository(): Flow<List<ArticlesListUiModel>> = flow {
        val articlesCache = articlesDao.getArticles()
        val articlesApi = loadArticlesListFromApi()
        when {
            articlesCache.isNotEmpty() -> emit(articlesCache.mapFromArticlesEntityToUiModel())
            articlesApi.isNotEmpty() -> emit(articlesApi)
        }
    }

    private suspend fun loadArticlesListFromApi(): List<ArticlesListUiModel> {
        val emptyList = emptyList<ArticlesListUiModel>()
        try {
            val responseBody = articlesApi.getArticles().body()
            if (responseBody != null) {
                val articlesResponse = responseBody.mapFromArticlesResponseToUiModel()
                cacheArticlesFromApi(articlesResponse.mapToEntity())
                return articlesResponse
            }
        } catch (e: Exception) {
            return emptyList
        }
        return emptyList
    }

    private suspend fun cacheArticlesFromApi(articlesEntityList: List<ArticlesEntity>) {
        articlesDao.insertArticles(articlesEntityList)
    }
}
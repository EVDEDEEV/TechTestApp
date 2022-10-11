package my.project.techtestapp.data.repository

import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import my.project.techtestapp.app.Application
import my.project.techtestapp.data.api.ArticlesResponseApi
import my.project.techtestapp.data.models.database.articlesTable.ArticlesDao
import my.project.techtestapp.data.models.database.articlesTable.ArticlesEntity
import my.project.techtestapp.data.worker.ScheduledArticlesRefresh
import my.project.techtestapp.presentation.models.ArticlesListUiModel
import my.project.techtestapp.utils.mapFromArticlesResponseToUiModel
import my.project.techtestapp.utils.mapToEntity
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ArticlesListRepository
@Inject constructor(
    private val articlesApi: ArticlesResponseApi,
    private val articlesDao: ArticlesDao,
    private val application: Application,
) {

    suspend fun deleteFromDb() {
        articlesDao.clearArticlesTable()
    }

    suspend fun getCachedArticles(): List<ArticlesEntity> {
        return articlesDao.getArticles()
    }

    suspend fun loadArticlesListFromApi(): List<ArticlesListUiModel> {
        val emptyList = emptyList<ArticlesListUiModel>()
        try {
            val responseBody = articlesApi.getArticles().body()
            if (responseBody != null) {
                val articlesResponse = responseBody.mapFromArticlesResponseToUiModel()
                deleteFromDb()
                cacheArticlesToEntityFromApi(articlesResponse.mapToEntity())
                return articlesResponse
            }
        } catch (e: Exception) {
            return emptyList
        }
        return emptyList
    }

    private suspend fun cacheArticlesToEntityFromApi(articlesEntityList: List<ArticlesEntity>) {
        articlesDao.insertArticles(articlesEntityList)
    }

    fun refreshArticlesInBackground() {
        val request = PeriodicWorkRequest
            .Builder(ScheduledArticlesRefresh::class.java, 30, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(application).enqueue(request)
    }
}
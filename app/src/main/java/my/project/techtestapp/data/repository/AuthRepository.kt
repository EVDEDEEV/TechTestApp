package my.project.techtestapp.data.repository

import my.project.techtestapp.data.api.ArticlesResponseApi
import my.project.techtestapp.data.api.AuthenticationApi
import my.project.techtestapp.data.models.database.articles.ArticlesDao
import my.project.techtestapp.data.models.database.articles.ArticlesEntity
import my.project.techtestapp.data.models.database.authentication.AuthenticationDao
import my.project.techtestapp.data.models.database.authentication.AuthenticationEntity
import my.project.techtestapp.data.models.remote.articles.mapToEntity
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authenticationApi: AuthenticationApi,
    private val articlesApi: ArticlesResponseApi,
    private val authenticationDao: AuthenticationDao,
    private val articlesDao: ArticlesDao,
) {

    //    suspend fun authFuncInRepository(phone: String, password: String): Boolean? {
//        return authenticationApi.login(phone, password)?.body()?.success
//    }
    suspend fun authFuncInRepository(phone: String, password: String): Boolean {
        val cache = authenticationDao.getAuthStateFromEntity().success
        if (!cache) {
            val result = authenticationApi.login(phone, password)?.body()?.success
            val resultResponse = result?.let { AuthenticationEntity(it) }
            if (resultResponse != null) {
                authenticationDao.insertAuthState(resultResponse)
            }
        }
        return cache
    }

//
//    private suspend fun getArticlesFromApi(): Response<ArticlesResponse> {
//        return articlesApi.getArticles()
//    }

    suspend fun getArticlesFromApi() {
        val responseBody = articlesApi.getArticles().body()
        if (responseBody != null) {
            articlesDao.insertArticles(responseBody.mapToEntity())
        }
    }


    fun getArticlesFromDb(): List<ArticlesEntity> = articlesDao.getArticles()


//    suspend fun getArticles(): List<ArticlesEntity> {
//        val articlesCache = articlesDao.getArticles()
//        if (articlesCache.isEmpty()) {
//            val responseBody = articlesApi.getArticles().body()
//            if (responseBody != null) {
//                articlesDao.insertArticles(responseBody.mapToEntity())
//            }
//        }
//        return articlesCache
//    }


    //    suspend fun authFuncInRepository(phone: String, password: String): Boolean {
//        val cache = authenticationDao.getAuthStateFromEntity().success
//        val result = authenticationApi.login(phone, password)?.body()?.success
//        val resultResponse = result?.let { AuthenticationEntity(it) }
//        if (resultResponse != null || !cache) {
//            if (resultResponse != null) {
//                authenticationDao.insertAuthState(resultResponse)
//            }
//        }
//        return cache
//    }

//    suspend fun getArticles(): ArticlesResponse? {
//        return articlesApi.getArticles().body()
//    }


}
package my.project.techtestapp.data.repository

import my.project.techtestapp.data.api.ArticlesResponseApi
import my.project.techtestapp.data.api.AuthenticationApi
import my.project.techtestapp.data.models.database.authentication.AuthenticationDao
import my.project.techtestapp.data.models.database.authentication.AuthenticationEntity
import my.project.techtestapp.data.models.remote.articles.ArticlesResponse
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authenticationApi: AuthenticationApi,
    private val articlesApi: ArticlesResponseApi,
    private val authenticationDao: AuthenticationDao,
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


    suspend fun getArticles(): ArticlesResponse? {
        return articlesApi.getArticles().body()
    }
}
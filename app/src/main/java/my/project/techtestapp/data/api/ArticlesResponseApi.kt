package my.project.techtestapp.data.api

import my.project.techtestapp.data.models.remote.articles.ArticlesResponse
import my.project.techtestapp.data.models.remote.articles.ArticlesResponseItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticlesResponseApi {

    @GET("/api/v1/posts")
    suspend fun getArticles(): Response<ArticlesResponse>
}
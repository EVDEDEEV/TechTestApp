package my.project.techtestapp.data.api

import my.project.techtestapp.data.models.remote.articles.ArticlesResponse
import retrofit2.Response
import retrofit2.http.GET

interface ArticlesResponseApi {

    @GET("/api/v1/posts")
    suspend fun getArticles(): Response<ArticlesResponse>
}
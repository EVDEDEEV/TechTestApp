package my.project.techtestapp.data.api

import my.project.techtestapp.data.models.remote.authentication.AuthResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthenticationApi {

//    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("/api/v1/auth")
    suspend fun login(
//        @Query("phone") phone: String,
//        @Query("password") password: String,
        @Field("phone") phone: String,
        @Field("password") password: String,
    )
    : AuthResponse
}

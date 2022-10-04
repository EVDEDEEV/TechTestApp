package my.project.techtestapp.data.api

import my.project.techtestapp.data.models.remote.authentication.AuthResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthenticationApi {

    @FormUrlEncoded
    @POST("/api/v1/auth")
    suspend fun login(
        @Field("phone") phone: String,
        @Field("password") password: String
    )
    : Response<AuthResponse>?
}
package my.project.techtestapp.data.api

import my.project.techtestapp.data.models.remote.AuthResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthenticationApi {

    @POST("auth")
    @FormUrlEncoded
    fun login(
        @Field("phone") phone: String,
        @Field("password") password: String
    )
    : Response<AuthResponse>
}
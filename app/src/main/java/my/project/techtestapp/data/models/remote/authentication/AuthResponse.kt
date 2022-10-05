package my.project.techtestapp.data.models.remote.authentication

import com.google.gson.annotations.SerializedName
import my.project.techtestapp.data.models.database.authentication.AuthenticationEntity

data class AuthResponse(
    @SerializedName("success")
    val success: Boolean,
)

//fun AuthResponse.mapToAuthEntity() : AuthenticationEntity {
//    return AuthenticationEntity(success)
//}
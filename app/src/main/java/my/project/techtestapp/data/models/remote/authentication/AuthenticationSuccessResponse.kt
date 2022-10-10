package my.project.techtestapp.data.models.remote.authentication

import com.google.gson.annotations.SerializedName
import my.project.techtestapp.data.models.database.authenticationTable.success.AuthenticationSuccessEntity

data class AuthenticationSuccessResponse(
    @SerializedName("success")
    val success: Boolean,
)

fun AuthenticationSuccessResponse.mapFromSuccessAuthenticationResponseToEntity():AuthenticationSuccessEntity {
    return AuthenticationSuccessEntity(this.success)
}
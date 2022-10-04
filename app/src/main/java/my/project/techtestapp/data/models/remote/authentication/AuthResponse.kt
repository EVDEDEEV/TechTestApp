package my.project.techtestapp.data.models.remote.authentication

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("success")
    val success: Boolean,
)


package my.project.techtestapp.data.models.remote

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("success")
    val success: String,
)

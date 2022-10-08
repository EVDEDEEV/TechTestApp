package my.project.techtestapp.data.models.authentication

import androidx.room.Entity

@Entity(tableName = "AUTHENTICATION_TABLE")
data class AuthenticationEntity(
    val success: Boolean,
    val phone: String,
    val password: String,
)
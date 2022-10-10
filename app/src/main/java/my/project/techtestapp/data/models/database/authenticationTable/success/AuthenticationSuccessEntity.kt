package my.project.techtestapp.data.models.database.authenticationTable.success

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AUTHENTICATION_SUCCESS_TABLE")
data class AuthenticationSuccessEntity(
    @PrimaryKey
    val success: Boolean,
)
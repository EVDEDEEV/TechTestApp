package my.project.techtestapp.data.models.database.authentication

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AUTHENTICATION_TABLE")
data class AuthenticationEntity(
    @PrimaryKey
    @ColumnInfo(name = "SUCCESS")
    val success: Boolean,
)

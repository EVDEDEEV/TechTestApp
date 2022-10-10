package my.project.techtestapp.data.models.database.authenticationTable.success

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AuthenticationSuccessDao {

    @Query("SELECT * FROM AUTHENTICATION_SUCCESS_TABLE")
     fun getAuthenticationSuccess(): AuthenticationSuccessEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuthenticationSuccess(authenticationSuccessEntity: AuthenticationSuccessEntity)

    @Query("DELETE FROM AUTHENTICATION_SUCCESS_TABLE")
    suspend fun clearAuthenticationSuccess()

}
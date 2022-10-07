package my.project.techtestapp.data.models.authentication

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AuthenticationDao {

    @Query("SELECT * FROM AUTHENTICATION_TABLE")
    suspend fun getAuthData(): AuthenticationEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuthData(authenticationData: AuthenticationEntity)

    @Query("DELETE FROM AUTHENTICATION_TABLE")
    suspend fun clearDataTable()


}
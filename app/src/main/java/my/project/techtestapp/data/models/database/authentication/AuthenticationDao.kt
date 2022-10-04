package my.project.techtestapp.data.models.database.authentication

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AuthenticationDao {

    @Query("SELECT * FROM authentication_table")
    fun getAuthStateFromEntity(): AuthenticationEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAuthState(authenticationState: AuthenticationEntity)
}
package my.project.techtestapp.data.models.database.authentication

import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AuthenticationDao {

    @Query("SELECT * FROM authentication_table")
     fun getAuthStateFromEntity(): Flow<AuthenticationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAuthState(authenticationState: AuthenticationEntity)
}
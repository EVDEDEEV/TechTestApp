package my.project.techtestapp.data.models.database.articles

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArticlesDao {

    @Query("SELECT * FROM articles_table")
    fun getArticles(): List<ArticlesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<ArticlesEntity?>)
}
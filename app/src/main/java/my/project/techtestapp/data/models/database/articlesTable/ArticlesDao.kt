package my.project.techtestapp.data.models.database.articlesTable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArticlesDao {

    @Query("SELECT * FROM ARTICLES_TABLE")
    suspend fun getArticles(): List<ArticlesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<ArticlesEntity?>)

    @Query("DELETE FROM ARTICLES_TABLE")
    suspend fun clearArticlesTable()
}
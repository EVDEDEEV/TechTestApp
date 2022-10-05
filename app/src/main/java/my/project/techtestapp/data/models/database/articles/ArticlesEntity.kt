package my.project.techtestapp.data.models.database.articles

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "ARTICLES_TABLE")
data class ArticlesEntity(
    @PrimaryKey
    @ColumnInfo(name = "ID")
    val id: String,
    @ColumnInfo(name = "DATE")
    val date: String,
    @ColumnInfo(name = "IMAGE")
    val image: String,
    @ColumnInfo(name = "SORT")
    val sort: Int,
    @ColumnInfo(name = "TEXT")
    val text: String,
    @ColumnInfo(name = "TITLE")
    val title: String,
)


package my.project.techtestapp.data.models.database

import androidx.room.Database
import androidx.room.RoomDatabase
import my.project.techtestapp.data.models.database.articles.ArticlesDao
import my.project.techtestapp.data.models.database.articles.ArticlesEntity

@Database(entities = [ArticlesEntity::class], version = 2)
abstract class TechTestAppDatabase : RoomDatabase() {
    abstract fun articlesDao(): ArticlesDao

}
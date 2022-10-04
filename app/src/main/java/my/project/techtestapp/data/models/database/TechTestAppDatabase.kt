package my.project.techtestapp.data.models.database

import androidx.room.Database
import androidx.room.RoomDatabase
import my.project.techtestapp.data.models.database.articles.ArticlesDao
import my.project.techtestapp.data.models.database.articles.ArticlesEntity
import my.project.techtestapp.data.models.database.authentication.AuthenticationDao
import my.project.techtestapp.data.models.database.authentication.AuthenticationEntity

@Database(entities = [AuthenticationEntity::class, ArticlesEntity::class], version = 2)
abstract class TechTestAppDatabase : RoomDatabase() {
    abstract fun authenticationDao(): AuthenticationDao
    abstract fun arteclesDao(): ArticlesDao


}
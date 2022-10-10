package my.project.techtestapp.data.models.database

import androidx.room.Database
import androidx.room.RoomDatabase
import my.project.techtestapp.data.models.database.articlesTable.ArticlesDao
import my.project.techtestapp.data.models.database.articlesTable.ArticlesEntity
import my.project.techtestapp.data.models.database.authenticationTable.success.AuthenticationSuccessDao
import my.project.techtestapp.data.models.database.authenticationTable.success.AuthenticationSuccessEntity

@Database(entities = [
    ArticlesEntity::class,
    AuthenticationSuccessEntity::class],
    exportSchema = false,
    version = 3)

abstract class TechTestAppDatabase : RoomDatabase() {
    abstract fun articlesDao(): ArticlesDao
    abstract fun authenticationSuccessDao(): AuthenticationSuccessDao
}
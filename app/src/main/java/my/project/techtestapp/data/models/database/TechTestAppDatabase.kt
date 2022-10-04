package my.project.techtestapp.data.models.database

import androidx.room.Database
import androidx.room.RoomDatabase
import my.project.techtestapp.data.models.database.authentication.AuthenticationDao
import my.project.techtestapp.data.models.database.authentication.AuthenticationEntity

@Database(entities = [AuthenticationEntity::class], version = 1)
abstract class TechTestAppDatabase : RoomDatabase() {
    abstract fun authenticationDao(): AuthenticationDao


}
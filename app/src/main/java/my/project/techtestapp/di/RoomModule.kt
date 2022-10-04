package my.project.techtestapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import my.project.techtestapp.data.models.database.TechTestAppDatabase
import my.project.techtestapp.data.models.database.articles.ArticlesDao
import my.project.techtestapp.data.models.database.authentication.AuthenticationDao
import my.project.techtestapp.utils.Constants.ROOM_DATABASE_NAME
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): TechTestAppDatabase {
        return Room.databaseBuilder(
            context,
            TechTestAppDatabase::class.java,
            ROOM_DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideAuthenticationDao(database: TechTestAppDatabase): AuthenticationDao {
        return database.authenticationDao()
    }

    @Provides
    @Singleton
    fun provideArticlesDao(database: TechTestAppDatabase): ArticlesDao {
        return database.arteclesDao()
    }

}
package my.project.techtestapp.app

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class Application : Application(), Configuration.Provider {

    override fun onCreate() {
        super.onCreate()
        WorkManager.getInstance(applicationContext).cancelAllWork()
    }

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

     override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}
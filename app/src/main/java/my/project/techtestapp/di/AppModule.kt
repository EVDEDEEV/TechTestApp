package my.project.techtestapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import my.project.techtestapp.app.Application
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext context: Context): Application {
        return context as Application
    }

//    @Provides
//    @Singleton
//    fun httpLoggingInterceptor(): HttpLoggingInterceptor =
//        HttpLoggingInterceptor().apply {
//            this.level = HttpLoggingInterceptor.Level.BODY
//        }
//
//    @Singleton
//    @Provides
//    fun provideRatesApiInterface(retrofit: Retrofit): AuthenticationApi {
//        return retrofit.create(AuthenticationApi::class.java)
//    }
//
//    @Singleton
//    @Provides
//    fun provideArticlesInterface(retrofit: Retrofit): ArticlesResponseApi {
//        return retrofit.create(ArticlesResponseApi::class.java)
//    }
//
//    @Provides
//    @Singleton
//    fun provideRetrofit(): Retrofit {
//        val client = OkHttpClient.Builder()
//            .addInterceptor(httpLoggingInterceptor())
//            .connectTimeout(60, TimeUnit.SECONDS)
//            .writeTimeout(120, TimeUnit.SECONDS)
//            .readTimeout(60, TimeUnit.SECONDS)
//            .build()
//
//        return Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .client(client)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
}
package com.emamagic.moviestreaming.di

import com.emamagic.moviestreaming.BuildConfig
import com.emamagic.moviestreaming.network.api.GenreApi
import com.emamagic.moviestreaming.network.api.HomeApi
import com.emamagic.moviestreaming.network.api.MovieApi
import com.emamagic.moviestreaming.network.intercepter.ClientConnection
import com.emamagic.moviestreaming.network.intercepter.ServerConnection
import com.emamagic.moviestreaming.util.Const
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return loggingInterceptor
    }

    @Singleton
    @Provides
    fun provideOkHttp(loggingInterceptor: HttpLoggingInterceptor, clientConnection: ClientConnection, serverConnection: ServerConnection): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(clientConnection)
            .addInterceptor(serverConnection)
            .readTimeout(8 ,TimeUnit.SECONDS)
            .writeTimeout(8 ,TimeUnit.SECONDS)
            .connectTimeout(5 ,TimeUnit.SECONDS)
            .build()
    }


    @Singleton
    @Provides
    fun provideRetrofit(client: Lazy<OkHttpClient>): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Const.BASE_URL)
            .callFactory { request ->
                // this bellow fun ,called in background thread
                client.get().newCall(request)
            }
            .build()
    }

    @Provides
    fun provideHomeApi(retrofit: Retrofit): HomeApi = retrofit.create(HomeApi::class.java)

    @Provides
    fun provideGenreApi(retrofit: Retrofit): GenreApi = retrofit.create(GenreApi::class.java)

    @Provides
    fun provideMovieApi(retrofit: Retrofit): MovieApi = retrofit.create(MovieApi::class.java)

}
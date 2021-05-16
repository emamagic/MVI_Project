package com.emamagic.moviestreaming.di

import com.emamagic.moviestreaming.network.api.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RetrofitModuleTest {


    @Provides
    fun provideMockWebServer() = MockWebServer()

    @Singleton
    @Provides
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .connectTimeout(5, TimeUnit.SECONDS)
            .build()
    }


    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient, mockWebServer: MockWebServer): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(mockWebServer.url("/"))
            .client(client)
            .build()
    }

    @Provides
    fun provideHomeApi(retrofit: Retrofit): com.emamagic.moviestreaming.data.network.api.HomeApi = retrofit.create(
        com.emamagic.moviestreaming.data.network.api.HomeApi::class.java)

    @Provides
    fun provideGenreApi(retrofit: Retrofit): com.emamagic.moviestreaming.data.network.api.GenreApi = retrofit.create(
        com.emamagic.moviestreaming.data.network.api.GenreApi::class.java)

    @Provides
    fun provideMovieApi(retrofit: Retrofit): com.emamagic.moviestreaming.data.network.api.MovieApi = retrofit.create(
        com.emamagic.moviestreaming.data.network.api.MovieApi::class.java)

    @Provides
    fun provideSearchApi(retrofit: Retrofit): com.emamagic.moviestreaming.data.network.api.SearchApi = retrofit.create(
        com.emamagic.moviestreaming.data.network.api.SearchApi::class.java)

    @Provides
    fun provideAuthApi(retrofit: Retrofit): com.emamagic.moviestreaming.data.network.api.AuthApi = retrofit.create(
        com.emamagic.moviestreaming.data.network.api.AuthApi::class.java)

}
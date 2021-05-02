package com.emamagic.moviestreaming.di

import android.content.Context
import androidx.room.Room
import com.emamagic.moviestreaming.db.MovieDatabase
import com.emamagic.moviestreaming.util.Const
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {


    @Singleton
    @Provides
    fun provideMovieDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context ,MovieDatabase::class.java , Const.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideSliderDao(database: MovieDatabase) = database.sliderDao()

    @Provides
    fun provideMovieDao(database: MovieDatabase) = database.movieDao()

    @Provides
    fun provideGenreDao(database: MovieDatabase) = database.genreDao()

    @Provides
    fun provideCastDao(database: MovieDatabase) = database.castDao()

    @Provides
    fun provideSeasonDao(database: MovieDatabase) = database.seasonDao()

    @Provides
    fun provideEpisodeDao(database: MovieDatabase) = database.episodeDao()
}
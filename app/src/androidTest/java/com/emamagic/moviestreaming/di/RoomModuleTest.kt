package com.emamagic.moviestreaming.di

import android.content.Context
import androidx.room.Room
import com.emamagic.moviestreaming.data.db.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModuleTest {

  //  @Named("TEST_DB")
    @Provides
    @Singleton
    fun provideInMemoryDb(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context , com.emamagic.moviestreaming.data.db.MovieDatabase::class.java)
            .allowMainThreadQueries()
            .build()

    @Provides
    fun provideSliderDao(database: com.emamagic.moviestreaming.data.db.MovieDatabase) = database.sliderDao()

    @Provides
    fun provideMovieDao(database: com.emamagic.moviestreaming.data.db.MovieDatabase) = database.movieDao()

    @Provides
    fun provideGenreDao(database: com.emamagic.moviestreaming.data.db.MovieDatabase) = database.genreDao()

    @Provides
    fun provideCastDao(database: com.emamagic.moviestreaming.data.db.MovieDatabase) = database.castDao()

    @Provides
    fun provideSeasonDao(database: com.emamagic.moviestreaming.data.db.MovieDatabase) = database.seasonDao()

    @Provides
    fun provideEpisodeDao(database: com.emamagic.moviestreaming.data.db.MovieDatabase) = database.episodeDao()

    @Provides
    fun provideFavoriteDao(database: com.emamagic.moviestreaming.data.db.MovieDatabase) = database.favoriteDao()

}
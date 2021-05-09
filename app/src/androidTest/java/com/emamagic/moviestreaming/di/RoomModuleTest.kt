package com.emamagic.moviestreaming.di

import android.content.Context
import androidx.room.Room
import com.emamagic.moviestreaming.db.MovieDatabase
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
        Room.inMemoryDatabaseBuilder(context ,MovieDatabase::class.java)
            .allowMainThreadQueries()
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

    @Provides
    fun provideFavoriteDao(database: MovieDatabase) = database.favoriteDao()

}
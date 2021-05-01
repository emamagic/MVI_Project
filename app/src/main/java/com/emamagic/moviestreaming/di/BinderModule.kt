package com.emamagic.moviestreaming.di

import com.emamagic.moviestreaming.repository.genre_list.GenreListRepository
import com.emamagic.moviestreaming.repository.genre_list.GenreListRepositoryImpl
import com.emamagic.moviestreaming.repository.home.HomeRepository
import com.emamagic.moviestreaming.repository.home.HomeRepositoryImpl
import com.emamagic.moviestreaming.repository.movie.MovieRepository
import com.emamagic.moviestreaming.repository.movie.MovieRepositoryImpl
import com.emamagic.moviestreaming.repository.movie_list.MovieListRepository
import com.emamagic.moviestreaming.repository.movie_list.MovieListRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class BinderModule {

    @Binds
    abstract fun bindHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository

    @Binds
    abstract fun bindGenreRepository(genreRepositoryImpl: GenreListRepositoryImpl): GenreListRepository

    @Binds
    abstract fun bindMovieListRepository(movieListRepositoryImpl: MovieListRepositoryImpl): MovieListRepository

    @Binds
    abstract fun bindMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository
}
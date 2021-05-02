package com.emamagic.moviestreaming.di

import com.emamagic.moviestreaming.repository.episode_list.EpisodeListRepository
import com.emamagic.moviestreaming.repository.episode_list.EpisodeListRepositoryImpl
import com.emamagic.moviestreaming.repository.favorite.FavoriteRepository
import com.emamagic.moviestreaming.repository.favorite.FavoriteRepositoryImpl
import com.emamagic.moviestreaming.repository.genre_list.GenreListRepository
import com.emamagic.moviestreaming.repository.genre_list.GenreListRepositoryImpl
import com.emamagic.moviestreaming.repository.genre_type.GenreTypeRepository
import com.emamagic.moviestreaming.repository.genre_type.GenreTypeRepositoryImpl
import com.emamagic.moviestreaming.repository.home.HomeRepository
import com.emamagic.moviestreaming.repository.home.HomeRepositoryImpl
import com.emamagic.moviestreaming.repository.movie.MovieRepository
import com.emamagic.moviestreaming.repository.movie.MovieRepositoryImpl
import com.emamagic.moviestreaming.repository.movie_list.MovieListRepository
import com.emamagic.moviestreaming.repository.movie_list.MovieListRepositoryImpl
import com.emamagic.moviestreaming.repository.search.SearchRepository
import com.emamagic.moviestreaming.repository.search.SearchRepositoryImpl
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
    abstract fun bindGenreTypeRepository(genreTypeRepositoryImpl: GenreTypeRepositoryImpl): GenreTypeRepository

    @Binds
    abstract fun bindMovieListRepository(movieListRepositoryImpl: MovieListRepositoryImpl): MovieListRepository

    @Binds
    abstract fun bindMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository

    @Binds
    abstract fun bindEpisodeRepository(episodeListRepositoryImpl: EpisodeListRepositoryImpl): EpisodeListRepository

    @Binds
    abstract fun bindGenreListRepository(genreListRepositoryImpl: GenreListRepositoryImpl): GenreListRepository

    @Binds
    abstract fun bindSearchRepository(searchRepositoryImpl: SearchRepositoryImpl): SearchRepository

    @Binds
    abstract fun bindFavoriteRepository(favoriteRepositoryImpl: FavoriteRepositoryImpl): FavoriteRepository

}


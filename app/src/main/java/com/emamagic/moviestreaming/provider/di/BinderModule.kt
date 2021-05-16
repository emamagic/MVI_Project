package com.emamagic.moviestreaming.provider.di

import com.emamagic.moviestreaming.data.repository.auth.login.LoginRepository
import com.emamagic.moviestreaming.data.repository.auth.login.LoginRepositoryImpl
import com.emamagic.moviestreaming.data.repository.auth.register.RegisterRepository
import com.emamagic.moviestreaming.data.repository.auth.register.RegisterRepositoryImpl
import com.emamagic.moviestreaming.data.repository.auth.verify.VerifyRepository
import com.emamagic.moviestreaming.data.repository.auth.verify.VerifyRepositoryImpl
import com.emamagic.moviestreaming.data.repository.episode_list.EpisodeListRepository
import com.emamagic.moviestreaming.data.repository.episode_list.EpisodeListRepositoryImpl
import com.emamagic.moviestreaming.data.repository.favorite.FavoriteRepository
import com.emamagic.moviestreaming.data.repository.favorite.FavoriteRepositoryImpl
import com.emamagic.moviestreaming.data.repository.genre_list.GenreListRepository
import com.emamagic.moviestreaming.data.repository.genre_list.GenreListRepositoryImpl
import com.emamagic.moviestreaming.data.repository.genre_type.GenreTypeRepository
import com.emamagic.moviestreaming.data.repository.genre_type.GenreTypeRepositoryImpl
import com.emamagic.moviestreaming.data.repository.home.HomeRepository
import com.emamagic.moviestreaming.data.repository.home.HomeRepositoryImpl
import com.emamagic.moviestreaming.data.repository.movie.MovieRepository
import com.emamagic.moviestreaming.data.repository.movie.MovieRepositoryImpl
import com.emamagic.moviestreaming.data.repository.movie_list.MovieListRepository
import com.emamagic.moviestreaming.data.repository.movie_list.MovieListRepositoryImpl
import com.emamagic.moviestreaming.data.repository.search.SearchRepository
import com.emamagic.moviestreaming.data.repository.search.SearchRepositoryImpl
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

    @Binds
    abstract fun bindRegisterRepository(registerRepositoryImpl: RegisterRepositoryImpl): RegisterRepository

    @Binds
    abstract fun bindLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository

    @Binds
    abstract fun bindVerifyRepository(verifyRepositoryImpl: VerifyRepositoryImpl): VerifyRepository

}


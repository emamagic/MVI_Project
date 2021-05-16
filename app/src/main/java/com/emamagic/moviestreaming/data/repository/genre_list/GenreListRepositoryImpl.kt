package com.emamagic.moviestreaming.data.repository.genre_list

import com.emamagic.moviestreaming.data.db.dao.FavoriteDao
import com.emamagic.moviestreaming.data.db.dao.MovieDao
import com.emamagic.moviestreaming.data.db.entity.FavoriteEntity
import com.emamagic.moviestreaming.data.db.entity.MovieWithFavorite
import com.emamagic.moviestreaming.data.network.api.GenreApi
import com.emamagic.moviestreaming.ui.base.insertOrDelete
import com.emamagic.moviestreaming.ui.base.upsert
import com.emamagic.moviestreaming.provider.mapper.MovieMapper
import com.emamagic.moviestreaming.provider.safe.ResultWrapper
import com.emamagic.moviestreaming.provider.safe.SafeApi
import com.emamagic.moviestreaming.provider.safe.bound.networkBoundResource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class GenreListRepositoryImpl @Inject constructor(
    private val genreApi: GenreApi,
    private val movieDao: MovieDao,
    private val movieMapper: MovieMapper,
    private val favoriteDao: FavoriteDao
): SafeApi() ,GenreListRepository {


    override fun getGenreByCategory(genreName: String): Flow<ResultWrapper<List<MovieWithFavorite>>> {
        return networkBoundResource(
            errorHandler = this,
            databaseQuery = { movieDao.getMoviesWithFavoriteByGenre(genreName) },
            networkCall = { genreApi.getGenreByCategory(genreName) },
            saveCallResult = { movieDao.upsert(movieMapper.mapFromEntityList(genreApi.getGenreByCategory(genreName).movies)) }
        )
    }

    override suspend fun updateFavoriteById(item: FavoriteEntity) {
        favoriteDao.insertOrDelete(item)
    }


}
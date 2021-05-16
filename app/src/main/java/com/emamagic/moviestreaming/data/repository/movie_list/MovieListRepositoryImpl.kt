package com.emamagic.moviestreaming.data.repository.movie_list

import com.emamagic.moviestreaming.data.db.dao.FavoriteDao
import com.emamagic.moviestreaming.data.db.dao.MovieDao
import com.emamagic.moviestreaming.data.db.entity.FavoriteEntity
import com.emamagic.moviestreaming.data.db.entity.MovieWithFavorite
import com.emamagic.moviestreaming.data.network.api.MovieApi
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
class MovieListRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDao: MovieDao,
    private val movieMapper: MovieMapper,
    private val favoriteDao: FavoriteDao
) : SafeApi(), MovieListRepository {


    override fun getAllMovie(category: String): Flow<ResultWrapper<List<MovieWithFavorite>>> {
        return networkBoundResource(
            errorHandler = this,
            databaseQuery = { movieDao.getMoviesWithFavoriteByCategory(category) },
            networkCall = { movieApi.getAllMovie(category) },
            saveCallResult = { movieDao.upsert(movieMapper.mapFromEntityList(it.movies)) }
        )
    }

    override suspend fun updateFavoriteById(item: FavoriteEntity) {
        favoriteDao.insertOrDelete(item)
    }


}
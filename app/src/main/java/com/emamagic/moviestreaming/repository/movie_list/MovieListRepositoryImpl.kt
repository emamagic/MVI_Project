package com.emamagic.moviestreaming.repository.movie_list

import com.emamagic.moviestreaming.base.insertOrDelete
import com.emamagic.moviestreaming.base.upsert
import com.emamagic.moviestreaming.db.dao.FavoriteDao
import com.emamagic.moviestreaming.db.dao.MovieDao
import com.emamagic.moviestreaming.db.entity.FavoriteEntity
import com.emamagic.moviestreaming.db.entity.MovieWithFavorite
import com.emamagic.moviestreaming.mapper.MovieMapper
import com.emamagic.moviestreaming.network.api.MovieApi
import com.emamagic.moviestreaming.util.helper.safe.ResultWrapper
import com.emamagic.moviestreaming.util.helper.safe.SafeApi
import com.emamagic.moviestreaming.util.helper.safe.networkBoundResource
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
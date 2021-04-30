package com.emamagic.moviestreaming.repository.movie_list

import com.emamagic.moviestreaming.base.upsert
import com.emamagic.moviestreaming.db.dao.MovieDao
import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.emamagic.moviestreaming.mapper.MovieMapper
import com.emamagic.moviestreaming.network.api.MovieApi
import com.emamagic.moviestreaming.util.helper.safe.ResultWrapper
import com.emamagic.moviestreaming.util.helper.safe.error.GeneralErrorHandlerImpl
import com.emamagic.moviestreaming.util.helper.safe.networkBoundResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieListRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDao: MovieDao,
    private val movieMapper: MovieMapper
) : GeneralErrorHandlerImpl(), MovieListRepository {


    override fun getAllMovie(category: String): Flow<ResultWrapper<List<MovieEntity>>> {
        return networkBoundResource(
            errorHandler = this,
            databaseQuery = { movieDao.getAllMovie(category) },
            networkCall = { movieApi.getAllMovie(category) },
            saveCallResult = { movieDao.upsert(movieMapper.mapFromEntityList(it.movies)) }
        )
    }


}
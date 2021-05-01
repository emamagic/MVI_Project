package com.emamagic.moviestreaming.repository.movie

import com.emamagic.moviestreaming.db.dao.MovieDao
import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.emamagic.moviestreaming.mapper.MovieMapper
import com.emamagic.moviestreaming.network.api.MovieApi
import com.emamagic.moviestreaming.util.helper.safe.ResultWrapper
import com.emamagic.moviestreaming.util.helper.safe.error.GeneralErrorHandlerImpl
import com.emamagic.moviestreaming.util.helper.safe.networkBoundResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieDao: MovieDao,
): GeneralErrorHandlerImpl() ,MovieRepository {

    override suspend fun getMovieById(id: Long): MovieEntity {
        return movieDao.getMovieById(id)
    }


}
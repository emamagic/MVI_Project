package com.emamagic.moviestreaming.repository.movie

import androidx.room.withTransaction
import com.emamagic.moviestreaming.base.upsert
import com.emamagic.moviestreaming.db.MovieDatabase
import com.emamagic.moviestreaming.db.entity.CastEntity
import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.emamagic.moviestreaming.mapper.CastMapper
import com.emamagic.moviestreaming.network.api.MovieApi
import com.emamagic.moviestreaming.network.response.MovieResponse
import com.emamagic.moviestreaming.util.helper.safe.*
import com.emamagic.moviestreaming.util.helper.safe.error.GeneralErrorHandlerImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MovieRepositoryImpl @Inject constructor(
    private val db: MovieDatabase,
    private val movieApi: MovieApi,
    private val castMapper: CastMapper
) : SafeApi(), MovieRepository {

    private val movieDao = db.movieDao()
    private val castDao = db.castDao()

    override suspend fun getMovieById(id: Long): MovieEntity {
        val detail = getMovieDetail(id)
        return if (detail.succeeded) {
            val movie = detail.data?.movie?.firstOrNull()
            db.withTransaction {
                updateDescription(
                    id = id,
                    description = movie?.description,
                    imageVideoLink = movie?.imageVideoLink,
                    videoLink = movie?.videoLink
                )
                movieDao.getMovieById(id)
            }
        } else movieDao.getMovieById(id)


    }

    override fun getCastsById(id: Long): Flow<ResultWrapper<List<CastEntity>>> {
        return networkBoundResource(
            errorHandler = this,
            databaseQuery = { castDao.getCastsById(id) },
            networkCall = { movieApi.getCasts(id) },
            saveCallResult = { castDao.upsert(castMapper.mapFromEntityList(movieApi.getCasts(id).casts)) }
        )
    }


    private suspend fun updateDescription(id: Long, description: String? ,imageVideoLink: String? ,videoLink: String?) {
        movieDao.updateMovieDetail(id = id, description = description ,imageVideoLink = imageVideoLink, videoLink = videoLink)
    }

    private suspend fun getMovieDetail(id: Long): ResultWrapper<MovieResponse> {
        return safe { movieApi.getDetailMovie(id) }
    }

}
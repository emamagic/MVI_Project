package com.emamagic.moviestreaming.data.repository.movie

import androidx.room.withTransaction
import com.emamagic.moviestreaming.data.db.MovieDatabase
import com.emamagic.moviestreaming.data.db.entity.CastEntity
import com.emamagic.moviestreaming.data.db.entity.MovieEntity
import com.emamagic.moviestreaming.data.db.entity.SeasonEntity
import com.emamagic.moviestreaming.data.network.api.MovieApi
import com.emamagic.moviestreaming.data.network.response.MovieResponse
import com.emamagic.moviestreaming.ui.base.upsert
import com.emamagic.moviestreaming.provider.mapper.CastMapper
import com.emamagic.moviestreaming.provider.mapper.SeasonMapper
import com.emamagic.moviestreaming.provider.safe.ResultWrapper
import com.emamagic.moviestreaming.provider.safe.SafeApi
import com.emamagic.moviestreaming.provider.safe.bound.networkBoundResource
import com.emamagic.moviestreaming.provider.safe.succeeded
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MovieRepositoryImpl @Inject constructor(
    private val db: MovieDatabase,
    private val movieApi: MovieApi,
    private val castMapper: CastMapper,
    private val seasonMapper: SeasonMapper
) : SafeApi(), MovieRepository {

    private val movieDao = db.movieDao()
    private val castDao = db.castDao()
    private val seasonDao = db.seasonDao()

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

    override fun getSeasonById(id: Long): Flow<ResultWrapper<List<SeasonEntity>>> {
        return networkBoundResource(
            errorHandler = this,
            databaseQuery = { seasonDao.getSeasonById(id) },
            networkCall = { movieApi.getSeasons(id) },
            saveCallResult = { seasonDao.upsert(seasonMapper.mapToEntityList(movieApi.getSeasons(id).seasons)) }
        )
    }


    private suspend fun updateDescription(id: Long, description: String? ,imageVideoLink: String? ,videoLink: String?) {
        movieDao.updateMovieDetailById(id = id, description = description ,imageVideoLink = imageVideoLink, videoLink = videoLink)
    }

    private suspend fun getMovieDetail(id: Long): ResultWrapper<MovieResponse> {
        return safe { movieApi.getDetailMovie(id) }
    }

}
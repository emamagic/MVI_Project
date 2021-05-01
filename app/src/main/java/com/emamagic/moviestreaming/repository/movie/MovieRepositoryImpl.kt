package com.emamagic.moviestreaming.repository.movie

import androidx.room.withTransaction
import com.emamagic.moviestreaming.db.MovieDatabase
import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.emamagic.moviestreaming.network.api.MovieApi
import com.emamagic.moviestreaming.network.response.MovieResponse
import com.emamagic.moviestreaming.util.helper.safe.ResultWrapper
import com.emamagic.moviestreaming.util.helper.safe.error.GeneralErrorHandlerImpl
import com.emamagic.moviestreaming.util.helper.safe.succeeded
import com.emamagic.moviestreaming.util.helper.safe.toResult
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val db: MovieDatabase,
    private val movieApi: MovieApi
) : GeneralErrorHandlerImpl(), MovieRepository {

    private val movieDao = db.movieDao()

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

    override suspend fun updateDescription(id: Long, description: String? ,imageVideoLink: String? ,videoLink: String?) {
        movieDao.updateMovieDetail(id = id, description = description ,imageVideoLink = imageVideoLink, videoLink = videoLink)
    }

    override suspend fun getMovieDetail(id: Long): ResultWrapper<MovieResponse> {
        return try {
            movieApi.getMovie(id).toResult(this)
        } catch (t: Throwable) {
            /** when server con nat hand shacking (SocketException)*/
            ResultWrapper.Failed(getError(t))
        }
    }


}
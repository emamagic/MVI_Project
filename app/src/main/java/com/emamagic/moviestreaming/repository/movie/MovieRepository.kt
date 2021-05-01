package com.emamagic.moviestreaming.repository.movie

import com.emamagic.moviestreaming.db.entity.CastEntity
import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.emamagic.moviestreaming.network.response.MovieResponse
import com.emamagic.moviestreaming.util.helper.safe.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getMovieById(id: Long): MovieEntity

    suspend fun updateDescription(id: Long ,description: String? ,imageVideoLink: String? ,videoLink: String?)

    suspend fun getMovieDetail(id: Long): ResultWrapper<MovieResponse>

    fun getCastsById(id: Long): Flow<ResultWrapper<List<CastEntity>>>

}
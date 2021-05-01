package com.emamagic.moviestreaming.repository.movie

import com.emamagic.moviestreaming.db.entity.CastEntity
import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.emamagic.moviestreaming.db.entity.SeasonEntity
import com.emamagic.moviestreaming.network.response.MovieResponse
import com.emamagic.moviestreaming.util.helper.safe.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getMovieById(id: Long): MovieEntity

    fun getCastsById(id: Long): Flow<ResultWrapper<List<CastEntity>>>

    fun getSeasonById(id: Long): Flow<ResultWrapper<List<SeasonEntity>>>

}
package com.emamagic.moviestreaming.data.repository.movie

import com.emamagic.moviestreaming.data.db.entity.CastEntity
import com.emamagic.moviestreaming.data.db.entity.MovieEntity
import com.emamagic.moviestreaming.data.db.entity.SeasonEntity
import com.emamagic.moviestreaming.provider.safe.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getMovieById(id: Long): MovieEntity

    fun getCastsById(id: Long): Flow<ResultWrapper<List<CastEntity>>>

    fun getSeasonById(id: Long): Flow<ResultWrapper<List<SeasonEntity>>>

}
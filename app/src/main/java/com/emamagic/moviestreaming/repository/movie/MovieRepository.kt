package com.emamagic.moviestreaming.repository.movie

import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.emamagic.moviestreaming.util.helper.safe.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getAllMovie(category: String): Flow<ResultWrapper<List<MovieEntity>>>
}
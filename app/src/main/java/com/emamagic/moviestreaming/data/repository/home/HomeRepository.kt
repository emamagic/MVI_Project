package com.emamagic.moviestreaming.data.repository.home

import com.emamagic.moviestreaming.data.db.entity.GenreEntity
import com.emamagic.moviestreaming.data.db.entity.MovieEntity
import com.emamagic.moviestreaming.data.db.entity.SliderEntity
import com.emamagic.moviestreaming.provider.safe.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun getSliders(): Flow<ResultWrapper<List<SliderEntity>>>

    fun getMoviesByCategory(category: String): Flow<ResultWrapper<List<MovieEntity>>>

    fun getGenre(): Flow<ResultWrapper<List<GenreEntity>>>

    fun enableRefreshMode()

    fun disableRefreshModel()

}
package com.emamagic.moviestreaming.repository.home

import com.emamagic.moviestreaming.db.entity.GenreEntity
import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.emamagic.moviestreaming.db.entity.SliderEntity
import com.emamagic.moviestreaming.safe.ResultWrapper
import com.emamagic.moviestreaming.util.Resource
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun getSliders(): Flow<Resource<List<SliderEntity>>>

    fun getMovies(category: String): Flow<ResultWrapper<List<MovieEntity>>>

    fun getGenre(): Flow<ResultWrapper<List<GenreEntity>>>

}
package com.emamagic.moviestreaming.repository.home

import com.emamagic.moviestreaming.db.entity.GenreEntity
import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.emamagic.moviestreaming.db.entity.SliderEntity
import com.emamagic.moviestreaming.util.helper.safe.Resource
import com.emamagic.moviestreaming.util.helper.safe.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun getSliders(): Flow<ResultWrapper<List<SliderEntity>>>

    fun getMovies(category: String): Flow<Resource<List<MovieEntity>>>

    fun getGenre(): Flow<Resource<List<GenreEntity>>>

}
package com.emamagic.moviestreaming.repository.home

import com.emamagic.moviestreaming.base.upsert
import com.emamagic.moviestreaming.db.dao.GenreDao
import com.emamagic.moviestreaming.db.dao.MovieDao
import com.emamagic.moviestreaming.db.dao.SliderDao
import com.emamagic.moviestreaming.db.entity.GenreEntity
import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.emamagic.moviestreaming.db.entity.SliderEntity
import com.emamagic.moviestreaming.network.HomeApi
import com.emamagic.moviestreaming.network.dto.GenreDto
import com.emamagic.moviestreaming.network.dto.MovieDto
import com.emamagic.moviestreaming.network.dto.SliderDto
import com.emamagic.moviestreaming.safe.GeneralErrorHandlerImpl
import com.emamagic.moviestreaming.safe.ResultWrapper
import com.emamagic.moviestreaming.safe.toResult
import com.emamagic.moviestreaming.util.performOperation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeApi: HomeApi,
    private val sliderDao: SliderDao,
    private val sliderDto: SliderDto,
    private val movieDao: MovieDao,
    private val movieDto: MovieDto,
    private val genreDao: GenreDao,
    private val genreDto: GenreDto
) : GeneralErrorHandlerImpl(), HomeRepository {


    override fun getSliders(): Flow<ResultWrapper<List<SliderEntity>>> {
        return performOperation(
            databaseQuery = { sliderDao.getAllSlider().toResult(this) },
            networkCall = { homeApi.getSliders().toResult(this) },
            saveCallResult = { sliderDao.upsert(sliderDto.mapFromEntityList(it.sliders)) }
        )
    }

    override fun getMovies(category: String): Flow<ResultWrapper<List<MovieEntity>>> {
        return performOperation(
            databaseQuery = { movieDao.getMovie(category).toResult(this) },
            networkCall = { homeApi.getMovies(category).toResult(this) },
            saveCallResult = { movieDao.upsert(movieDto.mapFromEntityList(it.movie_streaming)) }
        )
    }

    override fun getGenre(): Flow<ResultWrapper<List<GenreEntity>>> {
        return performOperation(
            databaseQuery = { genreDao.getGenre().toResult(this) },
            networkCall = { homeApi.getGenre().toResult(this) },
            saveCallResult = { genreDao.upsert(genreDto.mapFromEntityList(it.genres)) }
        )
    }


}
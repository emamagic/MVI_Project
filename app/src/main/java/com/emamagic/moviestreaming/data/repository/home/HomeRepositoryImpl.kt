package com.emamagic.moviestreaming.data.repository.home

import com.emamagic.moviestreaming.data.db.dao.GenreDao
import com.emamagic.moviestreaming.data.db.dao.MovieDao
import com.emamagic.moviestreaming.data.db.dao.SliderDao
import com.emamagic.moviestreaming.data.db.entity.GenreEntity
import com.emamagic.moviestreaming.data.db.entity.MovieEntity
import com.emamagic.moviestreaming.data.db.entity.SliderEntity
import com.emamagic.moviestreaming.data.network.api.HomeApi
import com.emamagic.moviestreaming.ui.base.upsert
import com.emamagic.moviestreaming.provider.mapper.GenreMapper
import com.emamagic.moviestreaming.provider.mapper.MovieMapper
import com.emamagic.moviestreaming.provider.mapper.SliderMapper
import com.emamagic.moviestreaming.provider.safe.ResultWrapper
import com.emamagic.moviestreaming.provider.safe.SafeApi
import com.emamagic.moviestreaming.provider.safe.bound.networkBoundResource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@ExperimentalCoroutinesApi
class HomeRepositoryImpl @Inject constructor(
    private val homeApi: HomeApi,
    private val sliderDao: SliderDao,
    private val sliderMapper: SliderMapper,
    private val movieDao: MovieDao,
    private val movieMapper: MovieMapper,
    private val genreDao: GenreDao,
    private val genreMapper: GenreMapper
) : SafeApi(), HomeRepository {

    private var isRefreshing: Boolean = false

    override fun getSliders(): Flow<ResultWrapper<List<SliderEntity>>> {
        return networkBoundResource(
            errorHandler = this,
            databaseQuery = { sliderDao.getAllSlider() },
            networkCall = {  homeApi.getSliders()  },
            saveCallResult = { sliderDao.upsert(sliderMapper.mapFromEntityList(it.sliders)) },
            shouldFetch = { cachedMovies ->
                if (isRefreshing) true
                else {
                    val sortedMovies = cachedMovies.sortedBy { movie ->
                        movie.updatedAt
                    }
                    val oldestTimestamp = sortedMovies.firstOrNull()?.updatedAt
                    val needsRefresh = oldestTimestamp == null || oldestTimestamp < System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(60)
                    needsRefresh
                }

            }
        )
    }

    override fun getMoviesByCategory(category: String): Flow<ResultWrapper<List<MovieEntity>>> {
        return networkBoundResource(
            errorHandler = this,
            databaseQuery = { movieDao.getMoviesByCategoryLimit6(category) },
            networkCall = { homeApi.getMoviesByCategory(category) },
            saveCallResult = { movieDao.upsert(movieMapper.mapFromEntityList(it.movies)) },
            shouldFetch = { cachedMovies ->
                Timber.e("$isRefreshing")
                if (isRefreshing) true
                else {
                    val sortedMovies = cachedMovies.sortedBy { movie ->
                        movie.updatedAt
                    }
                    val oldestTimestamp = sortedMovies.firstOrNull()?.updatedAt
                    val needsRefresh = oldestTimestamp == null || oldestTimestamp < System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(5)
                    needsRefresh
                }

            }
        )
    }

    override fun getGenre(): Flow<ResultWrapper<List<GenreEntity>>> {
        return networkBoundResource(
            errorHandler = this,
            databaseQuery = { genreDao.getGenre() },
            networkCall = { homeApi.getGenre() },
            saveCallResult = { genreDao.upsert(genreMapper.mapFromEntityList(it.genres)) },
            shouldFetch = { cachedMovies ->
                if (isRefreshing) true
                else {
                    val sortedMovies = cachedMovies.sortedBy { movie ->
                        movie.updatedAt
                    }
                    val oldestTimestamp = sortedMovies.firstOrNull()?.updatedAt
                    val needsRefresh = oldestTimestamp == null || oldestTimestamp < System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(5)
                    needsRefresh
                }
            }
        )
    }

    override fun enableRefreshMode() { isRefreshing = true }

    override fun disableRefreshModel() { isRefreshing = false }


}
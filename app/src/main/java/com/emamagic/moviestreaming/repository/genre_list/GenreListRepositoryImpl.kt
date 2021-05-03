package com.emamagic.moviestreaming.repository.genre_list

import com.emamagic.moviestreaming.base.insertOrDelete
import com.emamagic.moviestreaming.base.upsert
import com.emamagic.moviestreaming.db.dao.FavoriteDao
import com.emamagic.moviestreaming.db.dao.MovieDao
import com.emamagic.moviestreaming.db.entity.FavoriteEntity
import com.emamagic.moviestreaming.db.entity.MovieWithFavorite
import com.emamagic.moviestreaming.mapper.MovieMapper
import com.emamagic.moviestreaming.network.api.GenreApi
import com.emamagic.moviestreaming.util.helper.safe.ResultWrapper
import com.emamagic.moviestreaming.util.helper.safe.SafeApi
import com.emamagic.moviestreaming.util.helper.safe.networkBoundResource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class GenreListRepositoryImpl @Inject constructor(
    private val genreApi: GenreApi,
    private val movieDao: MovieDao,
    private val movieMapper: MovieMapper,
    private val favoriteDao: FavoriteDao
): SafeApi() ,GenreListRepository {


    override fun getGenreByCategory(genreName: String): Flow<ResultWrapper<List<MovieWithFavorite>>> {
        return networkBoundResource(
            errorHandler = this,
            databaseQuery = { movieDao.getMoviesByGenre(genreName) },
            networkCall = { genreApi.getGenreByCategory(genreName) },
            saveCallResult = { movieDao.upsert(movieMapper.mapFromEntityList(genreApi.getGenreByCategory(genreName).movies)) }
        )
    }

    override suspend fun updateFavoriteById(item: FavoriteEntity) {
        favoriteDao.insertOrDelete(item)
    }


}
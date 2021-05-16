package com.emamagic.moviestreaming.data.repository.favorite

import com.emamagic.moviestreaming.data.db.dao.MovieDao
import com.emamagic.moviestreaming.data.db.entity.MovieEntity
import com.emamagic.moviestreaming.provider.safe.SafeApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val movieDao: MovieDao
): SafeApi() ,FavoriteRepository {

    override fun getFavoriteMovies(): Flow<List<MovieEntity>> {
        return movieDao.getFavoriteMovie()
    }


}
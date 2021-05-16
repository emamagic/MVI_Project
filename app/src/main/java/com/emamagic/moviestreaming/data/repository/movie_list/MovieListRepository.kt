package com.emamagic.moviestreaming.data.repository.movie_list

import com.emamagic.moviestreaming.data.db.entity.FavoriteEntity
import com.emamagic.moviestreaming.data.db.entity.MovieWithFavorite
import com.emamagic.moviestreaming.provider.safe.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface MovieListRepository {

    fun getAllMovie(category: String): Flow<ResultWrapper<List<MovieWithFavorite>>>

    suspend fun updateFavoriteById(item: FavoriteEntity)
}
package com.emamagic.moviestreaming.repository.genre_list

import com.emamagic.moviestreaming.db.entity.FavoriteEntity
import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.emamagic.moviestreaming.db.entity.MovieWithFavorite
import com.emamagic.moviestreaming.util.helper.safe.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface GenreListRepository {

    fun getGenreByCategory(genreName: String): Flow<ResultWrapper<List<MovieWithFavorite>>>

    suspend fun updateFavoriteById(item: FavoriteEntity)
}
package com.emamagic.moviestreaming.data.repository.genre_list

import com.emamagic.moviestreaming.data.db.entity.FavoriteEntity
import com.emamagic.moviestreaming.data.db.entity.MovieWithFavorite
import com.emamagic.moviestreaming.provider.safe.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface GenreListRepository {

    fun getGenreByCategory(genreName: String): Flow<ResultWrapper<List<MovieWithFavorite>>>

    suspend fun updateFavoriteById(item: FavoriteEntity)
}
package com.emamagic.moviestreaming.data.repository.favorite

import com.emamagic.moviestreaming.data.db.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    fun getFavoriteMovies(): Flow<List<MovieEntity>>
}
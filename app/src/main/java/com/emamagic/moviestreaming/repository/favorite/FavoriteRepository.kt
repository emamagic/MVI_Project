package com.emamagic.moviestreaming.repository.favorite

import com.emamagic.moviestreaming.db.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    fun getFavoriteMovies(): Flow<List<MovieEntity>>
}
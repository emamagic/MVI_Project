package com.emamagic.moviestreaming.repository.movie

import com.emamagic.moviestreaming.db.entity.MovieEntity

interface MovieRepository {

    suspend fun getMovieById(id: Long): MovieEntity

}
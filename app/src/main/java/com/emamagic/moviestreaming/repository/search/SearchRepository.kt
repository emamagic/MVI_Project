package com.emamagic.moviestreaming.repository.search

import com.emamagic.moviestreaming.db.dao.MovieDao
import com.emamagic.moviestreaming.network.response.MovieListResponse
import com.emamagic.moviestreaming.network.response.MovieResponse
import com.emamagic.moviestreaming.util.helper.safe.ResultWrapper

interface SearchRepository {
    suspend fun searchMovies(category: String ,query: String): ResultWrapper<MovieListResponse>
}
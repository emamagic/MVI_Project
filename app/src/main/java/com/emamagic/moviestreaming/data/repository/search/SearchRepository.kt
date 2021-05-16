package com.emamagic.moviestreaming.data.repository.search

import com.emamagic.moviestreaming.data.network.response.MovieListResponse
import com.emamagic.moviestreaming.provider.safe.ResultWrapper

interface SearchRepository {
    suspend fun searchMovies(category: String ,query: String): ResultWrapper<MovieListResponse>
}
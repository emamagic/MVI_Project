package com.emamagic.moviestreaming.repository.search

import com.emamagic.moviestreaming.network.api.SearchApi
import com.emamagic.moviestreaming.network.response.MovieListResponse
import com.emamagic.moviestreaming.network.response.MovieResponse
import com.emamagic.moviestreaming.util.helper.safe.ResultWrapper
import com.emamagic.moviestreaming.util.helper.safe.SafeApi
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchApi: SearchApi
): SafeApi() ,SearchRepository {


    override suspend fun searchMovies(category: String, query: String): ResultWrapper<MovieListResponse> {
        return safe { searchApi.searchMovies(category, query) }
    }
}
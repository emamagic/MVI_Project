package com.emamagic.moviestreaming.data.repository.search

import com.emamagic.moviestreaming.data.network.api.SearchApi
import com.emamagic.moviestreaming.data.network.response.MovieListResponse
import com.emamagic.moviestreaming.provider.safe.ResultWrapper
import com.emamagic.moviestreaming.provider.safe.SafeApi
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchApi: SearchApi
): SafeApi() ,SearchRepository {


    override suspend fun searchMovies(category: String, query: String): ResultWrapper<MovieListResponse> {
        return safe { searchApi.searchMovies(category, query) }
    }
}
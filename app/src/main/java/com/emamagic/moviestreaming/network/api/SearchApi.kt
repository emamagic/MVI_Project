package com.emamagic.moviestreaming.network.api

import com.emamagic.moviestreaming.network.response.MovieListResponse
import com.emamagic.moviestreaming.network.response.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("getSearch.php")
    suspend fun searchMovies(
        @Query("category_name") category: String,
        @Query("name") query: String
    ): Response<MovieListResponse>
}
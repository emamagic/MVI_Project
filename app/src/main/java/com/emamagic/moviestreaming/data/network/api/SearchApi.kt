package com.emamagic.moviestreaming.data.network.api

import com.emamagic.moviestreaming.data.network.response.MovieListResponse
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
package com.emamagic.moviestreaming.network.api

import com.emamagic.moviestreaming.network.response.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("getAllInformationHome.php")
    suspend fun getAllMovie(
        @Query("category_name") category: String
    ): MovieListResponse

}
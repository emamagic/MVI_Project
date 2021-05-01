package com.emamagic.moviestreaming.network.api

import com.emamagic.moviestreaming.network.response.MovieListResponse
import com.emamagic.moviestreaming.network.response.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("getAllInforamtionHome.php")
    suspend fun getAllMovie(
        @Query("category_name") category: String
    ): MovieListResponse

    @GET("show_detail.php")
    suspend fun getMovie(
        @Query("id_item") id: Long
    ): Response<MovieResponse>

}
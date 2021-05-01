package com.emamagic.moviestreaming.network.api

import com.emamagic.moviestreaming.network.response.CastResponse
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
    suspend fun getDetailMovie(
        @Query("id_item") itemId: Long
    ): Response<MovieResponse>

    @GET("getCast.php")
    suspend fun getCasts(
        @Query("id_item") itemId: Long
    ): CastResponse

}
package com.emamagic.moviestreaming.data.network.api

import com.emamagic.moviestreaming.data.network.response.*
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

    @GET("getSeasons.php")
    suspend fun getSeasons(
        @Query("id_item") itemId: Long
    ): SeasonResponse

    @GET("getEpisodes.php")
    suspend fun getEpisodes(
        @Query("id_season") seasonId: Long
    ): EpisodeResponse

}
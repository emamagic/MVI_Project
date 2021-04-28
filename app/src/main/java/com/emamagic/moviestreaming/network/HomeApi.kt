package com.emamagic.moviestreaming.network

import com.emamagic.moviestreaming.network.response.GenreListResponse
import com.emamagic.moviestreaming.network.response.MovieListResponse
import com.emamagic.moviestreaming.network.response.SliderListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApi {


    @GET("getSlider.php")
    suspend fun getSliders(): Response<SliderListResponse>

    @GET("getInformationHome.php")
    suspend fun getMovies(
        @Query("category_name") category: String
    ): Response<MovieListResponse>

    @GET("getGenre.php")
    suspend fun getGenre(): Response<GenreListResponse>

}
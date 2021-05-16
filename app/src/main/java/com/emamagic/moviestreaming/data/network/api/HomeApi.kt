package com.emamagic.moviestreaming.data.network.api

import com.emamagic.moviestreaming.data.network.response.GenreListResponse
import com.emamagic.moviestreaming.data.network.response.MovieListResponse
import com.emamagic.moviestreaming.data.network.response.SliderListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApi {


    @GET("getSlider.php")
    suspend fun getSliders(): SliderListResponse

    @GET("getInformationHome.php")
    suspend fun getMoviesByCategory(
        @Query("category_name") category: String
    ): MovieListResponse

    @GET("getGenre.php")
    suspend fun getGenre(): GenreListResponse

}
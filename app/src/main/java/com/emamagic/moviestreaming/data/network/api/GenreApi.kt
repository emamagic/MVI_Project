package com.emamagic.moviestreaming.data.network.api

import com.emamagic.moviestreaming.data.network.response.GenreListResponse
import com.emamagic.moviestreaming.data.network.response.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GenreApi {

    @GET("GetAllGenre.php")
    suspend fun getAllGenre(): GenreListResponse

    @GET("get_show_genre.php")
    suspend fun getGenreByCategory(
        @Query("genre_name") genreName: String
    ): MovieListResponse

}
package com.emamagic.moviestreaming.network.api

import com.emamagic.moviestreaming.network.response.GenreListResponse
import retrofit2.Response
import retrofit2.http.GET

interface GenreApi {

    @GET("GetAllGenre.php")
    suspend fun getAllGenre(): GenreListResponse

}
package com.emamagic.moviestreaming.network

import com.emamagic.moviestreaming.network.response.SliderListResponse
import retrofit2.Response
import retrofit2.http.GET

interface HomeApi {


    @GET("MovieSteaming/getSlider.php")
    suspend fun getSliders(): Response<SliderListResponse>

}
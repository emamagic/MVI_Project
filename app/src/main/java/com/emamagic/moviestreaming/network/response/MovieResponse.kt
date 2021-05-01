package com.emamagic.moviestreaming.network.response

import com.emamagic.moviestreaming.network.dto.MovieDetailDto
import com.emamagic.moviestreaming.network.dto.MovieDto
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("detail")
    val movie: List<MovieDetailDto>
)
package com.emamagic.moviestreaming.data.network.response

import com.emamagic.moviestreaming.data.network.dto.MovieDetailDto
import com.emamagic.moviestreaming.data.network.dto.MovieDto
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("detail")
    val movie: List<MovieDetailDto>
)
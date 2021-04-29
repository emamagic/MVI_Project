package com.emamagic.moviestreaming.network.response

import com.emamagic.moviestreaming.network.dto.MovieDto
import com.google.gson.annotations.SerializedName

data class MovieListResponse (
    @SerializedName("movie_streaming")
    val movies: List<MovieDto>
)



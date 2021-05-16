package com.emamagic.moviestreaming.data.network.response

import com.emamagic.moviestreaming.data.network.dto.MovieDto
import com.google.gson.annotations.SerializedName

data class MovieListResponse (
    @SerializedName("movie_streaming")
    val movies: List<MovieDto>
)



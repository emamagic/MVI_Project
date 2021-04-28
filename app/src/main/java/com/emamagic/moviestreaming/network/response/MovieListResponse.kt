package com.emamagic.moviestreaming.network.response

data class MovieListResponse (
    val movie_streaming: List<MovieResponse>
)

data class MovieResponse(
    val id: Long,
    val name: String,
    val link_img: String,
    val time: String,
    val category_name: String,
    val rank: String,
    val rate_imdb: String,
    val published: String,
    val director: String
)

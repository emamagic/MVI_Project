package com.emamagic.moviestreaming.network.response

data class GenreListResponse(
    val genres: List<GenreResponse>
)

data class GenreResponse(
    val id: Long,
    val name: String,
    val link_img: String
)
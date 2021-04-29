package com.emamagic.moviestreaming.network.response

import com.emamagic.moviestreaming.network.dto.GenreDto
import com.google.gson.annotations.SerializedName


data class GenreListResponse(
    @SerializedName("genres")
    val genres: List<GenreDto>
)


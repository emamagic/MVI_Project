package com.emamagic.moviestreaming.data.network.response

import com.emamagic.moviestreaming.data.network.dto.GenreDto
import com.google.gson.annotations.SerializedName


data class GenreListResponse(
    @SerializedName("genres")
    val genres: List<GenreDto>
)


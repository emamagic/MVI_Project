package com.emamagic.moviestreaming.network.response

import com.emamagic.moviestreaming.network.dto.SeasonDto
import com.google.gson.annotations.SerializedName

data class SeasonResponse(
    @SerializedName("seasons")
    val seasons: List<SeasonDto>
)
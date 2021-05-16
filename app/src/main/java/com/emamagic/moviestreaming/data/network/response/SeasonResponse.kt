package com.emamagic.moviestreaming.data.network.response

import com.emamagic.moviestreaming.data.network.dto.SeasonDto
import com.google.gson.annotations.SerializedName

data class SeasonResponse(
    @SerializedName("seasons")
    val seasons: List<SeasonDto>
)
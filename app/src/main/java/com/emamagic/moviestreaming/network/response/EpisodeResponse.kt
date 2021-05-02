package com.emamagic.moviestreaming.network.response

import com.emamagic.moviestreaming.network.dto.EpisodeDto
import com.google.gson.annotations.SerializedName

data class EpisodeResponse(
    @SerializedName("episodes")
    val episodes: List<EpisodeDto>
)
package com.emamagic.moviestreaming.data.network.response

import com.emamagic.moviestreaming.data.network.dto.EpisodeDto
import com.google.gson.annotations.SerializedName

data class EpisodeResponse(
    @SerializedName("episodes")
    val episodes: List<EpisodeDto>
)
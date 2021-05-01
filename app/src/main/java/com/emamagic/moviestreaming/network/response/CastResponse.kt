package com.emamagic.moviestreaming.network.response

import com.emamagic.moviestreaming.network.dto.CastDto
import com.google.gson.annotations.SerializedName

data class CastResponse(
    @SerializedName("casts")
    val casts: List<CastDto>
)
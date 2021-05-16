package com.emamagic.moviestreaming.data.network.response

import com.emamagic.moviestreaming.data.network.dto.CastDto
import com.google.gson.annotations.SerializedName

data class CastResponse(
    @SerializedName("casts")
    val casts: List<CastDto>
)
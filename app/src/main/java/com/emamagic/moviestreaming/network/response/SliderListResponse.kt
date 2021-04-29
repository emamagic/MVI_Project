package com.emamagic.moviestreaming.network.response

import com.emamagic.moviestreaming.network.dto.SliderDto
import com.google.gson.annotations.SerializedName


data class SliderListResponse(
    @SerializedName("sliders")
    val sliders: List<SliderDto>
)

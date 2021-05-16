package com.emamagic.moviestreaming.data.network.response

import com.emamagic.moviestreaming.data.network.dto.SliderDto
import com.google.gson.annotations.SerializedName


data class SliderListResponse(
    @SerializedName("sliders")
    val sliders: List<SliderDto>
)

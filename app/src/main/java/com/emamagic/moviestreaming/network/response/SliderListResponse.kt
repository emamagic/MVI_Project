package com.emamagic.moviestreaming.network.response


data class SliderListResponse(
    val sliders: List<SliderResponse>
)

data class SliderResponse(
    val id: Long,
    val name: String,
    val link_img: String,
    val time: String,
    val published: String
)
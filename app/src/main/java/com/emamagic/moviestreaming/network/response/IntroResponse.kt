package com.emamagic.moviestreaming.network.response

import com.emamagic.moviestreaming.network.dto.IntroDto

data class IntroResponse(
    val intros: List<IntroDto>
)
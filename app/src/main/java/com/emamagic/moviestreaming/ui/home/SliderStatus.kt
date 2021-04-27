package com.emamagic.moviestreaming.ui.home

import com.emamagic.moviestreaming.db.entity.SliderEntity

sealed class SliderStatus {
    object EmptyList: SliderStatus()
    data class Loading(val isLoading: Boolean) : SliderStatus()
    data class FetchList(val sliders: List<SliderEntity>): SliderStatus()
}
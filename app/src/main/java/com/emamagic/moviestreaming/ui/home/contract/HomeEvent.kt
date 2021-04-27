package com.emamagic.moviestreaming.ui.home.contract

import com.emamagic.moviestreaming.base.BaseEvent

sealed class HomeEvent: BaseEvent {
    object GetSlider: HomeEvent()
}
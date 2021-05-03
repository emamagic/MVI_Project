package com.emamagic.moviestreaming.ui.intro.splash.contract

import com.emamagic.moviestreaming.base.BaseEvent

sealed class SplashEvent: BaseEvent {
    object TimeFinished: SplashEvent()
}
package com.emamagic.moviestreaming.ui.modules.intro.splash.contract

import com.emamagic.moviestreaming.ui.base.BaseEvent

sealed class SplashEvent: BaseEvent {
    object TimeFinished: SplashEvent()
}
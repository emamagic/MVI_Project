package com.emamagic.moviestreaming.ui.auth.register.contract

import com.emamagic.moviestreaming.base.BaseEvent
import com.emamagic.moviestreaming.network.request.RegisterRequest

sealed class RegisterEvent: BaseEvent {
    data class RegisterClicked(val request: RegisterRequest): RegisterEvent()
}

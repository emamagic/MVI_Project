package com.emamagic.moviestreaming.ui.modules.auth.register.contract

import com.emamagic.moviestreaming.data.network.request.RegisterRequest
import com.emamagic.moviestreaming.ui.base.BaseEvent

sealed class RegisterEvent: BaseEvent {
    data class RegisterClicked(val request: RegisterRequest): RegisterEvent()
}

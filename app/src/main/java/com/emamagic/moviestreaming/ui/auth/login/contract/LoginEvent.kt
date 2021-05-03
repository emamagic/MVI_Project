package com.emamagic.moviestreaming.ui.auth.login.contract

import com.emamagic.moviestreaming.base.BaseEvent
import com.emamagic.moviestreaming.network.request.LoginRequest

sealed class LoginEvent: BaseEvent {
    object RegisterClicked: LoginEvent()
    data class LoginClicked(val request: LoginRequest): LoginEvent()
}
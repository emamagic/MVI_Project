package com.emamagic.moviestreaming.ui.modules.auth.login.contract

import com.emamagic.moviestreaming.data.network.request.LoginRequest
import com.emamagic.moviestreaming.ui.base.BaseEvent

sealed class LoginEvent: BaseEvent {
    object RegisterClicked: LoginEvent()
    data class LoginClicked(val request: LoginRequest): LoginEvent()
    object CheckLogin: LoginEvent()
    data class CheckRememberChanged(val isChecked: Boolean): LoginEvent()
}
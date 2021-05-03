package com.emamagic.moviestreaming.ui.auth.login

import com.emamagic.moviestreaming.base.BaseViewModel
import com.emamagic.moviestreaming.ui.auth.login.contract.LoginEffect
import com.emamagic.moviestreaming.ui.auth.login.contract.LoginEvent
import com.emamagic.moviestreaming.ui.auth.login.contract.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(

): BaseViewModel<LoginState , LoginEffect ,LoginEvent>() {


    override fun createInitialState() = LoginState()

    override fun handleEvent(event: LoginEvent) {
        TODO("Not yet implemented")
    }
}
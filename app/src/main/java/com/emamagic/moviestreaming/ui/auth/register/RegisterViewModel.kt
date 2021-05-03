package com.emamagic.moviestreaming.ui.auth.register

import com.emamagic.moviestreaming.base.BaseViewModel
import com.emamagic.moviestreaming.ui.auth.register.contract.RegisterEffect
import com.emamagic.moviestreaming.ui.auth.register.contract.RegisterEvent
import com.emamagic.moviestreaming.ui.auth.register.contract.RegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(

): BaseViewModel<RegisterState ,RegisterEffect ,RegisterEvent>() {

    override fun createInitialState() = RegisterState()

    override fun handleEvent(event: RegisterEvent) {
        TODO("Not yet implemented")
    }
}
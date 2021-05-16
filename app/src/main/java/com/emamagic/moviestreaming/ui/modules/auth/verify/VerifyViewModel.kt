package com.emamagic.moviestreaming.ui.modules.auth.verify

import com.emamagic.moviestreaming.data.repository.auth.verify.VerifyRepository
import com.emamagic.moviestreaming.ui.base.BaseViewModel
import com.emamagic.moviestreaming.ui.base.CommonEffect
import com.emamagic.moviestreaming.ui.modules.auth.verify.contract.VerifyEvent
import com.emamagic.moviestreaming.ui.modules.auth.verify.contract.VerifyState
import com.emamagic.moviestreaming.util.exhaustive
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VerifyViewModel @Inject constructor(
    private val repository: VerifyRepository
): BaseViewModel<VerifyState, CommonEffect, VerifyEvent>() {

    override fun createInitialState() = VerifyState()

    override fun handleEvent(event: VerifyEvent) {
        when(event) {
            else -> {}
        }.exhaustive
    }
}
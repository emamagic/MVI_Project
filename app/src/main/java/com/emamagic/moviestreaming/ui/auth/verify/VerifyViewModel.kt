package com.emamagic.moviestreaming.ui.auth.verify

import com.emamagic.moviestreaming.base.BaseViewModel
import com.emamagic.moviestreaming.base.CommonEffect
import com.emamagic.moviestreaming.repository.auth.verify.VerifyRepository
import com.emamagic.moviestreaming.ui.auth.verify.contract.VerifyEvent
import com.emamagic.moviestreaming.ui.auth.verify.contract.VerifyState
import com.emamagic.moviestreaming.util.exhaustive
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VerifyViewModel @Inject constructor(
    private val repository: VerifyRepository
): BaseViewModel<VerifyState , CommonEffect,VerifyEvent>() {

    override fun createInitialState() = VerifyState()

    override fun handleEvent(event: VerifyEvent) {
        when(event) {
            else -> {}
        }.exhaustive
    }
}
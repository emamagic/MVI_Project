package com.emamagic.moviestreaming.ui.intro.intro

import com.emamagic.moviestreaming.base.BaseViewModel
import com.emamagic.moviestreaming.base.CommonEffect
import com.emamagic.moviestreaming.ui.intro.intro.contract.IntroEvent
import com.emamagic.moviestreaming.ui.intro.intro.contract.IntroState
import com.emamagic.moviestreaming.util.exhaustive
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(): BaseViewModel<IntroState , CommonEffect,IntroEvent>() {

    override fun createInitialState() = IntroState()

    override fun handleEvent(event: IntroEvent) {
        when(event) {
            else -> {}
        }.exhaustive
    }
}
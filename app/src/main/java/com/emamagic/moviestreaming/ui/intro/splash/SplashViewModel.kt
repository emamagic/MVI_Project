package com.emamagic.moviestreaming.ui.intro.splash

import androidx.lifecycle.viewModelScope
import com.emamagic.moviestreaming.base.BaseViewModel
import com.emamagic.moviestreaming.ui.intro.splash.contract.SplashEffect
import com.emamagic.moviestreaming.ui.intro.splash.contract.SplashEvent
import com.emamagic.moviestreaming.ui.intro.splash.contract.SplashState
import com.emamagic.moviestreaming.util.exhaustive
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(): BaseViewModel<SplashState ,SplashEffect ,SplashEvent>() {

    override fun createInitialState() = SplashState()

    override fun handleEvent(event: SplashEvent) {
        when(event) {
            SplashEvent.TimeFinished -> timeFinished()
        }.exhaustive
    }

    private fun timeFinished() = viewModelScope.launch {
        setEffect { SplashEffect.Navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment()) }
    }
}
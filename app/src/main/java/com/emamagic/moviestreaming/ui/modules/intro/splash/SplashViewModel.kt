package com.emamagic.moviestreaming.ui.modules.intro.splash

import androidx.lifecycle.viewModelScope
import com.emamagic.moviestreaming.ui.base.BaseViewModel
import com.emamagic.moviestreaming.ui.base.CommonEffect
import com.emamagic.moviestreaming.ui.modules.intro.splash.contract.SplashEvent
import com.emamagic.moviestreaming.ui.modules.intro.splash.contract.SplashState
import com.emamagic.moviestreaming.util.ToastyMode
import com.emamagic.moviestreaming.util.exhaustive
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(): BaseViewModel<SplashState, CommonEffect, SplashEvent>() {

    override fun createInitialState() = SplashState()

    override fun handleEvent(event: SplashEvent) {
        when(event) {
            SplashEvent.TimeFinished -> timeFinished()
        }.exhaustive
    }

    override fun showError(errorMessage: String) {
        CommonEffect.ShowToast(errorMessage, ToastyMode.MODE_TOAST_ERROR)
    }

    private fun timeFinished() = viewModelScope.launch {
        setEffect { CommonEffect.Navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment()) }
    }

}
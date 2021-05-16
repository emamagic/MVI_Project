package com.emamagic.moviestreaming.ui.modules.intro.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.emamagic.moviestreaming.ui.base.BaseFragment
import com.emamagic.moviestreaming.ui.base.CommonEffect
import com.emamagic.moviestreaming.databinding.FragmentSplashBinding
import com.emamagic.moviestreaming.ui.modules.intro.splash.contract.SplashEvent
import com.emamagic.moviestreaming.ui.modules.intro.splash.contract.SplashState

class SplashFragment: BaseFragment<FragmentSplashBinding, SplashState, CommonEffect, SplashEvent, SplashViewModel>() {


    override val viewModel: SplashViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSplashBinding.inflate(inflater ,container ,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.setEvent(SplashEvent.TimeFinished)
        },3000)

    }

    override fun renderViewState(viewState: SplashState) {}

}
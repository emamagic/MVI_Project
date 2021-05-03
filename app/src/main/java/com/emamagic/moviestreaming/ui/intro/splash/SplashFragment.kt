package com.emamagic.moviestreaming.ui.intro.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.emamagic.moviestreaming.base.BaseFragment
import com.emamagic.moviestreaming.databinding.FragmentSplashBinding
import com.emamagic.moviestreaming.ui.intro.splash.contract.SplashEffect
import com.emamagic.moviestreaming.ui.intro.splash.contract.SplashEvent
import com.emamagic.moviestreaming.ui.intro.splash.contract.SplashState
import com.emamagic.moviestreaming.util.exhaustive
import com.emamagic.moviestreaming.util.toasty

class SplashFragment: BaseFragment<FragmentSplashBinding ,SplashState ,SplashEffect ,SplashEvent ,SplashViewModel>() {


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

    override fun renderViewEffect(viewEffect: SplashEffect) {
        when(viewEffect) {
            is SplashEffect.Loading -> if (viewEffect.isLoading) showLoading(true) else hideLoading()
            is SplashEffect.Navigate -> findNavController().navigate(viewEffect.navDirections)
            is SplashEffect.ShowToast -> toasty(viewEffect.message ,viewEffect.mode)
        }.exhaustive
    }
}
package com.emamagic.moviestreaming.ui.intro.intro

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.emamagic.moviestreaming.base.BaseFragment
import com.emamagic.moviestreaming.databinding.FragmentIntroBinding
import com.emamagic.moviestreaming.ui.intro.intro.contract.IntroEffect
import com.emamagic.moviestreaming.ui.intro.intro.contract.IntroEvent
import com.emamagic.moviestreaming.ui.intro.intro.contract.IntroState
import com.emamagic.moviestreaming.util.exhaustive
import com.emamagic.moviestreaming.util.toasty
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroFragment: BaseFragment<FragmentIntroBinding ,IntroState ,IntroEffect ,IntroEvent ,IntroViewModel>() {

    override val viewModel: IntroViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentIntroBinding.inflate(inflater ,container ,false)

    override fun renderViewState(viewState: IntroState) {

    }

    override fun renderViewEffect(viewEffect: IntroEffect) {
        when(viewEffect) {
            is IntroEffect.Loading -> if (viewEffect.isLoading) showLoading(true) else hideLoading()
            is IntroEffect.Navigate -> findNavController().navigate(viewEffect.navDirections)
            is IntroEffect.ShowToast -> toasty(viewEffect.message ,viewEffect.mode)
        }.exhaustive
    }
}
package com.emamagic.moviestreaming.ui.modules.intro.intro

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.emamagic.moviestreaming.ui.base.BaseFragment
import com.emamagic.moviestreaming.ui.base.CommonEffect
import com.emamagic.moviestreaming.databinding.FragmentIntroBinding
import com.emamagic.moviestreaming.ui.modules.intro.intro.contract.IntroEvent
import com.emamagic.moviestreaming.ui.modules.intro.intro.contract.IntroState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroFragment: BaseFragment<FragmentIntroBinding, IntroState, CommonEffect, IntroEvent, IntroViewModel>() {

    override val viewModel: IntroViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentIntroBinding.inflate(inflater ,container ,false)

    override fun renderViewState(viewState: IntroState) {

    }

}
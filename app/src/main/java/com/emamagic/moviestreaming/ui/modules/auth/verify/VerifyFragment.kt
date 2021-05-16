package com.emamagic.moviestreaming.ui.modules.auth.verify

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.emamagic.moviestreaming.ui.base.BaseFragment
import com.emamagic.moviestreaming.ui.base.CommonEffect
import com.emamagic.moviestreaming.databinding.FragmentVerifyBinding
import com.emamagic.moviestreaming.ui.modules.auth.verify.contract.VerifyEvent
import com.emamagic.moviestreaming.ui.modules.auth.verify.contract.VerifyState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerifyFragment: BaseFragment<FragmentVerifyBinding, VerifyState, CommonEffect, VerifyEvent, VerifyViewModel>() {

    override val viewModel: VerifyViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentVerifyBinding.inflate(inflater ,container ,false)

    override fun renderViewState(viewState: VerifyState) {

    }

}
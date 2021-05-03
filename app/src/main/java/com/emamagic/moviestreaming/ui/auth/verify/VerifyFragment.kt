package com.emamagic.moviestreaming.ui.auth.verify

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.emamagic.moviestreaming.base.BaseFragment
import com.emamagic.moviestreaming.databinding.FragmentVerifyBinding
import com.emamagic.moviestreaming.ui.auth.verify.contract.VerifyEffect
import com.emamagic.moviestreaming.ui.auth.verify.contract.VerifyEvent
import com.emamagic.moviestreaming.ui.auth.verify.contract.VerifyState
import com.emamagic.moviestreaming.util.exhaustive
import com.emamagic.moviestreaming.util.toasty
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerifyFragment: BaseFragment<FragmentVerifyBinding ,VerifyState ,VerifyEffect ,VerifyEvent ,VerifyViewModel>() {

    override val viewModel: VerifyViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentVerifyBinding.inflate(inflater ,container ,false)

    override fun renderViewState(viewState: VerifyState) {

    }

    override fun renderViewEffect(viewEffect: VerifyEffect) {
        when(viewEffect) {
            is VerifyEffect.Loading -> if (viewEffect.isLoading) showLoading(true) else hideLoading()
            is VerifyEffect.Navigate -> findNavController().navigate(viewEffect.navDirections)
            is VerifyEffect.ShowToast -> toasty(viewEffect.message ,viewEffect.mode)
        }.exhaustive
    }
}
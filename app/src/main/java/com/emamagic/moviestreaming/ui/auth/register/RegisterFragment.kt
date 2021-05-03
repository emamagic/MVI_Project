package com.emamagic.moviestreaming.ui.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.emamagic.moviestreaming.base.BaseFragment
import com.emamagic.moviestreaming.databinding.FragmentRegisterBinding
import com.emamagic.moviestreaming.ui.auth.register.contract.RegisterEffect
import com.emamagic.moviestreaming.ui.auth.register.contract.RegisterEvent
import com.emamagic.moviestreaming.ui.auth.register.contract.RegisterState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment: BaseFragment<FragmentRegisterBinding ,RegisterState ,RegisterEffect ,RegisterEvent ,RegisterViewModel>() {

    override val viewModel: RegisterViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentRegisterBinding.inflate(inflater ,container ,false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun renderViewState(viewState: RegisterState) {
        TODO("Not yet implemented")
    }

    override fun renderViewEffect(viewEffect: RegisterEffect) {
        TODO("Not yet implemented")
    }
}
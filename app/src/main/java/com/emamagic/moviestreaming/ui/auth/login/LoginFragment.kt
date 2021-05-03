package com.emamagic.moviestreaming.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.emamagic.moviestreaming.base.BaseFragment
import com.emamagic.moviestreaming.databinding.FragmentLoginBinding
import com.emamagic.moviestreaming.ui.auth.login.contract.LoginEffect
import com.emamagic.moviestreaming.ui.auth.login.contract.LoginEvent
import com.emamagic.moviestreaming.ui.auth.login.contract.LoginState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment: BaseFragment<FragmentLoginBinding ,LoginState ,LoginEffect ,LoginEvent ,LoginViewModel>() {

    override val viewModel: LoginViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater ,container ,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun renderViewState(viewState: LoginState) {
        TODO("Not yet implemented")
    }

    override fun renderViewEffect(viewEffect: LoginEffect) {
        TODO("Not yet implemented")
    }


}
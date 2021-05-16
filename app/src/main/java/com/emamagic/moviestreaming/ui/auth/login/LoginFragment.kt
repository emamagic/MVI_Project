package com.emamagic.moviestreaming.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.emamagic.moviestreaming.base.BaseFragment
import com.emamagic.moviestreaming.base.CommonEffect
import com.emamagic.moviestreaming.databinding.FragmentLoginBinding
import com.emamagic.moviestreaming.network.request.LoginRequest
import com.emamagic.moviestreaming.ui.auth.login.contract.LoginEvent
import com.emamagic.moviestreaming.ui.auth.login.contract.LoginState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment: BaseFragment<FragmentLoginBinding ,LoginState , CommonEffect,LoginEvent ,LoginViewModel>() {

    override val viewModel: LoginViewModel by viewModels()
    private lateinit var request: LoginRequest

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater ,container ,false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setEvent(LoginEvent.CheckLogin)
        request = LoginRequest()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.checkRemember.setOnCheckedChangeListener { _, isChecked -> viewModel.setEvent(LoginEvent.CheckRememberChanged(isChecked))  }
        binding.txtRegister.setOnClickListener { viewModel.setEvent(LoginEvent.RegisterClicked) }
        binding.btnLogin.setOnClickListener {
            request.email = binding.edtEmail.text.toString().trim()
            request.phone = binding.edtPhone.text.toString().trim()
            request.password = binding.edtPassword.text.toString().trim()
            viewModel.setEvent(LoginEvent.LoginClicked(request))
        }

    }

    override fun renderViewState(viewState: LoginState) {

    }


}
package com.emamagic.moviestreaming.ui.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.emamagic.moviestreaming.base.BaseFragment
import com.emamagic.moviestreaming.databinding.FragmentRegisterBinding
import com.emamagic.moviestreaming.network.request.RegisterRequest
import com.emamagic.moviestreaming.ui.auth.register.contract.RegisterEffect
import com.emamagic.moviestreaming.ui.auth.register.contract.RegisterEvent
import com.emamagic.moviestreaming.ui.auth.register.contract.RegisterState
import com.emamagic.moviestreaming.ui.favorite.contract.FavoriteEffect
import com.emamagic.moviestreaming.util.exhaustive
import com.emamagic.moviestreaming.util.toasty
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class RegisterFragment: BaseFragment<FragmentRegisterBinding ,RegisterState ,RegisterEffect ,RegisterEvent ,RegisterViewModel>() {

    override val viewModel: RegisterViewModel by viewModels()
    private lateinit var request: RegisterRequest

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentRegisterBinding.inflate(inflater ,container ,false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        request = RegisterRequest()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtLogin.setOnClickListener { findNavController().popBackStack() }
        binding.btnRegister.setOnClickListener {
            request.name = binding.edtUsername.text.toString().trim()
            request.email = binding.edtEmail.text.toString().trim()
            request.phone = binding.edtPhone.text.toString().trim()
            request.password = binding.edtPassword.text.toString().trim()
            viewModel.setEvent(RegisterEvent.RegisterClicked(request))
        }


    }

    override fun renderViewState(viewState: RegisterState) {

    }

    override fun renderViewEffect(viewEffect: RegisterEffect) {
        when(viewEffect) {
            is RegisterEffect.ShowToast -> toasty(viewEffect.message ,viewEffect.mode)
            is RegisterEffect.Loading -> if (viewEffect.isLoading) showLoading(true) else hideLoading()
            is RegisterEffect.Navigate -> findNavController().navigate(viewEffect.navDirections)
        }.exhaustive
    }
}
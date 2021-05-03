package com.emamagic.moviestreaming.ui.auth.login

import androidx.lifecycle.viewModelScope
import com.emamagic.moviestreaming.base.BaseViewModel
import com.emamagic.moviestreaming.network.request.LoginRequest
import com.emamagic.moviestreaming.repository.auth.login.LoginRepository
import com.emamagic.moviestreaming.ui.auth.login.contract.LoginEffect
import com.emamagic.moviestreaming.ui.auth.login.contract.LoginEvent
import com.emamagic.moviestreaming.ui.auth.login.contract.LoginState
import com.emamagic.moviestreaming.ui.auth.register.contract.RegisterEffect
import com.emamagic.moviestreaming.util.ToastyMode
import com.emamagic.moviestreaming.util.exhaustive
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
): BaseViewModel<LoginState , LoginEffect ,LoginEvent>() {


    override fun createInitialState() = LoginState()

    override fun handleEvent(event: LoginEvent) {
        when(event){
            LoginEvent.RegisterClicked -> registerClicked()
            is LoginEvent.LoginClicked -> loginClicked(event.request)
        }.exhaustive
    }

    private fun registerClicked() = viewModelScope.launch {
        setEffect { LoginEffect.Navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment()) }
    }

    private fun loginClicked(request: LoginRequest) = viewModelScope.launch {
        if (request.email.isNotEmpty() && request.phone.isNotEmpty() && request.password.isNotEmpty()){
            setEffect { LoginEffect.Loading(isLoading = true) }
            when(val response = repository.login(request)) {
                "Login Ok" -> setEffect { LoginEffect.Navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment()) }
                else -> setEffect { LoginEffect.ShowToast(response ,ToastyMode.MODE_TOAST_ERROR) }
            }
            setEffect { LoginEffect.Loading(isLoading = false) }
        } else setEffect { LoginEffect.ShowToast("Please fill all Field" ,ToastyMode.MODE_TOAST_WARNING) }

    }
}
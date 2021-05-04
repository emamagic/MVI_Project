package com.emamagic.moviestreaming.ui.auth.login

import androidx.lifecycle.viewModelScope
import com.emamagic.moviestreaming.base.BaseViewModel
import com.emamagic.moviestreaming.network.request.LoginRequest
import com.emamagic.moviestreaming.repository.auth.login.LoginRepository
import com.emamagic.moviestreaming.ui.auth.login.contract.LoginEffect
import com.emamagic.moviestreaming.ui.auth.login.contract.LoginEvent
import com.emamagic.moviestreaming.ui.auth.login.contract.LoginState
import com.emamagic.moviestreaming.util.PreferencesManager
import com.emamagic.moviestreaming.util.ToastyMode
import com.emamagic.moviestreaming.util.exhaustive
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository,
    private val preferencesManager: PreferencesManager
): BaseViewModel<LoginState , LoginEffect ,LoginEvent>() {


    override fun createInitialState() = LoginState()

    override fun handleEvent(event: LoginEvent) {
        when(event){
            LoginEvent.RegisterClicked -> registerClicked()
            is LoginEvent.LoginClicked -> loginClicked(event.request)
            LoginEvent.CheckLogin -> checkLogin()
        }.exhaustive
    }

    private fun registerClicked() = viewModelScope.launch {
        setEffect { LoginEffect.Navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment()) }
    }

    private fun loginClicked(request: LoginRequest) = viewModelScope.launch {
        if (request.email.isNotEmpty() && request.phone.isNotEmpty() && request.password.isNotEmpty()){
            setEffect { LoginEffect.Loading(isLoading = true ,isDim = true) }
            when(val response = repository.login(request)) {
                "Login Ok" -> {
                    preferencesManager.setUserEmail(request.email)
                    preferencesManager.setUserPhone(request.phone)
                    setEffect { LoginEffect.Navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment()) }
                }
                else -> {
                    setEffect { LoginEffect.ShowToast(response ,ToastyMode.MODE_TOAST_ERROR) }
                    setEffect { LoginEffect.Loading(isLoading = false ,isDim = true) }
                }
            }
        } else setEffect { LoginEffect.ShowToast("Please fill all Field" ,ToastyMode.MODE_TOAST_WARNING) }

    }

    private fun checkLogin() = viewModelScope.launch {
        setEffect { LoginEffect.Loading(isLoading = true) }
        preferencesManager.isLogin().collect {
            if(it)
            setEffect { LoginEffect.Navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment()) }
            else setEffect { LoginEffect.Loading(isLoading = false) }
        }
    }

}
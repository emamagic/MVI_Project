package com.emamagic.moviestreaming.ui.modules.auth.login

import androidx.lifecycle.viewModelScope
import com.emamagic.moviestreaming.data.network.request.LoginRequest
import com.emamagic.moviestreaming.ui.base.CommonEffect
import com.emamagic.moviestreaming.data.preferences.PreferencesManager
import com.emamagic.moviestreaming.data.repository.auth.login.LoginRepository
import com.emamagic.moviestreaming.ui.base.BaseViewModel
import com.emamagic.moviestreaming.ui.modules.auth.login.contract.LoginEvent
import com.emamagic.moviestreaming.ui.modules.auth.login.contract.LoginState
import com.emamagic.moviestreaming.util.ToastyMode
import com.emamagic.moviestreaming.util.exhaustive
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@Suppress("IMPLICIT_CAST_TO_ANY")
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository,
    private val preferencesManager: PreferencesManager
): BaseViewModel<LoginState, CommonEffect, LoginEvent>() {

    private var isCheckedRemember: Boolean = false

    override fun createInitialState() = LoginState()

    override fun handleEvent(event: LoginEvent) {
        when(event){
            LoginEvent.RegisterClicked -> registerClicked()
            is LoginEvent.LoginClicked -> loginClicked(event.request)
            LoginEvent.CheckLogin -> checkLogin()
            is LoginEvent.CheckRememberChanged -> isCheckedRemember = event.isChecked
        }.exhaustive
    }

    private fun registerClicked() = viewModelScope.launch {
        setEffect { CommonEffect.Navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment()) }
    }

    private fun loginClicked(request: LoginRequest) = viewModelScope.launch {
        if (request.email.isNotEmpty() && request.phone.isNotEmpty() && request.password.isNotEmpty()) {
            setEffect { CommonEffect.Loading(isLoading = true, isDim = true) }
            when (val response = repository.login(request)) {
                "Login Ok" -> {
                    if (isCheckedRemember) {
                            preferencesManager.setUserEmail(request.email)
                            preferencesManager.setUserPhone(request.phone)
                        }
                    delay(1000)
                    setEffect { CommonEffect.Navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment()) }
                }
                else -> {
                    setEffect { CommonEffect.ShowToast(response, ToastyMode.MODE_TOAST_ERROR) }
                    setEffect { CommonEffect.Loading(isLoading = false, isDim = true) }
                }
            }
        } else setEffect {
            CommonEffect.ShowToast(
                "Please fill all Field",
                ToastyMode.MODE_TOAST_WARNING
            )
        }

    }

    private fun checkLogin() = viewModelScope.launch {
        setEffect { CommonEffect.Loading(isLoading = true) }
        preferencesManager.isLogin().collect {
            Timber.e("$it")
            if (it)
                setEffect { CommonEffect.Navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment()) }
            else setEffect { CommonEffect.Loading(isLoading = false) }
        }
    }

}
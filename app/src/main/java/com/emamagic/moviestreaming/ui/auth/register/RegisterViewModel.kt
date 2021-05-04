package com.emamagic.moviestreaming.ui.auth.register

import androidx.lifecycle.viewModelScope
import com.emamagic.moviestreaming.base.BaseViewModel
import com.emamagic.moviestreaming.network.request.RegisterRequest
import com.emamagic.moviestreaming.repository.auth.register.RegisterRepository
import com.emamagic.moviestreaming.ui.auth.register.contract.RegisterEffect
import com.emamagic.moviestreaming.ui.auth.register.contract.RegisterEvent
import com.emamagic.moviestreaming.ui.auth.register.contract.RegisterState
import com.emamagic.moviestreaming.util.PreferencesManager
import com.emamagic.moviestreaming.util.ToastyMode
import com.emamagic.moviestreaming.util.exhaustive
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: RegisterRepository,
    private val preferencesManager: PreferencesManager
): BaseViewModel<RegisterState ,RegisterEffect ,RegisterEvent>() {

    override fun createInitialState() = RegisterState()

    override fun handleEvent(event: RegisterEvent) {
        when(event) {
            is RegisterEvent.RegisterClicked -> registerClicked(event.request)
        }.exhaustive
    }

    private fun registerClicked(request: RegisterRequest) = viewModelScope.launch {
        if (request.name.isNotEmpty() && request.email.isNotEmpty() && request.phone.isNotEmpty() && request.password.isNotEmpty()) {
            setEffect { RegisterEffect.Loading(isLoading = true) }
            when(val response = repository.register(request)) {
                "Register Ok" -> {
                    preferencesManager.setUserName(request.name)
                    preferencesManager.setUserEmail(request.email)
                    preferencesManager.setUserPhone(request.phone)
                    setEffect { RegisterEffect.Navigate(RegisterFragmentDirections.actionRegisterFragmentToHomeFragment()) }
                }
                else -> setEffect { RegisterEffect.ShowToast(response ,ToastyMode.MODE_TOAST_SUCCESS) }
            }
            setEffect { RegisterEffect.Loading(isLoading = false) }
        }else setEffect { RegisterEffect.ShowToast("Please fill all Field" ,ToastyMode.MODE_TOAST_WARNING) }
    }
}
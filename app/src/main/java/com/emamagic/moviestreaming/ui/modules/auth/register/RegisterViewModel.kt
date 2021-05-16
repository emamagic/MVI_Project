package com.emamagic.moviestreaming.ui.modules.auth.register

import androidx.lifecycle.viewModelScope
import com.emamagic.moviestreaming.data.network.request.RegisterRequest
import com.emamagic.moviestreaming.data.repository.auth.register.RegisterRepository
import com.emamagic.moviestreaming.ui.base.BaseViewModel
import com.emamagic.moviestreaming.ui.base.CommonEffect
import com.emamagic.moviestreaming.ui.modules.auth.register.contract.RegisterEvent
import com.emamagic.moviestreaming.ui.modules.auth.register.contract.RegisterState
import com.emamagic.moviestreaming.util.ToastyMode
import com.emamagic.moviestreaming.util.exhaustive
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: RegisterRepository
): BaseViewModel<RegisterState, CommonEffect, RegisterEvent>() {

    override fun createInitialState() = RegisterState()

    override fun handleEvent(event: RegisterEvent) {
        when(event) {
            is RegisterEvent.RegisterClicked -> registerClicked(event.request)
        }.exhaustive
    }

    override fun showError(errorMessage: String) {
        setEffect { CommonEffect.ShowToast(errorMessage ,ToastyMode.MODE_TOAST_SUCCESS) }
    }

    private fun registerClicked(request: RegisterRequest) = viewModelScope.launch {
        if (request.name.isNotEmpty() && request.email.isNotEmpty() && request.phone.isNotEmpty() && request.password.isNotEmpty()) {
            setEffect { CommonEffect.Loading(isLoading = true) }
            when(val response = repository.register(request)) {
                "Register Ok" -> setEffect { CommonEffect.Navigate(navDirections = null) }
            }
            setEffect { CommonEffect.Loading(isLoading = false) }
        }else setEffect { CommonEffect.ShowToast("Please fill all Field" ,ToastyMode.MODE_TOAST_WARNING) }
    }
}
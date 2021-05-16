package com.emamagic.moviestreaming.provider.safe.error

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object ErrorRepository {

    private val _errorMessage : MutableStateFlow<String> = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

}
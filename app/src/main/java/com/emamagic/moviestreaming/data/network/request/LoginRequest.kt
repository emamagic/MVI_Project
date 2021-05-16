package com.emamagic.moviestreaming.data.network.request

data class LoginRequest(
    var email: String = "",
    var phone: String = "",
    var password: String = ""
)
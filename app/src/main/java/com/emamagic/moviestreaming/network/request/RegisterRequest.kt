package com.emamagic.moviestreaming.network.request

data class RegisterRequest(
    var name: String = "",
    var email: String = "",
    var phone: String = "",
    var password: String = ""
)
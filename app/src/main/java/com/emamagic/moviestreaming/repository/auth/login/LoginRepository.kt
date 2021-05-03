package com.emamagic.moviestreaming.repository.auth.login

import com.emamagic.moviestreaming.network.request.LoginRequest

interface LoginRepository {
    suspend fun login(request: LoginRequest): String
}
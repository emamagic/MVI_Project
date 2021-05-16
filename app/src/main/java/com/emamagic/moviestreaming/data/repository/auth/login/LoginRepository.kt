package com.emamagic.moviestreaming.data.repository.auth.login

import com.emamagic.moviestreaming.data.network.request.LoginRequest

interface LoginRepository {
    suspend fun login(request: LoginRequest): String
}
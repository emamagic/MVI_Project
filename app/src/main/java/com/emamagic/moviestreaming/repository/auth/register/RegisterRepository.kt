package com.emamagic.moviestreaming.repository.auth.register

import com.emamagic.moviestreaming.network.request.RegisterRequest

interface RegisterRepository {
    suspend fun register(request: RegisterRequest): String
}
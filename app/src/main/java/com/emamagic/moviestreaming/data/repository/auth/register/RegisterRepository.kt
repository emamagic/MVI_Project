package com.emamagic.moviestreaming.data.repository.auth.register

import com.emamagic.moviestreaming.data.network.request.RegisterRequest

interface RegisterRepository {
    suspend fun register(request: RegisterRequest): String
}
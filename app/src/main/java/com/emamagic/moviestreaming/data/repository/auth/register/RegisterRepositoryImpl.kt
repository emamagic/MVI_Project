package com.emamagic.moviestreaming.data.repository.auth.register

import com.emamagic.moviestreaming.data.network.api.AuthApi
import com.emamagic.moviestreaming.data.network.request.RegisterRequest
import com.emamagic.moviestreaming.provider.safe.SafeApi
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
): SafeApi() ,RegisterRepository {


    override suspend fun register(request: RegisterRequest): String {
        return try {
            authApi.register(name = request.name ,email = request.email ,phone = request.phone ,password = request.password).string()
        } catch (t: Throwable) {
            getError(t).message ?: "There is error"
        }
    }
}
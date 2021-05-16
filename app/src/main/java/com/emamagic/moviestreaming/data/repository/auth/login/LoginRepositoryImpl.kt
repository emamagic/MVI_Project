package com.emamagic.moviestreaming.data.repository.auth.login

import com.emamagic.moviestreaming.data.network.api.AuthApi
import com.emamagic.moviestreaming.data.network.request.LoginRequest
import com.emamagic.moviestreaming.provider.safe.SafeApi
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
): SafeApi() ,LoginRepository {


    override suspend fun login(request: LoginRequest): String {
        return try {
            authApi.login(email = request.email ,phone = request.phone ,password = request.password).string()
        }catch (t: Throwable) {
            getError(t).message ?: "There is error"
        }
    }


}
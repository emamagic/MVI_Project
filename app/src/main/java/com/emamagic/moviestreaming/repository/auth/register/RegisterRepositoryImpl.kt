package com.emamagic.moviestreaming.repository.auth.register

import com.emamagic.moviestreaming.network.api.AuthApi
import com.emamagic.moviestreaming.network.request.RegisterRequest
import com.emamagic.moviestreaming.util.helper.safe.SafeApi
import okhttp3.ResponseBody
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
): SafeApi() ,RegisterRepository {


    override suspend fun register(request: RegisterRequest): String {
        return authApi.register(name = request.name ,email = request.email ,phone = request.phone ,password = request.password).string()
    }
}
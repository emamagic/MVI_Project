package com.emamagic.moviestreaming.network.api

import okhttp3.ResponseBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {


    @POST("register.php")
    @FormUrlEncoded
    suspend fun register(
        @Field("username") name: String,
        @Field("email") email: String,
        @Field("phone") phone: String,
        @Field("password") password: String
    ): ResponseBody

    @POST("login.php")
    @FormUrlEncoded
    suspend fun login(
        @Field("email") email: String,
        @Field("phone") phone: String,
        @Field("password") password: String
    ): ResponseBody

}